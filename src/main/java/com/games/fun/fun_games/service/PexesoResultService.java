package com.games.fun.fun_games.service;

import com.games.fun.fun_games.entity.PexesoResult;
import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.repository.PexesoResultRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PexesoResultService {

    private final PexesoResultRepository pexesoResultRepository;
    private final UserService userService;

    @Autowired
    public PexesoResultService(PexesoResultRepository pexesoResultRepository, UserService userService) {
        this.pexesoResultRepository = pexesoResultRepository;
        this.userService = userService;
    }

    public List<PexesoResult> getResults(int bottom, int top, int difficulty) {
        int size = top - bottom + 1;
        int pageNumber = bottom / size;
        Sort sort = Sort.by(Sort.Direction.ASC, "score");
        PageRequest pageRequest = PageRequest.of(pageNumber, size, sort);
        return pexesoResultRepository.findByDifficulty(difficulty, pageRequest).getContent();
    }

    public PexesoResult createResult(String username, int score, int difficulty) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return null; // Or handle error appropriately
        }
        return pexesoResultRepository.save(new PexesoResult(user, score, difficulty));
    }

    public Map<String, PexesoResult> getBestResultsByUser(User user) {
        Map<String, PexesoResult> bestResults = new HashMap<>();
        int[] difficulties = {1, 2, 3}; // Assuming difficulties are 1 (easy), 2 (medium), 3 (hard)
        PageRequest pageRequest = PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC, "score"));

        for (int difficulty : difficulties) {
            PexesoResult result = pexesoResultRepository.findTopResultByUserAndDifficulty(user, difficulty, pageRequest)
                                                       .stream().findFirst().orElse(null);
            String key = switch (difficulty) {
                case 1 -> "easy";
                case 2 -> "medium";
                case 3 -> "hard";
                default -> "unknown";
            };
            bestResults.put(key, result);
        }

        return bestResults;
    }
}
