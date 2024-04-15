package com.games.fun.fun_games.controller;

import com.games.fun.fun_games.dto.CommentDto;
import com.games.fun.fun_games.entity.Comment;
import com.games.fun.fun_games.entity.Post;
import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.service.CommentService;
import com.games.fun.fun_games.service.PostService;
import com.games.fun.fun_games.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public CommentController(CommentService commentService, UserService userService, PostService postService) {
        this.commentService = commentService;
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getComments(
            @RequestParam(name = "postId", required = false) Long postId,
            @RequestParam(defaultValue = "0") int bottom,
            @RequestParam(defaultValue = "9") int top
    ) {
        if (bottom < 0 || top <= 0 || top < bottom) {
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
        User user = userService.getUserByUsername(commentDto.getUsername());
        Post post = postService.getPost(commentDto.getPostId());

        if (user == null || post == null) {
            return ResponseEntity.badRequest().build();
        }

        LocalDateTime dateCreated = LocalDateTime.now();
        Comment newComment = commentService.createComment(user, post, dateCreated, commentDto.getContent());
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }
}
