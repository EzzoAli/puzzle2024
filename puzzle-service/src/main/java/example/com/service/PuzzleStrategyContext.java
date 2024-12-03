package example.com.service;

import example.com.model.Puzzle;
import example.com.repository.PuzzleRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PuzzleStrategyContext {

    private final Map<String, PuzzleStrategy> strategies; // Mapping of puzzle types to strategies
    private final PuzzleRepository puzzleRepository;

    public PuzzleStrategyContext(Map<String, PuzzleStrategy> strategies, PuzzleRepository puzzleRepository) {
        this.strategies = strategies;
        this.puzzleRepository = puzzleRepository;
    }

    /**
     * Returns the appropriate strategy for the given puzzle ID.
     *
     * @param puzzleId The ID of the puzzle.
     * @return The corresponding PuzzleStrategy.
     */
    public PuzzleStrategy getStrategy(Long puzzleId) {
        // Fetch the puzzle from the repository
        Puzzle puzzle = puzzleRepository.findById(puzzleId)
                .orElseThrow(() -> new IllegalArgumentException("Puzzle not found with ID: " + puzzleId));

        // Use the class name (or discriminator column) to identify the puzzle type
        String puzzleType = puzzle.getClass().getSimpleName();

        // Fetch the strategy for the puzzle type
        PuzzleStrategy strategy = strategies.get(puzzleType);

        if (strategy == null) {
            throw new IllegalArgumentException("No strategy found for puzzle type: " + puzzleType);
        }

        return strategy;
    }
}
