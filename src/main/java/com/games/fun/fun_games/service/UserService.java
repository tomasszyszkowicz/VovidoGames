package com.games.fun.fun_games.service;

import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing users.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Constructor for UserService.
     * 
     * @param userRepository The UserRepository to be used for database operations.
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all users from the database.
     * 
     * @return A list of all users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Searches for users based on a query string.
     * 
     * @param query The query string to search for.
     * @return A list of users matching the query.
     */
    public List<User> searchUsers(String query) {
        return userRepository.findByUsernameContaining(query);
    }

    /**
     * Retrieves a user by their username.
     * 
     * @param username The username of the user to retrieve.
     * @return The user with the specified username, or null if not found.
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Saves a user to the database.
     * 
     * @param user The user to save.
     */
    public void saveUser(User user) {
        userRepository.save(user);
    }

    /**
     * Registers a new user.
     * 
     * @param user The user to register.
     */
    public void registerUser(User user) {
        userRepository.save(user);
    }

    /**
     * Deletes a user by their username.
     * 
     * @param username The username of the user to delete.
     */
    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }
}
