package com.games.fun.fun_games.dto;

public class SnakeResultDto {

    private String username;
    private int score;

    public SnakeResultDto() {
    }

    public SnakeResultDto(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    } 
}
