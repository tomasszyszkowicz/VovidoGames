package com.games.fun.fun_games.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.games.fun.fun_games.repository.CommentRepository;
import com.games.fun.fun_games.entity.Comment;
import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.dto.CommentDto;
import com.games.fun.fun_games.repository.UserRepository;
import com.games.fun.fun_games.repository.PostRepository;
import com.games.fun.fun_games.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public ResponseEntity<List<Comment>> getComments(
        @RequestParam(name = "postId", required = false) Long postId,
        @RequestParam(defaultValue = "0") int bottom,
        @RequestParam(defaultValue = "9") int top
    ) {
        // Validate input parameters
        if (bottom < 0 || top <= 0 || top < bottom) {
            return ResponseEntity.badRequest().build();
        }

        // Calculate the number of results to fetch (size) and the page number
        int size = top - bottom + 1; // This ensures we get the range from bottom to top
        int pageNumber = bottom / size; // Calculate the page number starting from 0

        // Create Pageable object for pagination and sorting
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.ASC, "dateCreated"));

        List<Comment> comments;

        // Retrieve comments based on postId if provided, otherwise retrieve all comments
        if (postId != null) {
            comments = commentRepository.findByPostId(postId, pageable);
        } else {
            comments = commentRepository.findAll(pageable).getContent();
        }

        // Return the list of comments
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentDto comment) {
        User user = userRepository.findByUsername(comment.getUsername());
        Post post = postRepository.findById(comment.getPostId()).orElse(null);
        if (user == null || post == null) {
            return ResponseEntity.badRequest().build();
        }
        LocalDateTime dateCreated = LocalDateTime.now();
        Comment newComment = commentRepository.save(new Comment(user, post, dateCreated, comment.getContent()));
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }
}
