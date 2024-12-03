package example.com.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class Puzzle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Name of the puzzle

    @Column(nullable = false)
    private String description; // Short description of the puzzle

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PuzzleDifficulty difficulty; // EASY, MEDIUM, HARD

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private RoomServiceModel room; // The room this puzzle is associated with

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

    // Abstract method to be implemented by specific puzzle types
    public abstract boolean validateSolution(String solution);

    // Abstract method to generate puzzle data (to be overridden by concrete puzzles)
    public abstract String generatePuzzleData();
}
