package example.com.service;

import example.com.model.Game;
import example.com.model.GameStatus;
import example.com.model.Puzzle;
import example.com.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final PuzzleService puzzleService;

    public GameService(GameRepository gameRepository, PuzzleService puzzleService) {
        this.gameRepository = gameRepository;
        this.puzzleService = puzzleService;
    }

    /**
     * Creates a new game with a set of puzzles.
     *
     * @param gameName The name of the game.
     * @param puzzleIds A list of puzzle IDs to associate with the game.
     * @return The created game.
     */
    public Game createGame(String gameName, List<Long> puzzleIds) {
        Game game = new Game();
        game.setName(gameName);
        game.setStatus(GameStatus.ACTIVE);
        game.setStartTime(LocalDateTime.now());

        // Fetch puzzles by IDs and add them to the game
        List<Puzzle> puzzles = puzzleIds.stream()
                .map(puzzleService::findPuzzleById)
                .flatMap(Optional::stream)
                .toList();
        game.getPuzzles().addAll(puzzles);

        return gameRepository.save(game);
    }

    /**
     * Updates the status of a game.
     *
     * @param gameId The ID of the game.
     * @param status The new status (ACTIVE, COMPLETED, FAILED).
     * @return The updated game.
     */
    public Optional<Game> updateGameStatus(Long gameId, GameStatus status) {
        return gameRepository.findById(gameId).map(game -> {
            game.setStatus(status);
            if (status == GameStatus.COMPLETED || status == GameStatus.FAILED) {
                game.setEndTime(LocalDateTime.now());
            }
            return gameRepository.save(game);
        });
    }

    /**
     * Finds games by their status.
     *
     * @param status The game status (ACTIVE, COMPLETED, FAILED).
     * @return A list of games with the specified status.
     */
    public List<Game> findGamesByStatus(GameStatus status) {
        return gameRepository.findByStatus(status);
    }

    /**
     * Finds a game by its ID.
     *
     * @param gameId The ID of the game.
     * @return An optional game.
     */
    public Optional<Game> findGameById(Long gameId) {
        return gameRepository.findById(gameId);
    }

    /**
     * Adds a puzzle to an existing game.
     *
     * @param gameId   The ID of the game.
     * @param puzzleId The ID of the puzzle to add.
     * @return The updated game.
     */
    public Optional<Game> addPuzzleToGame(Long gameId, Long puzzleId) {
        return gameRepository.findById(gameId).flatMap(game -> {
            Optional<Puzzle> puzzle = puzzleService.findPuzzleById(puzzleId);
            puzzle.ifPresent(game::addPuzzle);
            return Optional.of(gameRepository.save(game));
        });
    }

    /**
     * Removes a puzzle from an existing game.
     *
     * @param gameId   The ID of the game.
     * @param puzzleId The ID of the puzzle to remove.
     * @return The updated game.
     */
    public Optional<Game> removePuzzleFromGame(Long gameId, Long puzzleId) {
        return gameRepository.findById(gameId).flatMap(game -> {
            Optional<Puzzle> puzzle = puzzleService.findPuzzleById(puzzleId);
            puzzle.ifPresent(game::removePuzzle);
            return Optional.of(gameRepository.save(game));
        });
    }
}
