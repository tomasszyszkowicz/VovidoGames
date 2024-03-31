package com.games.fun.fun_games.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.repository.UserRepository;

/**
 * The UserController class handles HTTP requests related to users.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves all users.
     *
     * @return ResponseEntity containing a list of users and HTTP status code OK.
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllPlayers() {
        List<User> players = userRepository.findAll();
        return new ResponseEntity<>(players, HttpStatus.OK);
    }
}