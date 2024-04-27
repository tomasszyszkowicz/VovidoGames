package com.games.fun.fun_games.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Represents a Pexeso game result.
 */
@Entity
public class PexesoResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private int score;
    private int difficulty;

    /**
     * Default constructor.
     */
    public PexesoResult() {
    }

    /**
     * Constructor with parameters.
     * 
     * @param user       The user associated with the result.
     * @param score      The score achieved in the game.
     * @param difficulty The difficulty level of the game.
     */
    public PexesoResult(User user, int score, int difficulty) {
        this.user = user;
        this.score = score;
        this.difficulty = difficulty;
    }

    /**
     * Get the ID of the result.
     * 
     * @return The ID of the result.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the ID of the result.
     * 
     * @param id The ID of the result.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the user associated with the result.
     * 
     * @return The user associated with the result.
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the user associated with the result.
     * 
     * @param user The user associated with the result.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Get the score achieved in the game.
     * 
     * @return The score achieved in the game.
     */
    public int getScore() {
        return score;
    }

    /**
     * Set the score achieved in the game.
     * 
     * @param score The score achieved in the game.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Get the difficulty level of the game.
     * 
     * @return The difficulty level of the game.
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Set the difficulty level of the game.
     * 
     * @param difficulty The difficulty level of the game.
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
    
}
