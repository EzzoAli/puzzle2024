package example.com.service;

import example.com.model.Puzzle;
import example.com.model.PuzzleDifficulty;
import example.com.repository.PuzzleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PuzzleService {

    private final PuzzleRepository puzzleRepository;
    private final PuzzleStrategyContext strategyContext;

    public PuzzleService(PuzzleRepository puzzleRepository, PuzzleStrategyContext strategyContext) {
        this.puzzleRepository = puzzleRepository;
        this.strategyContext = strategyContext;
    }

    /**
     * Finds puzzles by difficulty level.
     *
     * @param difficulty The difficulty level (EASY, MEDIUM, HARD).
     * @return A list of puzzles matching the specified difficulty.
     */
    public List<Puzzle> findPuzzlesByDifficulty(PuzzleDifficulty difficulty) {
        return puzzleRepository.findByDifficulty(difficulty);
    }

    /**
     * Validates the solution for a specific puzzle.
     *
     * @param puzzleId The ID of the puzzle.
     * @param solution The proposed solution.
     * @return true if the solution is valid, false otherwise.
     */
    public boolean validatePuzzleSolution(Long puzzleId, String solution) {
        PuzzleStrategy strategy = strategyContext.getStrategy(puzzleId);
        return strategy.validateSolution(puzzleId, solution);
    }

    /**
     * Generates data for a specific puzzle.
     *
     * @param puzzleId The ID of the puzzle.
     * @return The puzzle data as a string.
     */
    public String generatePuzzleData(Long puzzleId) {
        PuzzleStrategy strategy = strategyContext.getStrategy(puzzleId);
        return strategy.generatePuzzleData(puzzleId);
    }

    /**
     * Saves a new puzzle or updates an existing puzzle.
     *
     * @param puzzle The puzzle to save or update.
     * @return The saved puzzle.
     */
    public Puzzle savePuzzle(Puzzle puzzle) {
        return puzzleRepository.save(puzzle);
    }

    /**
     * Finds a puzzle by its ID.
     *
     * @param puzzleId The ID of the puzzle.
     * @return An optional puzzle.
     */
    public Optional<Puzzle> findPuzzleById(Long puzzleId) {
        return puzzleRepository.findById(puzzleId);
    }
}
