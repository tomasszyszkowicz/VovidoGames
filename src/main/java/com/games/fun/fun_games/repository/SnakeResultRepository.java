package com.games.fun.fun_games.repository;

import com.games.fun.fun_games.entity.SnakeResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.games.fun.fun_games.entity.User;

/**
 * The SnakeResultRepository interface provides methods to interact with the SnakeResult entity in the database.
 */
public interface SnakeResultRepository extends JpaRepository<SnakeResult, Long> {

    /**
     * Retrieves the top SnakeResult records for a given user, ordered by score in descending order.
     *
     * @param user     The user for which to retrieve the top results.
     * @param pageable The pagination information.
     * @return A Page of SnakeResult objects representing the top results for the user.
     */
    @Query("SELECT sr FROM SnakeResult sr WHERE sr.user = :user ORDER BY sr.score DESC")
    Page<SnakeResult> findTopResultByUser(User user, Pageable pageable);
}
