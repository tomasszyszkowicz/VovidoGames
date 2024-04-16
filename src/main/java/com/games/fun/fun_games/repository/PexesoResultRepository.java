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

    @Query("SELECT pr FROM PexesoResult pr WHERE pr.score = (SELECT MIN(pr2.score) FROM PexesoResult pr2 WHERE pr2.difficulty = pr.difficulty) ORDER BY pr.difficulty ASC")
    List<PexesoResult> findLowestHighscoresByDifficulty(); 

    @Query("SELECT pr FROM PexesoResult pr WHERE pr.user = :user ORDER BY pr.score ASC")
    Page<PexesoResult> findTopResultByUser(User user, Pageable pageable);
}