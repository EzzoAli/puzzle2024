package example.com.service;

public interface PuzzleStrategy {
    /**
     * Validates the solution for the puzzle.
     *
     * @param puzzleId The ID of the puzzle.
     * @param solution The proposed solution to the puzzle.
     * @return true if the solution is correct, false otherwise.
     */
    boolean validateSolution(Long puzzleId, String solution);

    /**
     * Generates data specific to the puzzle type.
     *
     * @param puzzleId The ID of the puzzle.
     * @return A string representing the puzzle data.
     */
    String generatePuzzleData(Long puzzleId);
}
