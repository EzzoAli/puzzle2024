package example.com.strategy;

import example.com.service.PuzzleStrategy;
import org.springframework.stereotype.Component;

@Component
public class WordSearchPuzzleStrategy implements PuzzleStrategy {

    @Override
    public boolean validateSolution(Long puzzleId, String solution) {
        // Logic for validating a Word Search puzzle
        return solution.equals("VALID_WORD"); // Replace with actual validation logic
    }

    @Override
    public String generatePuzzleData(Long puzzleId) {
        // Logic for generating Word Search puzzle data
        return "Generated Word Search Puzzle Data";
    }
}
