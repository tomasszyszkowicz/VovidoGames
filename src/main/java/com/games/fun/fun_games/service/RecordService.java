package com.games.fun.fun_games.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.entity.SnakeResult;
import com.games.fun.fun_games.entity.PexesoResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class for managing game records.
 */
@Service
public class RecordService {

    private final UserService userService;
    private final SnakeResultService snakeResultService;
    private final PexesoResultService pexesoResultService;

    @Autowired
    public RecordService(UserService userService, SnakeResultService snakeResultService,
            PexesoResultService pexesoResultService) {
        this.userService = userService;
        this.snakeResultService = snakeResultService;
        this.pexesoResultService = pexesoResultService;
    }

    /**
     * Retrieves the game records for a specific user.
     *
     * @param username The username of the user.
     * @return A map containing the game records.
     */
    public Map<String, Object> getRecordsByUsername(String username) {
        Map<String, Object> response = new HashMap<>();
        User user = userService.getUserByUsername(username);

        if (user == null) {
            response.put("error", "No user found with username: " + username);
            return response;
        }

        response.put("User", user.getUsername()); // Adds username to the response.

        // Fetching and adding the best Snake result.
        SnakeResult snakeResult = snakeResultService.getBestResultByUser(user);
        if (snakeResult != null) {
            response.put("snakeHighScore", snakeResult.getScore());
        } else {
            response.put("snakeHighScore", "No results");
        }

        // Fetching and adding the best Pexeso results for all difficulties.
        Map<String, PexesoResult> pexesoResults = pexesoResultService.getBestResultsByUser(user);
        pexesoResults.forEach((difficulty, result) -> {
            if (result != null) {
                response.put("pexesoHighScore_" + difficulty, result.getScore());
            } else {
                response.put("pexesoHighScore_" + difficulty, "No results");
            }
        });

        return response;
    }

    /**
     * Retrieves the overall game records.
     *
     * @return A map containing the overall game records.
     */
    public Map<String, Object> getRecords() {
        Map<String, Object> response = new HashMap<>();
        
        SnakeResult snakeResult = snakeResultService.getOverallBestResult();
        if (snakeResult != null) {
            response.put("snake", snakeResult);
        } else {
            response.put("snake", "No results");
        }

        Map<String, PexesoResult> pexesoResults = pexesoResultService.getOverallBestResults();

        response.putAll(pexesoResults);
        return response;
    }
}
