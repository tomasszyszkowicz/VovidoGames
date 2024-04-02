package com.games.fun.fun_games.dto;

/**
 * The RegistrationUserDto class represents a data transfer object for user registration information.
 */
public class RegistrationUserDto {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    
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
     * @param username The username to set.
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
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the confirm password of the user.
     * 
     * @return The confirm password of the user.
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * Sets the confirm password of the user.
     * 
     * @param confirmPassword The confirm password to set.
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * Gets the email of the user.
     * 
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     * 
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
