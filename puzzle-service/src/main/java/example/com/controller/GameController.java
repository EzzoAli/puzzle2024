package example.com.controller;

import example.com.model.Game;
import example.com.model.GameStatus;
import example.com.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Creates a new game with a list of puzzles.
     *
     * @param gameName The name of the game.
     * @param puzzleIds The list of puzzle IDs to associate with the game.
     * @return The created game.
     */
    @PostMapping
    public ResponseEntity<Game> createGame(@RequestParam String gameName, @RequestParam List<Long> puzzleIds) {
        Game game = gameService.createGame(gameName, puzzleIds);
        return ResponseEntity.ok(game);
    }

    /**
     * Updates the status of a game.
     *
     * @param gameId The ID of the game.
     * @param status The new status of the game.
     * @return The updated game.
     */
    @PatchMapping("/{gameId}/status")
    public ResponseEntity<Game> updateGameStatus(@PathVariable Long gameId, @RequestParam GameStatus status) {
        Optional<Game> updatedGame = gameService.updateGameStatus(gameId, status);
        return updatedGame.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Fetches a game by its ID.
     *
     * @param gameId The ID of the game.
     * @return The requested game.
     */
    @GetMapping("/{gameId}")
    public ResponseEntity<Game> getGameById(@PathVariable Long gameId) {
        Optional<Game> game = gameService.findGameById(gameId);
        return game.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Fetches all games with a specific status.
     *
     * @param status The status of the games to fetch.
     * @return A list of games with the specified status.
     */
    @GetMapping
    public ResponseEntity<List<Game>> getGamesByStatus(@RequestParam GameStatus status) {
        List<Game> games = gameService.findGamesByStatus(status);
        return ResponseEntity.ok(games);
    }

    /**
     * Adds a puzzle to an existing game.
     *
     * @param gameId The ID of the game.
     * @param puzzleId The ID of the puzzle to add.
     * @return The updated game.
     */
    @PostMapping("/{gameId}/puzzles")
    public ResponseEntity<Game> addPuzzleToGame(@PathVariable Long gameId, @RequestParam Long puzzleId) {
        Optional<Game> updatedGame = gameService.addPuzzleToGame(gameId, puzzleId);
        return updatedGame.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Removes a puzzle from an existing game.
     *
     * @param gameId The ID of the game.
     * @param puzzleId The ID of the puzzle to remove.
     * @return The updated game.
     */
    @DeleteMapping("/{gameId}/puzzles")
    public ResponseEntity<Game> removePuzzleFromGame(@PathVariable Long gameId, @RequestParam Long puzzleId) {
        Optional<Game> updatedGame = gameService.removePuzzleFromGame(gameId, puzzleId);
        return updatedGame.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
