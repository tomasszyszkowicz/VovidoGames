package com.games.fun.fun_games.repository;

import com.games.fun.fun_games.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // Method to find a post by its ID
    Optional<Post> findById(Long id);
}
