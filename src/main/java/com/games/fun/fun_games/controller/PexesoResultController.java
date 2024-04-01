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
     * Retrieves a list of Pexeso results.
     *
     * @param count The number of results to retrieve. Defaults to 5 if not specified.
     * @return A ResponseEntity containing a list of PexesoResult objects.
     */
    @GetMapping
    public ResponseEntity<List<PexesoResult>> getResults(@RequestParam(name="top", defaultValue = "5") int top) {
        // Create PageRequest for pagination and sorting
        PageRequest pageRequest = PageRequest.of(0, top, Sort.by(Sort.Direction.ASC, "score"));

        // Fetch the first 'count' results sorted by score in ascending order
        Page<PexesoResult> resultPage = pexesoResultRepository.findAll(pageRequest);
        
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
        PexesoResult newResult = pexesoResultRepository.save(new PexesoResult(user, result.getScore()));
        return new ResponseEntity<>(newResult, HttpStatus.CREATED);
    }
}