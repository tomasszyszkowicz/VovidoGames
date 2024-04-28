package com.games.fun.fun_games.service;

import com.games.fun.fun_games.entity.JumpResult;
import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.dto.JumpResultDto;
import com.games.fun.fun_games.repository.JumpResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.Comparator;

/**
 * Service class for managing SnakeResult entities.
 */
@Service
public class JumpResultService {

    private final JumpResultRepository jumpResultRepository;
    private final UserService userService;

    @Autowired
    public JumpResultService(JumpResultRepository jumpResultService, UserService userService) {
        this.jumpResultRepository = jumpResultService;
        this.userService = userService;
    }

    /**
     * Retrieves a list of JumpResult entities within the specified range.
     *
     * @param bottom the bottom index of the range (inclusive)
     * @param top    the top index of the range (inclusive)
     * @return a list of JumpResult entities
     */
    public List<JumpResult> getResults(int bottom, int top) {
        int size = top - bottom + 1;
        int pageNumber = bottom / size;
        Sort sort = Sort.by(Sort.Direction.DESC, "score");
        PageRequest pageRequest = PageRequest.of(pageNumber, size, sort);
        return jumpResultRepository.findAll(pageRequest).getContent();
    }

    /**
     * Retrieves a list of JumpResult entities representing the highest scores per user within the specified range.
     *
     * @param bottom the bottom index of the range (inclusive)
     * @param top    the top index of the range (inclusive)
     * @return a list of SnakeResult entities
     */
    public List<JumpResult> getRecords(int bottom, int top) {
        List<JumpResult> allResults = jumpResultRepository.findAll();

        // Group by user and find the maximum score per user
        List<JumpResult> filteredResults = allResults.stream()
            .collect(Collectors.groupingBy(
                JumpResult::getUser,
                Collectors.maxBy(Comparator.comparing(JumpResult::getScore))
            ))
            .values()
            .stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .sorted(Comparator.comparing(JumpResult::getScore).reversed()) // Sort by score DESC
            .collect(Collectors.toList());

        int fromIndex = Math.min(bottom, filteredResults.size());
        int toIndex = Math.min(top + 1, filteredResults.size());

        return filteredResults.subList(fromIndex, toIndex);
    }

    /**
     * Creates a new JumpResult entity based on the provided JumpResultDto.
     *
     * @param resultDto the JumpResultDto containing the result details
     * @return the created JumpResult entity
     */
    public JumpResult createResult(JumpResultDto resultDto) {
        User user = userService.getUserByUsername(resultDto.getUsername());
        if (user == null) {
            return null;
        }
        return jumpResultRepository.save(new JumpResult(user, resultDto.getScore()));
    }

    /**
     * Retrieves the best JumpResult entity for the specified user.
     *
     * @param user the user for which to retrieve the best result
     * @return the best JumpResult entity for the user, or null if no results found
     */
    public JumpResult getBestResultByUser(User user) {
        PageRequest pageRequest = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "score"));
        Page<JumpResult> resultPage = jumpResultRepository.findTopResultByUser(user, pageRequest);
        return resultPage.stream().findFirst().orElse(null);  // returns null if no results
    }

    /**
     * Retrieves the overall best JumpResult entity.
     *
     * @return the overall best JumpResult entity, or null if no results found
     */
    public JumpResult getOverallBestResult() {
        List<JumpResult> results = getResults(0, 0);
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }
}