package com.games.fun.fun_games.controller;

import com.games.fun.fun_games.dto.PostDto;
import com.games.fun.fun_games.entity.Post;
import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.service.PostService;
import com.games.fun.fun_games.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getPosts(@RequestParam(defaultValue = "0") int bottom,
                                               @RequestParam(defaultValue = "9") int top) {
        // Validate input parameters
        if (bottom < 0 || top <= 0 || top < bottom) {
            return ResponseEntity.badRequest().build();
        }

        List<Post> posts = postService.getPosts(bottom, top);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        Post post = postService.getPost(id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostDto postDto) {
        User user = userService.getUserByUsername(postDto.getUsername());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        LocalDateTime dateCreated = LocalDateTime.now();
        Post newPost = postService.createPost(user, dateCreated, postDto.getTitle(), postDto.getContent());
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }
}
