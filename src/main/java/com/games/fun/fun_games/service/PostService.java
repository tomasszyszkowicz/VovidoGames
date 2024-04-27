package com.games.fun.fun_games.service;

import com.games.fun.fun_games.entity.Post;
import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.dto.PostDto;
import com.games.fun.fun_games.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for managing posts.
 */
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    /**
     * Constructor for PostService.
     *
     * @param postRepository The post repository.
     * @param userService The user service.
     */
    @Autowired
    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    /**
     * Retrieves a list of posts within the specified range.
     *
     * @param bottom The bottom index of the range.
     * @param top The top index of the range.
     * @return The list of posts within the specified range.
     */
    public List<Post> getPosts(int bottom, int top) {
        int size = top - bottom + 1;
        int pageNumber = bottom / size;
        Sort sort = Sort.by(Sort.Direction.DESC, "dateCreated");
        PageRequest pageRequest = PageRequest.of(pageNumber, size, sort);
        return postRepository.findAll(pageRequest).getContent();
    }    

    /**
     * Retrieves a post by its ID.
     *
     * @param id The ID of the post.
     * @return The post with the specified ID, or null if not found.
     */
    public Post getPost(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    /**
     * Creates a new post.
     *
     * @param postDto The post DTO containing the post details.
     * @return The created post.
     */
    public Post createPost(PostDto postDto) {
        User user = userService.getUserByUsername(postDto.getUsername());
        if (user == null) {
            return null;
        }
        LocalDateTime dateCreated = LocalDateTime.now();
        return postRepository.save(new Post(user, dateCreated, postDto.getTitle(), postDto.getContent()));
    }

    /**
     * Deletes a post by its ID.
     *
     * @param id The ID of the post to delete.
     */
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}

