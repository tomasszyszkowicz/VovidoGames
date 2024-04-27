package com.games.fun.fun_games.dto;

public class ProfilePictureDto {

    private String profilePictureURL;
    private String password;

    /**
     * Default constructor for ProfilePictureDto class.
     */
    public ProfilePictureDto() {
    }

    /**
     * Constructor for ProfilePictureDto class with profile picture URL and password parameters.
     *
     * @param profilePictureURL The URL of the profile picture.
     * @param password          The password associated with the profile picture.
     */
    public ProfilePictureDto(String profilePictureURL, String password) {
        this.profilePictureURL = profilePictureURL;
        this.password = password;
    }

    /**
     * Retrieves the profile picture URL.
     *
     * @return The profile picture URL.
     */
    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    /**
     * Sets the profile picture URL.
     *
     * @param profilePictureURL The URL of the profile picture.
     */
    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    /**
     * Retrieves the password associated with the profile picture.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password associated with the profile picture.
     *
     * @param password The password.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
