package com.games.fun.fun_games.dto;

/**
 * The UsernameDto class represents a data transfer object for username and password.
 * It is used to transfer username and password information between different layers of the application.
 */
public class UsernameDto {

    private String username;
    private String password;

    /**
     * Default constructor for the UsernameDto class.
     */
    public UsernameDto() {
    }

    /**
     * Constructor for the UsernameDto class with username and password parameters.
     * 
     * @param username The username.
     * @param password The password.
     */
    public UsernameDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Get the username.
     * 
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username.
     * 
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the password.
     * 
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password.
     * 
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
