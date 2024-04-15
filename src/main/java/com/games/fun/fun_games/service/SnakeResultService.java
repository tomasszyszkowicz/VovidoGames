package com.games.fun.fun_games.service;

import com.games.fun.fun_games.entity.SnakeResult;
import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.repository.SnakeResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SnakeResultService {

    private final SnakeResultRepository snakeResultRepository;

    @Autowired
    public SnakeResultService(SnakeResultRepository snakeResultRepository) {
        this.snakeResultRepository = snakeResultRepository;
    }

    public List<SnakeResult> getResults(int bottom, int top) {
        int size = top - bottom + 1;
        int pageNumber = bottom / size;
        Sort sort = Sort.by(Sort.Direction.DESC, "score");
        PageRequest pageRequest = PageRequest.of(pageNumber, size, sort);
        return snakeResultRepository.findAll(pageRequest).getContent();
    }

    public SnakeResult createResult(User user, int score) {
        return snakeResultRepository.save(new SnakeResult(user, score));
    }
}