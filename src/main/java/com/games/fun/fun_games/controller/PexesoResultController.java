package com.games.fun.fun_games.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

import com.games.fun.fun_games.dto.PexesoResultDto;
import com.games.fun.fun_games.entity.PexesoResult;
import com.games.fun.fun_games.repository.PexesoResultRepository;
import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

/**
 * The UserController class handles HTTP requests related to users.
 */
@RestController
@RequestMapping("/results")
public class PexesoResultController {

    @Autowired
    private PexesoResultRepository pexesoResultRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Fetches a list of results within the specified range and difficulty.
     *
     * @param top The top index of the range.
     * @param bottom The bottom index of the range.
     * @param difficulty The difficulty of the results.
     * @return ResponseEntity containing the list of results and HTTP status code OK.
     */
    @GetMapping
    public ResponseEntity<List<PexesoResult>> getResults(
            @RequestParam(name = "top", defaultValue = "20") int top,
            @RequestParam(name = "bottom", defaultValue = "0") int bottom,
            @RequestParam(name = "difficulty", defaultValue = "1") int difficulty) {
        
        // Calculate the number of results to fetch (size) and the page number
        int size = top - bottom + 1; // This ensures we get the range from bottom to top
        int pageNumber = bottom / size; // Calculate the page number starting from 0
        
        // Create PageRequest for pagination and sorting, using calculated page number and size
        PageRequest pageRequest = PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.ASC, "score"));
        
        // Fetch the results filtered by difficulty, within the specified range
        Page<PexesoResult> resultPage = pexesoResultRepository.findByDifficulty(difficulty, pageRequest);
        
        // Get the content of the page
        List<PexesoResult> results = resultPage.getContent();
        
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    /**
     * Creates a new result.
     *
     * @param result The result object to be created.
     * @return ResponseEntity containing the created result and HTTP status code CREATED.
     */
    @PostMapping
    public ResponseEntity<PexesoResult> createResult(@RequestBody PexesoResultDto result) {
        System.out.println(result.getUsername());
        User user = userRepository.findByUsername(result.getUsername());
        PexesoResult newResult = pexesoResultRepository.save(new PexesoResult(user, result.getScore(), result.getDifficulty()));
        return new ResponseEntity<>(newResult, HttpStatus.CREATED);
    }
}