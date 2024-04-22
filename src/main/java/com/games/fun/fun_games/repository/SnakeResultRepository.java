package com.games.fun.fun_games.repository;

import com.games.fun.fun_games.entity.SnakeResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.games.fun.fun_games.entity.User;


public interface SnakeResultRepository extends JpaRepository<SnakeResult, Long> {
    @Query("SELECT sr FROM SnakeResult sr WHERE sr.user = :user ORDER BY sr.score DESC")
    Page<SnakeResult> findTopResultByUser(User user, Pageable pageable);
}
