package com.games.fun.fun_games.repository;

import com.games.fun.fun_games.entity.PexesoResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PexesoResultRepository extends JpaRepository<PexesoResult, Long> {

    Page<PexesoResult> findByDifficulty(int difficulty, Pageable pageable);
    
}