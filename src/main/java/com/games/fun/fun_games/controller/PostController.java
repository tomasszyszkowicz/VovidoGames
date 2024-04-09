package com.games.fun.fun_games.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.PageRequest;

import com.games.fun.fun_games.repository.PostRepository;
import com.games.fun.fun_games.repository.UserRepository;
import com.games.fun.fun_games.entity.Post;
import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.dto.PostDto;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;

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
    public ResponseEntity<List<Post>> getPosts(@RequestParam(defaultValue = "0") int bottom,
                                           @RequestParam(defaultValue = "9") int top) {
        // Validate input parameters
        if (bottom < 0 || top <= 0 || top < bottom) {
            return ResponseEntity.badRequest().build();
        }

        // Calculate the number of results to fetch (size) and the page number
        int size = top - bottom + 1; // This ensures we get the range from bottom to top
        int pageNumber = bottom / size; // Calculate the page number starting from 0

        // Create Pageable object for pagination and sorting
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.DESC, "dateCreated"));

        // Retrieve posts from the repository based on the Pageable object
        List<Post> posts = postRepository.findAll(pageable).getContent();

        // Return the list of posts
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
