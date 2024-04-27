package com.games.fun.fun_games.repository;

import com.games.fun.fun_games.entity.PexesoResult;
import com.games.fun.fun_games.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Repository interface for managing PexesoResult entities.
 */
public interface PexesoResultRepository extends JpaRepository<PexesoResult, Long> {

    /**
     * Retrieves a page of PexesoResult entities with the given difficulty.
     *
     * @param difficulty the difficulty level
     * @param pageable   the pagination information
     * @return a page of PexesoResult entities
     */
    Page<PexesoResult> findByDifficulty(int difficulty, Pageable pageable);

    /**
     * Retrieves a list of PexesoResult entities with the given difficulty.
     *
     * @param difficulty the difficulty level
     * @return a list of PexesoResult entities
     */
    List<PexesoResult> findByDifficulty(int difficulty);

    /**
     * Retrieves a page of PexesoResult entities for a specific user and difficulty, ordered by score in ascending order.
     *
     * @param user       the user entity
     * @param difficulty the difficulty level
     * @param pageable   the pagination information
     * @return a page of PexesoResult entities
     */
    @Query("SELECT pr FROM PexesoResult pr WHERE pr.user = :user AND pr.difficulty = :difficulty ORDER BY pr.score ASC")
    Page<PexesoResult> findTopResultByUserAndDifficulty(User user, int difficulty, Pageable pageable);
}
