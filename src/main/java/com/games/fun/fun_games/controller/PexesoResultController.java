package com.games.fun.fun_games.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

import com.games.fun.fun_games.dto.PexesoResultDto;
import com.games.fun.fun_games.entity.PexesoResult;
import com.games.fun.fun_games.repository.PexesoResultRepository;
import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.repository.UserRepository;

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
     * Retrieves all users.
     *
     * @return ResponseEntity containing a list of users and HTTP status code OK.
     */
    @GetMapping
    public ResponseEntity<List<PexesoResult>> getAllResults() {
        List<PexesoResult> results = pexesoResultRepository.findAll();
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