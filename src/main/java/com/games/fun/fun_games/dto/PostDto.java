package com.games.fun.fun_games.dto;

public class PostDto {

    private String username;
    private String dateCreated;
    private String title;
    private String content;

    public PostDto() {
    }

    public PostDto(String username, String dateCreated, String title, String content) {
        this.username = username;
        this.dateCreated = dateCreated;
        this.title = title;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public String getDateCreated() {
        return dateCreated;
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

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
