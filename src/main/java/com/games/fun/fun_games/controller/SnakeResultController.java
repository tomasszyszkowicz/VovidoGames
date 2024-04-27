package com.games.fun.fun_games.controller;

import com.games.fun.fun_games.dto.SnakeResultDto;
import com.games.fun.fun_games.entity.SnakeResult;
import com.games.fun.fun_games.service.SnakeResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling SnakeResult related operations.
 */
@RestController
@RequestMapping("/snake")
public class SnakeResultController {

    private final SnakeResultService snakeResultService;

    @Autowired
    public SnakeResultController(SnakeResultService snakeResultService) {
        this.snakeResultService = snakeResultService;
    }

    /**
     * Retrieves a list of SnakeResult objects within the specified range.
     *
     * @param top    The upper limit of the range (default: 20)
     * @param bottom The lower limit of the range (default: 0)
     * @return A ResponseEntity containing the list of SnakeResult objects and the HTTP status code
     */
    @GetMapping("/results")
    public ResponseEntity<List<SnakeResult>> getResults(
            @RequestParam(name = "top", defaultValue = "20") int top,
            @RequestParam(name = "bottom", defaultValue = "0") int bottom) {
        List<SnakeResult> results = snakeResultService.getResults(bottom, top);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    /**
     * Retrieves a list of SnakeResult objects representing the records within the specified range.
     *
     * @param top    The upper limit of the range (default: 20)
     * @param bottom The lower limit of the range (default: 0)
     * @return A ResponseEntity containing the list of SnakeResult objects and the HTTP status code
     */
    @GetMapping("/records")
    public ResponseEntity<List<SnakeResult>> getRecords(
            @RequestParam(name = "top", defaultValue = "20") int top,
            @RequestParam(name = "bottom", defaultValue = "0") int bottom) {
        List<SnakeResult> results = snakeResultService.getRecords(bottom, top);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    /**
     * Creates a new SnakeResult object based on the provided SnakeResultDto.
     *
     * @param resultDto The SnakeResultDto object containing the data for the new SnakeResult
     * @return A ResponseEntity containing the created SnakeResult object and the HTTP status code
     */
    @PostMapping
    public ResponseEntity<SnakeResult> createResult(@RequestBody SnakeResultDto resultDto) {
        SnakeResult newResult = snakeResultService.createResult(resultDto);
        if (newResult == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(newResult, HttpStatus.CREATED);
    }
}
