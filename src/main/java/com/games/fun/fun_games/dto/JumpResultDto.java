package com.games.fun.fun_games.dto;

public class JumpResultDto {

    private String username;
    private int score;

    /**
     * Default constructor for JumpResultDto class.
     */
    public JumpResultDto() {
    }

    /**
     * Constructor for JumpResultDto class with username and score parameters.
     * 
     * @param username the username of the player
     * @param score the score achieved by the player
     */
    public JumpResultDto(String username, int score) {
        this.username = username;
        this.score = score;
    }

    /**
     * Get the username of the player.
     * 
     * @return the username of the player
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username of the player.
     * 
     * @param username the username of the player
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the score achieved by the player.
     * 
     * @return the score achieved by the player
     */
    public int getScore() {
        return score;
    }

    /**
     * Set the score achieved by the player.
     * 
     * @param score the score achieved by the player
     */
    public void setScore(int score) {
        this.score = score;
    } 
}

