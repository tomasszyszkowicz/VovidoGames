package com.games.fun.fun_games.dto;

/**
 * Represents an email data transfer object (DTO) used for transferring email and password information.
 */
public class EmailDto {

    private String email;
    private String password;

    /**
     * Constructs an empty EmailDto object.
     */
    public EmailDto() {
    }

    /**
     * Constructs an EmailDto object with the specified email and password.
     *
     * @param email    the email address
     * @param password the password
     */
    public EmailDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Returns the email address.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     *
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
