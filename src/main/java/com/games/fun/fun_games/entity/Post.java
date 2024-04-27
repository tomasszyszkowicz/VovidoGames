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
 * Represents a post entity in the application.
 */
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime dateCreated;
    private String title;
    private String content;

    /**
     * Default constructor for the Post class.
     */
    public Post() {
    }

    /**
     * Constructor for the Post class.
     * 
     * @param user         The user who created the post.
     * @param dateCreated  The date and time when the post was created.
     * @param title        The title of the post.
     * @param content      The content of the post.
     */
    public Post(User user, LocalDateTime dateCreated, String title, String content) {
        this.user = user;
        this.dateCreated = dateCreated;
        this.title = title;
        this.content = content;
    }

    /**
     * Get the ID of the post.
     * 
     * @return The ID of the post.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the ID of the post.
     * 
     * @param id The ID of the post.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the user who created the post.
     * 
     * @return The user who created the post.
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the user who created the post.
     * 
     * @param user The user who created the post.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Get the date and time when the post was created.
     * 
     * @return The date and time when the post was created.
     */
    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    /**
     * Set the date and time when the post was created.
     * 
     * @param dateCreated The date and time when the post was created.
     */
    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Get the title of the post.
     * 
     * @return The title of the post.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the post.
     * 
     * @param title The title of the post.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the content of the post.
     * 
     * @return The content of the post.
     */
    public String getContent() {
        return content;
    }

    /**
     * Set the content of the post.
     * 
     * @param content The content of the post.
     */
    public void setContent(String content) {
        this.content = content;
    }
}
