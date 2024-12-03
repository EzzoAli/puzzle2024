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

    // Find puzzles by roomId (matches RoomServiceModel's primary key field)
    List<Puzzle> findByRoom_RoomId(Long roomId);
}

