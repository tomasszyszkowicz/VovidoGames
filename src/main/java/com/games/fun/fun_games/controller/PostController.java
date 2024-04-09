package com.games.fun.fun_games.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.games.fun.fun_games.repository.PostRepository;
import com.games.fun.fun_games.repository.UserRepository;
import com.games.fun.fun_games.entity.Post;
import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.dto.PostDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {
        List<Post> posts = postRepository.findAll();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostDto post) {
        System.out.println(post.getUsername());
        User user = userRepository.findByUsername(post.getUsername());
        LocalDateTime dateCreated = LocalDateTime.now();
        System.out.println(dateCreated);
        Post newPost = postRepository.save(new Post(user, dateCreated, post.getTitle(), post.getContent()));
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }
}
