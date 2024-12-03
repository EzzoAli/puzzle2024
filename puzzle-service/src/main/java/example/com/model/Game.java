package example.com.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Name of the game session

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameStatus status; // ACTIVE, COMPLETED, FAILED

    @ManyToMany
    @JoinTable(
            name = "game_puzzles", // Name of the join table
            joinColumns = @JoinColumn(name = "game_id"), // Foreign key for Game
            inverseJoinColumns = @JoinColumn(name = "puzzle_id") // Foreign key for Puzzle
    )
    private List<Puzzle> puzzles = new ArrayList<>(); // List of puzzles associated with the game

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Convenience methods for managing puzzles
    public void addPuzzle(Puzzle puzzle) {
        this.puzzles.add(puzzle);
    }

    public void removePuzzle(Puzzle puzzle) {
        this.puzzles.remove(puzzle);
    }
}
