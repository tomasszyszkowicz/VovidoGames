package com.games.fun.fun_games.dto;

public class ProfilePictureDto {

    private String profilePictureURL;
    private String password;

    public ProfilePictureDto() {
    }

    public ProfilePictureDto(String profilePictureURL, String password) {
        this.profilePictureURL = profilePictureURL;
        this.password = password;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
