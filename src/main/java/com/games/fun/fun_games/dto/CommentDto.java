package com.games.fun.fun_games.dto;

/**
 * Data transfer object for comments.
 */
public class CommentDto {

    private String username;
    private long postId;
    private String content;

    /**
     * Default constructor for CommentDto class.
     */
    public CommentDto() {
    }

    /**
     * Constructor for CommentDto class with parameters.
     * 
     * @param username The username of the commenter.
     * @param postId   The ID of the post the comment is associated with.
     * @param content  The content of the comment.
     */
    public CommentDto(String username, long postId, String content) {
        this.username = username;
        this.postId = postId;
        this.content = content;
    }

    /**
     * Get the username of the commenter.
     * 
     * @return The username of the commenter.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the content of the comment.
     * 
     * @return The content of the comment.
     */
    public String getContent() {
        return content;
    }

    /**
     * Set the username of the commenter.
     * 
     * @param username The username of the commenter.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set the content of the comment.
     * 
     * @param content The content of the comment.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get the ID of the post the comment is associated with.
     * 
     * @return The ID of the post.
     */
    public long getPostId() {
        return postId;
    }

    /**
     * Set the ID of the post the comment is associated with.
     * 
     * @param postId The ID of the post.
     */
    public void setPostId(long postId) {
        this.postId = postId;
    }
    
}
