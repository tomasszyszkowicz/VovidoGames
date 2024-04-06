package com.games.fun.fun_games.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.games.fun.fun_games.repository.SnakeResultRepository;
import com.games.fun.fun_games.entity.SnakeResult;
import com.games.fun.fun_games.dto.SnakeResultDto;

import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.repository.UserRepository;


/**
 * The UserController class handles HTTP requests related to users.
 */
@RestController
@RequestMapping("/snake")
public class SnakeResultController {

    @Autowired
    private SnakeResultRepository snakeResultRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/results")
    public ResponseEntity<List<SnakeResult>> getResults() {
        List<SnakeResult> results = snakeResultRepository.findAll();
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SnakeResult> createResult(@RequestBody SnakeResultDto result) {
        System.out.println(result.getUsername());
        User user = userRepository.findByUsername(result.getUsername());
        SnakeResult newResult = snakeResultRepository.save(new SnakeResult(user, result.getScore()));
        return new ResponseEntity<>(newResult, HttpStatus.CREATED);
    }
}