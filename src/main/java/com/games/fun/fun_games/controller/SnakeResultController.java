package com.games.fun.fun_games.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<List<SnakeResult>> getResults(
            @RequestParam(name = "top", defaultValue = "20") int top,
            @RequestParam(name = "bottom", defaultValue = "0") int bottom) {
        
        // Calculate the number of results to fetch (size) and the page number
        int size = top - bottom + 1; // This ensures we get the range from bottom to top
        int pageNumber = bottom / size; // Calculate the page number starting from 0
        
        // Create PageRequest for pagination and sorting, using calculated page number and size
        PageRequest pageRequest = PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.DESC, "score"));
        
        // Fetch the results filtered by difficulty, within the specified range
        Page<SnakeResult> resultPage = snakeResultRepository.findAll(pageRequest);
        // Get the content of the page
        List<SnakeResult> results = resultPage.getContent();
        
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