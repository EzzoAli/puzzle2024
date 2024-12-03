package example.com.repository;

import example.com.model.PlayerProgress;
import example.com.model.ProgressStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerProgressRepository extends JpaRepository<PlayerProgress, Long> {
    // Find progress for a specific game
    List<PlayerProgress> findByGame_Id(Long gameId);

    // Find progress for a specific puzzle
    List<PlayerProgress> findByPuzzle_Id(Long puzzleId);

    // Find progress for a specific player in a specific game
    List<PlayerProgress> findByGame_IdAndPlayerUsername(Long gameId, String playerUsername);

    // Find progress by status (e.g., COMPLETED, IN_PROGRESS)
    List<PlayerProgress> findByStatus(ProgressStatus status);
}
