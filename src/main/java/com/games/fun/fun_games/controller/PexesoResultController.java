package com.games.fun.fun_games.controller;

import com.games.fun.fun_games.dto.PexesoResultDto;
import com.games.fun.fun_games.entity.PexesoResult;
import com.games.fun.fun_games.service.PexesoResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling PexesoResult related operations.
 */
@RestController
@RequestMapping("/pexeso")
public class PexesoResultController {

    private final PexesoResultService pexesoResultService;

    @Autowired
    public PexesoResultController(PexesoResultService pexesoResultService) {
        this.pexesoResultService = pexesoResultService;
    }

    /**
     * Retrieves a list of PexesoResult objects based on the specified parameters.
     *
     * @param top        The number of top results to retrieve (default: 20).
     * @param bottom     The number of bottom results to retrieve (default: 0).
     * @param difficulty The difficulty level of the results to retrieve (default: 1).
     * @return A ResponseEntity containing the list of PexesoResult objects and the HTTP status code.
     */
    @GetMapping("/results")
    public ResponseEntity<List<PexesoResult>> getResults(
            @RequestParam(name = "top", defaultValue = "20") int top,
            @RequestParam(name = "bottom", defaultValue = "0") int bottom,
            @RequestParam(name = "difficulty", defaultValue = "1") int difficulty) {

        List<PexesoResult> results = pexesoResultService.getResults(bottom, top, difficulty);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    /**
     * Retrieves a list of PexesoResult objects representing the records based on the specified parameters.
     *
     * @param top        The number of top records to retrieve (default: 20).
     * @param bottom     The number of bottom records to retrieve (default: 0).
     * @param difficulty The difficulty level of the records to retrieve (default: 1).
     * @return A ResponseEntity containing the list of PexesoResult objects and the HTTP status code.
     */
    @GetMapping("/records")
    public ResponseEntity<List<PexesoResult>> getRecords(
            @RequestParam(name = "top", defaultValue = "20") int top,
            @RequestParam(name = "bottom", defaultValue = "0") int bottom,
            @RequestParam(name = "difficulty", defaultValue = "1") int difficulty) {

        List<PexesoResult> results = pexesoResultService.getRecords(bottom, top, difficulty);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    /**
     * Creates a new PexesoResult object based on the provided PexesoResultDto.
     *
     * @param resultDto The PexesoResultDto object containing the result details.
     * @return A ResponseEntity containing the created PexesoResult object and the HTTP status code.
     */
    @PostMapping
    public ResponseEntity<PexesoResult> createResult(@RequestBody PexesoResultDto resultDto) {
        PexesoResult newResult = pexesoResultService.createResult(resultDto.getUsername(), resultDto.getScore(), resultDto.getDifficulty());
        if (newResult == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(newResult, HttpStatus.CREATED);
    }
}
