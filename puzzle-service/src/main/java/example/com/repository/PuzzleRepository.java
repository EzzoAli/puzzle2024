package example.com.repository;

import example.com.model.Puzzle;
import example.com.model.PuzzleDifficulty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PuzzleRepository extends JpaRepository<Puzzle, Long> {
    // Find puzzles by difficulty level
    List<Puzzle> findByDifficulty(PuzzleDifficulty difficulty);

    // Find puzzles by type (using the discriminator column)
    List<Puzzle> findByRoom_Id(Long roomId); // Find puzzles linked to a specific room
}
