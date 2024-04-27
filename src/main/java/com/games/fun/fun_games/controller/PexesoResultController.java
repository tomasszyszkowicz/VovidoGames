package com.games.fun.fun_games.controller;

import com.games.fun.fun_games.dto.PexesoResultDto;
import com.games.fun.fun_games.entity.PexesoResult;
import com.games.fun.fun_games.service.PexesoResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pexeso")
public class PexesoResultController {

    private final PexesoResultService pexesoResultService;

    @Autowired
    public PexesoResultController(PexesoResultService pexesoResultService) {
        this.pexesoResultService = pexesoResultService;
    }

    @GetMapping("/results")
    public ResponseEntity<List<PexesoResult>> getResults(
            @RequestParam(name = "top", defaultValue = "20") int top,
            @RequestParam(name = "bottom", defaultValue = "0") int bottom,
            @RequestParam(name = "difficulty", defaultValue = "1") int difficulty) {

        List<PexesoResult> results = pexesoResultService.getResults(bottom, top, difficulty);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PexesoResult> createResult(@RequestBody PexesoResultDto resultDto) {
        PexesoResult newResult = pexesoResultService.createResult(resultDto.getUsername(), resultDto.getScore(), resultDto.getDifficulty());
        if (newResult == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(newResult, HttpStatus.CREATED);
    }
}
