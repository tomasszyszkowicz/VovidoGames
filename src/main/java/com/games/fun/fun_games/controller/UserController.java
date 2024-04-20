package com.games.fun.fun_games.controller;

import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.games.fun.fun_games.dto.PasswordDto;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(@RequestParam(name = "query", required = false) String query) {
        List<User> users;
        if (query != null && !query.isEmpty()) {
            users = userService.searchUsers(query);
        } else {
            users = userService.getAllUsers();
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{username}/pfp")
    public ResponseEntity<?> updateProfilePicture(@PathVariable String username, @RequestParam("pfpURL") String pfpURL) {
        try {
            User user = userService.getUserByUsername(username);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            user.setProfilePictureURL(pfpURL);
            userService.saveUser(user);
            return ResponseEntity.ok("Profile picture updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not update profile picture");
        }
    }

    @PatchMapping("/{username}/password")
    public ResponseEntity<?> updatePassword(@PathVariable String username, @RequestBody PasswordDto passwordDto) {
        try {
            User user = userService.getUserByUsername(username);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            if (!passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect password");
            }
            if (passwordDto.getNewPassword().length() <= 6) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("New password too short. Must be at least 6 characters.");
            }
            if (!passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match");
            }
            user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
            userService.saveUser(user);
            return ResponseEntity.ok("Password updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not update password");
        }
    }

}

