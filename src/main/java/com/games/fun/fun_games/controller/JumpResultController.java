package com.games.fun.fun_games.controller;

import com.games.fun.fun_games.dto.JumpResultDto;
import com.games.fun.fun_games.entity.JumpResult;
import com.games.fun.fun_games.service.JumpResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling JumpResult related operations.
 */
@RestController
@RequestMapping("/jump")
public class JumpResultController {

    private final JumpResultService jumpResultService;

    @Autowired
    public JumpResultController(JumpResultService jumpResultService) {
        this.jumpResultService = jumpResultService;
    }

    /**
     * Retrieves a list of JumpResult objects within the specified range.
     *
     * @param top    The upper limit of the range (default: 20)
     * @param bottom The lower limit of the range (default: 0)
     * @return A ResponseEntity containing the list of JumpResult objects and the HTTP status code
     */
    @GetMapping("/results")
    public ResponseEntity<List<JumpResult>> getResults(
            @RequestParam(name = "top", defaultValue = "20") int top,
            @RequestParam(name = "bottom", defaultValue = "0") int bottom) {
        List<JumpResult> results = jumpResultService.getResults(bottom, top);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    /**
     * Retrieves a list of JumpResult objects representing the records within the specified range.
     *
     * @param top    The upper limit of the range (default: 20)
     * @param bottom The lower limit of the range (default: 0)
     * @return A ResponseEntity containing the list of JumpResult objects and the HTTP status code
     */
    @GetMapping("/records")
    public ResponseEntity<List<JumpResult>> getRecords(
            @RequestParam(name = "top", defaultValue = "20") int top,
            @RequestParam(name = "bottom", defaultValue = "0") int bottom) {
        List<JumpResult> results = jumpResultService.getRecords(bottom, top);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    /**
     * Creates a new JumpResult object based on the provided JumpResultDto.
     *
     * @param resultDto The JumpResultDto object containing the data for the new SnakeResult
     * @return A ResponseEntity containing the created SnakeResult object and the HTTP status code
     */
    @PostMapping
    public ResponseEntity<JumpResult> createResult(@RequestBody JumpResultDto resultDto) {
        JumpResult newResult = jumpResultService.createResult(resultDto);
        if (newResult == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(newResult, HttpStatus.CREATED);
    }
}
