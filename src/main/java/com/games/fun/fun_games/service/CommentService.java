package com.games.fun.fun_games.service;

import com.games.fun.fun_games.entity.Comment;
import com.games.fun.fun_games.entity.Post;
import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<Comment> getAllComments(int bottom, int top) {
        int size = top - bottom + 1;
        int pageNumber = bottom / size;
        return commentRepository.findAll(PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.ASC, "dateCreated"))).getContent();
    }

    public List<Comment> getCommentsByPostId(Long postId, int bottom, int top) {
        int size = top - bottom + 1;
        int pageNumber = bottom / size;
        return commentRepository.findByPostId(postId, PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.ASC, "dateCreated")));
    }

    public Comment createComment(String username, Long postId, String content) {
        User user = userService.getUserByUsername(username);
        Post post = postService.getPost(postId);

        if (user == null || post == null) {
            return null; // Or handle error appropriately
        }

        LocalDateTime dateCreated = LocalDateTime.now();
        return commentRepository.save(new Comment(user, post, dateCreated, content));
    }
}

