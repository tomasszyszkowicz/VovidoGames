package com.games.fun.fun_games.controller;

import com.games.fun.fun_games.dto.CommentDto;
import com.games.fun.fun_games.entity.Comment;
import com.games.fun.fun_games.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling comments related endpoints.
 */
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    
    /**
     * Retrieves a list of comments based on the provided parameters.
     * If postId is specified, retrieves comments for a specific post.
     * If postId is not specified, retrieves all comments.
     *
     * @param postId the ID of the post to retrieve comments for (optional)
     * @param bottom the starting index of the comments to retrieve (default: 0)
     * @param top the ending index of the comments to retrieve (default: 9)
     * @return a ResponseEntity containing the list of comments and the HTTP status code
     */
    @GetMapping
    public ResponseEntity<List<Comment>> getComments(
            @RequestParam(name = "postId", required = false) Long postId,
            @RequestParam(defaultValue = "0") int bottom,
            @RequestParam(defaultValue = "9") int top
    ) {
        if (bottom < 0 || top < 0) {
            return ResponseEntity.badRequest().build();
        }

        List<Comment> comments;

        if (postId != null) {
            comments = commentService.getCommentsByPostId(postId, bottom, top);
        } else {
            comments = commentService.getAllComments(bottom, top);
        }

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    /**
     * Creates a new comment based on the provided CommentDto.
     *
     * @param commentDto the CommentDto object containing the comment details
     * @return a ResponseEntity containing the created comment and the HTTP status code
     */
    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentDto commentDto) {
        Comment newComment = commentService.createComment(commentDto.getUsername(), commentDto.getPostId(), commentDto.getContent());
        if (newComment == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }

    /**
     * Deletes a comment with the specified ID.
     *
     * @param id the ID of the comment to delete
     * @return a ResponseEntity containing a success message and the HTTP status code
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}
