package example.com.repository;

import example.com.model.Game;
import example.com.model.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    // Find games by status (e.g., ACTIVE, COMPLETED)
    List<Game> findByStatus(GameStatus status);

    // Find games associated with a specific player (via player progress)
    List<Game> findByPuzzles_Id(Long puzzleId); // Find games linked to specific puzzles
}
