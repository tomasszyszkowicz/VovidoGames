package com.games.fun.fun_games.controller;

import com.games.fun.fun_games.dto.PostDto;
import com.games.fun.fun_games.entity.Post;
import com.games.fun.fun_games.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling HTTP requests related to posts.
 */
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Get a list of posts within the specified range.
     *
     * @param bottom The starting index of the range (default: 0)
     * @param top    The ending index of the range (default: 9)
     * @return A ResponseEntity containing the list of posts and the HTTP status code
     */
    @GetMapping
    public ResponseEntity<List<Post>> getPosts(@RequestParam(defaultValue = "0") int bottom,
                                               @RequestParam(defaultValue = "9") int top) {
        // Validate input parameters
        if (bottom < 0 || top < 0) {
            return ResponseEntity.badRequest().build();
        }

        List<Post> posts = postService.getPosts(bottom, top);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    /**
     * Get a post by its ID.
     *
     * @param id The ID of the post
     * @return A ResponseEntity containing the post and the HTTP status code
     */
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        Post post = postService.getPost(id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(post);
    }

    /**
     * Create a new post.
     *
     * @param postDto The DTO object containing the post data
     * @return A ResponseEntity containing the created post and the HTTP status code
     */
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostDto postDto) {
        Post newPost = postService.createPost(postDto);
        if (newPost == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    /**
     * Delete a post by its ID.
     *
     * @param id The ID of the post to delete
     * @return A ResponseEntity containing a success message and the HTTP status code
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }
}
