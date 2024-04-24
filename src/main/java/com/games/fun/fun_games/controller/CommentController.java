package com.games.fun.fun_games.controller;

import com.games.fun.fun_games.dto.CommentDto;
import com.games.fun.fun_games.entity.Comment;
import com.games.fun.fun_games.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

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

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentDto commentDto) {
        Comment newComment = commentService.createComment(commentDto.getUsername(), commentDto.getPostId(), commentDto.getContent());
        if (newComment == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}
