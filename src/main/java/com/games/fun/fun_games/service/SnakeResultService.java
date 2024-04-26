package com.games.fun.fun_games.service;

import com.games.fun.fun_games.entity.SnakeResult;
import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.dto.SnakeResultDto;
import com.games.fun.fun_games.repository.SnakeResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;

@Service
public class SnakeResultService {

    private final SnakeResultRepository snakeResultRepository;
    private final UserService userService;

    @Autowired
    public SnakeResultService(SnakeResultRepository snakeResultRepository, UserService userService) {
        this.snakeResultRepository = snakeResultRepository;
        this.userService = userService;
    }

    public List<SnakeResult> getResults(int bottom, int top) {
        int size = top - bottom + 1;
        int pageNumber = bottom / size;
        Sort sort = Sort.by(Sort.Direction.DESC, "score");
        PageRequest pageRequest = PageRequest.of(pageNumber, size, sort);
        return snakeResultRepository.findAll(pageRequest).getContent();
    }

    public SnakeResult createResult(SnakeResultDto resultDto) {
        User user = userService.getUserByUsername(resultDto.getUsername());
        if (user == null) {
            return null;
        }
        return snakeResultRepository.save(new SnakeResult(user, resultDto.getScore()));
    }

    public SnakeResult getBestResultByUser(User user) {
        PageRequest pageRequest = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "score"));
        Page<SnakeResult> resultPage = snakeResultRepository.findTopResultByUser(user, pageRequest);
        return resultPage.stream().findFirst().orElse(null);  // returns null if no results
    }

    public SnakeResult getOverallBestResult() {
        List<SnakeResult> results = getResults(0, 0);
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }
}
