package com.games.fun.fun_games.dto;

public class CommentDto {

    private String username;
    private long postId;
    private String content;

    public CommentDto() {
    }

    public CommentDto(String username, long postId, String content) {
        this.username = username;
        this.postId = postId;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }
    
}
