package com.games.fun.fun_games.repository;

import com.games.fun.fun_games.entity.JumpResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.games.fun.fun_games.entity.User;

/**
 * The JumpResultRepository interface provides methods to interact with the JumpResult entity in the database.
 */
public interface JumpResultRepository extends JpaRepository<JumpResult, Long> {

    /**
     * Retrieves the top JumpResult records for a given user, ordered by score in descending order.
     *
     * @param user     The user for which to retrieve the top results.
     * @param pageable The pagination information.
     * @return A Page of JumpResult objects representing the top results for the user.
     */
    @Query("SELECT sr FROM JumpResult sr WHERE sr.user = :user ORDER BY sr.score DESC")
    Page<JumpResult> findTopResultByUser(User user, Pageable pageable);
}