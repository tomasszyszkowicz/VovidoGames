package com.games.fun.fun_games.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.entity.SnakeResult;

@Service
public class RecordService {

    private final UserService userService;
    private final SnakeResultService snakeResultService;

    @Autowired
    public RecordService(UserService userService, SnakeResultService snakeResultService) {
        this.userService = userService;
        this.snakeResultService = snakeResultService;
    }

    public String getRecordsByUsername(String username) {
        User user = userService.getUserByUsername(username);
        SnakeResult snakeResult = snakeResultService.getBestResultByUser(user);
        int snakeHighScore = snakeResult.getScore();
        return "Snake high score: " + snakeHighScore;
    }


    
}
