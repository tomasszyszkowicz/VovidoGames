package com.games.fun.fun_games.entity;

import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

/**
 * Represents a user entity in the application.
 */
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    
    private String password;
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PexesoResult> results = new ArrayList<>();

    /**
     * Retrieves the ID of the user.
     * 
     * @return The ID of the user.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the user.
     * 
     * @param id The ID of the user.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the username of the user.
     * 
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     * 
     * @param userName The username of the user.
     */
    public void setUsername(String userName) {
        this.username = userName;
    }

    /**
     * Retrieves the password of the user.
     * 
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * 
     * @param password The password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the email of the user.
     * 
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     * 
     * @param email The email of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the results of the user.
     * 
     * @return The results of the user.
     */
    public List<PexesoResult> getResults() {
        return results;
    }

    /**
     * Sets the results of the user.
     * 
     * @param results The results of the user.
     */
    public void setResults(List<PexesoResult> results) {
        this.results = results;
    }
}
