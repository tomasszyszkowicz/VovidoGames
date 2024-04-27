package com.games.fun.fun_games.controller;

import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.games.fun.fun_games.dto.PasswordDto;
import com.games.fun.fun_games.dto.EmailDto;
import com.games.fun.fun_games.dto.UsernameDto;
import com.games.fun.fun_games.dto.ProfilePictureDto;

import java.util.List;

/**
 * Controller class for managing user-related operations.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for UserController.
     * 
     * @param userService     The UserService instance.
     * @param passwordEncoder The PasswordEncoder instance.
     */
    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Get a list of users.
     * 
     * @param query The search query (optional).
     * @return The ResponseEntity containing the list of users.
     */
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

    /**
     * Get a user by username.
     * 
     * @param username The username of the user.
     * @return The ResponseEntity containing the user.
     */
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Register a new user.
     * 
     * @param user The user to register.
     * @return The ResponseEntity containing the registered user.
     */
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * Update the password of a user.
     * 
     * @param username    The username of the user.
     * @param passwordDto The PasswordDto containing the old and new passwords.
     * @return The ResponseEntity indicating the status of the password update.
     */
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

    /**
     * Update the email of a user.
     * 
     * @param username The username of the user.
     * @param emailDto The EmailDto containing the password and new email.
     * @return The ResponseEntity indicating the status of the email update.
     */
    @PatchMapping("/{username}/email")
    public ResponseEntity<?> updateEmail(@PathVariable String username, @RequestBody EmailDto emailDto) {
        try {
            User user = userService.getUserByUsername(username);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            if (!passwordEncoder.matches(emailDto.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect password");
            }
            user.setEmail(emailDto.getEmail());
            userService.saveUser(user);
            return ResponseEntity.ok("Email updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not update email");
        }
    }

    /**
     * Update the username of a user.
     * 
     * @param username    The username of the user.
     * @param usernameDto The UsernameDto containing the password and new username.
     * @return The ResponseEntity indicating the status of the username update.
     */
    @PatchMapping("/{username}/username")
    public ResponseEntity<?> updateUsername(@PathVariable String username, @RequestBody UsernameDto usernameDto) {
        try {
            User user = userService.getUserByUsername(username);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            if (!passwordEncoder.matches(usernameDto.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect password");
            }
            user.setUsername(usernameDto.getUsername());
            userService.saveUser(user);
            return ResponseEntity.ok("Username updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not update username");
        }
    }

    /**
     * Update the profile picture of a user.
     * 
     * @param username          The username of the user.
     * @param profilePictureDto The ProfilePictureDto containing the password and profile picture URL.
     * @return The ResponseEntity indicating the status of the profile picture update.
     */
    @PatchMapping("/{username}/pfp")
    public ResponseEntity<?> updateProfilePicture(@PathVariable String username, @RequestBody ProfilePictureDto profilePictureDto) {
        try {
            User user = userService.getUserByUsername(username);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            if (!passwordEncoder.matches(profilePictureDto.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect password");
            }
            user.setProfilePictureURL(profilePictureDto.getProfilePictureURL());
            userService.saveUser(user);
            return ResponseEntity.ok("Profile picture updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not update profile picture");
        }
    }

    /**
     * Delete a user.
     * 
     * @param username The username of the user to delete.
     * @return The ResponseEntity indicating the status of the user deletion.
     */
    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        System.out.println("Deleting user: " + username);
        try {
            userService.deleteUser(username);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not delete user");
        }
    }
}

