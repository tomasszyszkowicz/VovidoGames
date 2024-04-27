package com.games.fun.fun_games.dto;

/**
 * The PexesoResultDto class represents the data transfer object for Pexeso game results.
 * It contains information about the username, score, and difficulty level.
 */
public class PexesoResultDto {

    private String username;
    private int score;
    private int difficulty;

    /**
     * Constructs an empty PexesoResultDto object.
     */
    public PexesoResultDto() {
    }

    /**
     * Constructs a PexesoResultDto object with the specified username and score.
     *
     * @param username the username of the player
     * @param score    the score achieved by the player
     */
    public PexesoResultDto(String username, int score) {
        this.username = username;
        this.score = score;
    }

    /**
     * Returns the username of the player.
     *
     * @return the username of the player
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the score achieved by the player.
     *
     * @return the score achieved by the player
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the username of the player.
     *
     * @param username the username of the player
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the score achieved by the player.
     *
     * @param score the score achieved by the player
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Returns the difficulty level of the game.
     *
     * @return the difficulty level of the game
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the difficulty level of the game.
     *
     * @param difficulty the difficulty level of the game
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
    
}
