package com.games.fun.fun_games.service;

import com.games.fun.fun_games.entity.PexesoResult;
import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.repository.PexesoResultRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing PexesoResult entities.
 */
@Service
public class PexesoResultService {

    private final PexesoResultRepository pexesoResultRepository;
    private final UserService userService;

    @Autowired
    public PexesoResultService(PexesoResultRepository pexesoResultRepository, UserService userService) {
        this.pexesoResultRepository = pexesoResultRepository;
        this.userService = userService;
    }

    /**
     * Retrieves a list of PexesoResult entities based on the specified criteria.
     *
     * @param bottom     the bottom index of the results to retrieve
     * @param top        the top index of the results to retrieve
     * @param difficulty the difficulty level of the results to retrieve
     * @return a list of PexesoResult entities
     */
    public List<PexesoResult> getResults(int bottom, int top, int difficulty) {
        int size = top - bottom + 1;
        int pageNumber = bottom / size;
        Sort sort = Sort.by(Sort.Direction.ASC, "score");
        PageRequest pageRequest = PageRequest.of(pageNumber, size, sort);
        return pexesoResultRepository.findByDifficulty(difficulty, pageRequest).getContent();
    }

    /**
     * Retrieves a list of PexesoResult entities representing the records for each user based on the specified criteria.
     *
     * @param bottom     the bottom index of the records to retrieve
     * @param top        the top index of the records to retrieve
     * @param difficulty the difficulty level of the records to retrieve
     * @return a list of PexesoResult entities representing the records
     */
    public List<PexesoResult> getRecords(int bottom, int top, int difficulty) {
        // Fetch all data filtered by difficulty
        List<PexesoResult> allResults = pexesoResultRepository.findByDifficulty(difficulty);

        // Filter to find minimum scores per user and sort them
        List<PexesoResult> filteredResults = allResults.stream()
                .collect(Collectors.groupingBy(
                        PexesoResult::getUser,
                        Collectors.minBy(Comparator.comparing(PexesoResult::getScore))
                ))
                .values()
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted(Comparator.comparing(PexesoResult::getScore))
                .collect(Collectors.toList());

        int fromIndex = Math.min(bottom, filteredResults.size());
        int toIndex = Math.min(top + 1, filteredResults.size());

        return filteredResults.subList(fromIndex, toIndex);
    }

    /**
     * Creates a new PexesoResult entity with the specified username, score, and difficulty level.
     *
     * @param username   the username of the user
     * @param score      the score achieved
     * @param difficulty the difficulty level
     * @return the created PexesoResult entity
     */
    public PexesoResult createResult(String username, int score, int difficulty) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return null; // Or handle error appropriately
        }
        return pexesoResultRepository.save(new PexesoResult(user, score, difficulty));
    }

    /**
     * Retrieves the best PexesoResult entities for each difficulty level for the specified user.
     *
     * @param user the user
     * @return a map of difficulty levels mapped to the best PexesoResult entities
     */
    public Map<String, PexesoResult> getBestResultsByUser(User user) {
        Map<String, PexesoResult> bestResults = new HashMap<>();
        int[] difficulties = {1, 2, 3};
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

    /**
     * Retrieves the overall best PexesoResult entities for each difficulty level.
     *
     * @return a map of difficulty levels mapped to the overall best PexesoResult entities
     */
    public Map<String, PexesoResult> getOverallBestResults() {
        Map<String, PexesoResult> resultsMap = new HashMap<>();

        // Retrieve the result for the "easy" level
        List<PexesoResult> easyResults = getResults(0, 0, 1);
        if (easyResults.isEmpty()) {
            resultsMap.put("easy", null);
        } else {
            resultsMap.put("easy", easyResults.get(0));
        }

        // Retrieve the result for the "medium" level
        List<PexesoResult> mediumResults = getResults(0, 0, 2);
        if (mediumResults.isEmpty()) {
            resultsMap.put("medium", null);
        } else {
            resultsMap.put("medium", mediumResults.get(0));
        }

        // Retrieve the result for the "hard" level
        List<PexesoResult> hardResults = getResults(0, 0, 3);
        if (hardResults.isEmpty()) {
            resultsMap.put("hard", null);
        } else {
            resultsMap.put("hard", hardResults.get(0));
        }

        return resultsMap;
    }
}
