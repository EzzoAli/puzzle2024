package example.com.validation;

import example.com.model.Puzzle;
import example.com.repository.PuzzleRepository;
import example.com.service.PuzzleStrategy;
import example.com.service.PuzzleStrategyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PuzzleValidator {

    private final PuzzleRepository puzzleRepository;
    private final PuzzleStrategyContext strategyContext;

    @Autowired
    public PuzzleValidator(PuzzleRepository puzzleRepository, PuzzleStrategyContext strategyContext) {
        this.puzzleRepository = puzzleRepository;
        this.strategyContext = strategyContext;
    }

    /**
     * Validates a solution for a specific puzzle.
     *
     * @param puzzleId The ID of the puzzle.
     * @param solution The player's proposed solution.
     * @return true if the solution is valid, false otherwise.
     */
    public boolean validateSolution(Long puzzleId, String solution) {
        Optional<Puzzle> puzzleOpt = puzzleRepository.findById(puzzleId);

        if (puzzleOpt.isEmpty()) {
            throw new IllegalArgumentException("Puzzle not found with ID: " + puzzleId);
        }

        PuzzleStrategy strategy = strategyContext.getStrategy(puzzleId);
        return strategy.validateSolution(puzzleId, solution);
    }

    /**
     * Checks if the puzzle solution is correct and updates progress if applicable.
     *
     * @param puzzleId The ID of the puzzle.
     * @param solution The player's proposed solution.
     * @return A message indicating the result.
     */
    public String checkAndUpdateProgress(Long puzzleId, String solution) {
        boolean isValid = validateSolution(puzzleId, solution);
        return isValid ? "Correct solution!" : "Incorrect solution. Try again!";
    }
}
