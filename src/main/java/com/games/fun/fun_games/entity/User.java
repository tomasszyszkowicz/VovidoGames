package com.games.fun.fun_games.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.games.fun.fun_games.enums.Role;

import java.util.HashSet;
import java.util.Set;

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
    private String profilePictureURL;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

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
     * Retrieves the profile picture URL of the user.
     * 
     * @return The profile picture URL of the user.
     */
    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    /**
     * Sets the profile picture URL of the user.
     * 
     * @param profilePictureURL The profile picture URL of the user.
     */
    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    /**
     * Retrieves the roles of the user.
     * 
     * @return The roles of the user.
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Sets the roles of the user.
     * 
     * @param roles The roles of the user.
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
