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

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Autowired
    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public List<Post> getPosts(int bottom, int top) {
        int size = top - bottom + 1;
        int pageNumber = bottom / size;
        Sort sort = Sort.by(Sort.Direction.DESC, "dateCreated");
        PageRequest pageRequest = PageRequest.of(pageNumber, size, sort);
        return postRepository.findAll(pageRequest).getContent();
    }    

    public Post getPost(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post createPost(PostDto postDto) {
        User user = userService.getUserByUsername(postDto.getUsername());
        if (user == null) {
            return null;
        }
        LocalDateTime dateCreated = LocalDateTime.now();
        return postRepository.save(new Post(user, dateCreated, postDto.getTitle(), postDto.getContent()));
    }
}

