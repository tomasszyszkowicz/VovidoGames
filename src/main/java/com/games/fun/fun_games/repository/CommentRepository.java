package com.games.fun.fun_games.repository;

import com.games.fun.fun_games.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Repository interface for managing comments in the database.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Retrieves a list of comments for a specific post.
     *
     * @param postId    the ID of the post
     * @param pageable  the pageable object for pagination
     * @return          a list of comments for the specified post
     */
    List<Comment> findByPostId(Long postId, Pageable pageable);
}
