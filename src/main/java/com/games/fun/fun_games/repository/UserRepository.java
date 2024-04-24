package com.games.fun.fun_games.repository;

import com.games.fun.fun_games.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * This interface represents a repository for managing User entities.
 * It extends the JpaRepository interface, providing CRUD operations for User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find
     * @return the User entity if found, null otherwise
     */
    User findByUsername(String username);

    /**
     * Finds all users whose username contains the specified string.
     *
     * @param query the string to search for in the usernames
     * @return a list of User entities whose usernames contain the specified string
     */
    List<User> findByUsernameContaining(String query);

    /**
     * Deletes a user by their username.
     *
     * @param username the username of the user to delete
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.username = ?1")
    void deleteByUsername(String username);
}
