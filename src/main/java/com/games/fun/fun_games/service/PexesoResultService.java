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

    public Map<String, PexesoResult> getLowestHighscoresByDifficultyMapped() {
        List<PexesoResult> results = pexesoResultRepository.findLowestHighscoresByDifficulty();
        Map<String, PexesoResult> mappedResults = new HashMap<>();
        results.forEach(result -> {
            String key;
            switch (result.getDifficulty()) {
                case 1:
                    key = "easy";
                    break;
                case 2:
                    key = "medium";
                    break;
                case 3:
                    key = "hard";
                    break;
                default:
                    key = "unknown";
            }
            mappedResults.put(key, result);
        });
        return mappedResults;
    }

    public PexesoResult createResult(String username, int score, int difficulty) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return null; // Or handle error appropriately
        }
        return pexesoResultRepository.save(new PexesoResult(user, score, difficulty));
    }
}
