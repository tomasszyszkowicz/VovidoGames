package com.games.fun.fun_games.dto;

/**
 * The UserDto class represents a data transfer object for a user.
 * It contains the username and password of the user.
 */
public class UserDto {
    private String username;
    private String password;
    
    /**
     * Gets the username of the user.
     * 
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     * 
     * @param username The username to be set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     * 
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * 
     * @param password The password to be set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
