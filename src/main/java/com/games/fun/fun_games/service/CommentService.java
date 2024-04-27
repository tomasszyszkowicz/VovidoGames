package com.games.fun.fun_games.service;

import com.games.fun.fun_games.entity.Comment;
import com.games.fun.fun_games.entity.Post;
import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    /**
     * Retrieves all comments within the specified range.
     *
     * @param bottom the starting index of the range (inclusive)
     * @param top    the ending index of the range (inclusive)
     * @return a list of comments within the specified range
     */
    public List<Comment> getAllComments(int bottom, int top) {
        int size = top - bottom + 1;
        int pageNumber = bottom / size;
        return commentRepository.findAll(PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.ASC, "dateCreated"))).getContent();
    }

    /**
     * Retrieves comments for a specific post within the specified range.
     *
     * @param postId the ID of the post
     * @param bottom the starting index of the range (inclusive)
     * @param top    the ending index of the range (inclusive)
     * @return a list of comments for the specified post within the specified range
     */
    public List<Comment> getCommentsByPostId(Long postId, int bottom, int top) {
        int size = top - bottom + 1;
        int pageNumber = bottom / size;
        return commentRepository.findByPostId(postId, PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.ASC, "dateCreated")));
    }

    /**
     * Creates a new comment for a post.
     *
     * @param username the username of the user creating the comment
     * @param postId   the ID of the post
     * @param content  the content of the comment
     * @return the created comment, or null if the user or post does not exist
     */
    public Comment createComment(String username, Long postId, String content) {
        User user = userService.getUserByUsername(username);
        Post post = postService.getPost(postId);

        if (user == null || post == null) {
            return null;
        }

        LocalDateTime dateCreated = LocalDateTime.now();
        return commentRepository.save(new Comment(user, post, dateCreated, content));
    }

    /**
     * Deletes a comment by its ID.
     *
     * @param id the ID of the comment to delete
     */
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
