package com.games.fun.fun_games.dto;

public class PostDto {

    private String username;
    private String title;
    private String content;

    public PostDto() {
    }

    public PostDto(String username, String title, String content) {
        this.username = username;
        this.title = title;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
