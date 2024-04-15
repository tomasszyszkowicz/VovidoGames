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

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
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

    public Comment createComment(User user, Post post, LocalDateTime dateCreated, String content) {
        return commentRepository.save(new Comment(user, post, dateCreated, content));
    }
}

