package com.games.fun.fun_games.repository;

import com.games.fun.fun_games.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    
}


