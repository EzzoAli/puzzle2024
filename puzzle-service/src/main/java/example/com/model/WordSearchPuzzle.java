package example.com.model;

import jakarta.persistence.Entity;

@Entity
public class WordSearchPuzzle extends Puzzle {

    private String gridData; // Specific data for Word Search puzzles

    @Override
    public boolean validateSolution(String solution) {
        // WordSearch-specific validation logic
        return solution.equals("VALID_WORD");
    }

    @Override
    public String generatePuzzleData() {
        // WordSearch-specific puzzle generation logic
        return "Generated Grid Data";
    }
}
