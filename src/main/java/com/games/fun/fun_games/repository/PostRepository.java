package com.games.fun.fun_games.repository;

import com.games.fun.fun_games.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Post entities.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    /**
     * Retrieves a post by its ID.
     *
     * @param id the ID of the post to retrieve
     * @return an Optional containing the post, or an empty Optional if not found
     */
    Optional<Post> findById(Long id);
}
