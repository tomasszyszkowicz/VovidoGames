package com.games.fun.fun_games.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Represents a Snake game result.
 */
@Entity
public class SnakeResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private int score;

    /**
     * Default constructor.
     */
    public SnakeResult() {
    }

    /**
     * Constructor with user and score parameters.
     *
     * @param user  the user associated with the result
     * @param score the score achieved in the game
     */
    public SnakeResult(User user, int score) {
        this.user = user;
        this.score = score;
    }

    /**
     * Get the ID of the Snake result.
     *
     * @return the ID of the Snake result
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the ID of the Snake result.
     *
     * @param id the ID of the Snake result
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the user associated with the Snake result.
     *
     * @return the user associated with the Snake result
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the user associated with the Snake result.
     *
     * @param user the user associated with the Snake result
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Get the score achieved in the game.
     *
     * @return the score achieved in the game
     */
    public int getScore() {
        return score;
    }

    /**
     * Set the score achieved in the game.
     *
     * @param score the score achieved in the game
     */
    public void setScore(int score) {
        this.score = score;
    }
}
