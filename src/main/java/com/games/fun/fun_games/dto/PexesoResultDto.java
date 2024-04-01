package com.games.fun.fun_games.dto;

public class PexesoResultDto {

    private String username;
    private int score;

    public PexesoResultDto() {
    }

    public PexesoResultDto(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
}
