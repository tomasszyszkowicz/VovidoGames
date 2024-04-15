package com.games.fun.fun_games.service;

import com.games.fun.fun_games.entity.Post;
import com.games.fun.fun_games.entity.User;
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

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getPosts(int bottom, int top) {
        int size = top - bottom + 1;
        int pageNumber = bottom / size;
        PageRequest pageRequest = PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.DESC, "dateCreated"));
        return postRepository.findAll(pageRequest).getContent();
    }

    public Post getPost(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post createPost(User user, LocalDateTime dateCreated, String title, String content) {
        return postRepository.save(new Post(user, dateCreated, title, content));
    }
}
