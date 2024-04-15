package com.games.fun.fun_games.controller;

import com.games.fun.fun_games.dto.PexesoResultDto;
import com.games.fun.fun_games.entity.PexesoResult;
import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.service.PexesoResultService;
import com.games.fun.fun_games.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/results")
public class PexesoResultController {

    private final PexesoResultService pexesoResultService;
    private final UserService userService;

    @Autowired
    public PexesoResultController(PexesoResultService pexesoResultService, UserService userService) {
        this.pexesoResultService = pexesoResultService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<PexesoResult>> getResults(
            @RequestParam(name = "top", defaultValue = "20") int top,
            @RequestParam(name = "bottom", defaultValue = "0") int bottom,
            @RequestParam(name = "difficulty", defaultValue = "1") int difficulty) {

        List<PexesoResult> results = pexesoResultService.getResults(bottom, top, difficulty);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/record-holders")
    public ResponseEntity<Map<String, PexesoResult>> getLowestHighscoresByDifficultyMapped() {
        Map<String, PexesoResult> mappedResults = pexesoResultService.getLowestHighscoresByDifficultyMapped();
        return new ResponseEntity<>(mappedResults, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PexesoResult> createResult(@RequestBody PexesoResultDto resultDto) {
        User user = userService.getUserByUsername(resultDto.getUsername());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        PexesoResult newResult = pexesoResultService.createResult(user, resultDto.getScore(), resultDto.getDifficulty());
        return new ResponseEntity<>(newResult, HttpStatus.CREATED);
    }
}
