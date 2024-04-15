package com.games.fun.fun_games.controller;

import com.games.fun.fun_games.dto.PostDto;
import com.games.fun.fun_games.entity.Post;
import com.games.fun.fun_games.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
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
        Post newPost = postService.createPost(postDto);
        if (newPost == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }
}
