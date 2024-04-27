package com.games.fun.fun_games.repository;

import com.games.fun.fun_games.entity.PexesoResult;
import com.games.fun.fun_games.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PexesoResultRepository extends JpaRepository<PexesoResult, Long> {

    Page<PexesoResult> findByDifficulty(int difficulty, Pageable pageable);

    List<PexesoResult> findByDifficulty(int difficulty);

    @Query("SELECT pr FROM PexesoResult pr WHERE pr.user = :user AND pr.difficulty = :difficulty ORDER BY pr.score ASC")
    Page<PexesoResult> findTopResultByUserAndDifficulty(User user, int difficulty, Pageable pageable);
}