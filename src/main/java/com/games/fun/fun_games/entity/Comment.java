package com.games.fun.fun_games.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * The Comment class represents a comment made by a user on a post.
 */
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDateTime dateCreated;
    private String content;

    /**
     * Default constructor for Comment class.
     */
    public Comment() {
    }

    /**
     * Constructor for Comment class.
     * 
     * @param user         The user who made the comment.
     * @param post         The post to which the comment belongs.
     * @param dateCreated  The date and time when the comment was created.
     * @param content      The content of the comment.
     */
    public Comment(User user, Post post, LocalDateTime dateCreated, String content) {
        this.user = user;
        this.post = post;
        this.dateCreated = dateCreated;
        this.content = content;
    }

    /**
     * Get the ID of the comment.
     * 
     * @return The ID of the comment.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the ID of the comment.
     * 
     * @param id The ID of the comment.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the user who made the comment.
     * 
     * @return The user who made the comment.
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the user who made the comment.
     * 
     * @param user The user who made the comment.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Get the date and time when the comment was created.
     * 
     * @return The date and time when the comment was created.
     */
    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    /**
     * Set the date and time when the comment was created.
     * 
     * @param dateCreated The date and time when the comment was created.
     */
    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
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
     * Set the content of the comment.
     * 
     * @param content The content of the comment.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get the post to which the comment belongs.
     * 
     * @return The post to which the comment belongs.
     */
    public Post getPost() {
        return post;
    }

    /**
     * Set the post to which the comment belongs.
     * 
     * @param post The post to which the comment belongs.
     */
    public void setPost(Post post) {
        this.post = post;
    }
}



