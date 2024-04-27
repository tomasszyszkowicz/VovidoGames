package com.games.fun.fun_games.dto;

/**
 * The PostDto class represents a data transfer object for a post.
 */
public class PostDto {

    private String username;
    private String title;
    private String content;

    /**
     * Constructs an empty PostDto object.
     */
    public PostDto() {
    }

    /**
     * Constructs a PostDto object with the specified username, title, and content.
     *
     * @param username the username of the post author
     * @param title    the title of the post
     * @param content  the content of the post
     */
    public PostDto(String username, String title, String content) {
        this.username = username;
        this.title = title;
        this.content = content;
    }

    /**
     * Returns the username of the post author.
     *
     * @return the username of the post author
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the title of the post.
     *
     * @return the title of the post
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the content of the post.
     *
     * @return the content of the post
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the username of the post author.
     *
     * @param username the username of the post author
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the title of the post.
     *
     * @param title the title of the post
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the content of the post.
     *
     * @param content the content of the post
     */
    public void setContent(String content) {
        this.content = content;
    }
}
