package com.games.fun.fun_games.controller;

import com.games.fun.fun_games.dto.SnakeResultDto;
import com.games.fun.fun_games.entity.SnakeResult;
import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.service.SnakeResultService;
import com.games.fun.fun_games.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/snake")
public class SnakeResultController {

    private final SnakeResultService snakeResultService;
    private final UserService userService;

    @Autowired
    public SnakeResultController(SnakeResultService snakeResultService, UserService userService) {
        this.snakeResultService = snakeResultService;
        this.userService = userService;
    }

    @GetMapping("/results")
    public ResponseEntity<List<SnakeResult>> getResults(
            @RequestParam(name = "top", defaultValue = "20") int top,
            @RequestParam(name = "bottom", defaultValue = "0") int bottom) {
        List<SnakeResult> results = snakeResultService.getResults(bottom, top);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SnakeResult> createResult(@RequestBody SnakeResultDto resultDto) {
        User user = userService.getUserByUsername(resultDto.getUsername());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        SnakeResult newResult = snakeResultService.createResult(user, resultDto.getScore());
        return new ResponseEntity<>(newResult, HttpStatus.CREATED);
    }
}
