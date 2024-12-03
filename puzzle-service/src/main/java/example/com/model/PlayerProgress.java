package example.com.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "player_progress")
public class PlayerProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game; // The game this progress is associated with

    @ManyToOne
    @JoinColumn(name = "puzzle_id", nullable = false)
    private Puzzle puzzle; // The puzzle this progress is associated with

    @Column(nullable = false)
    private String playerUsername; // Tracks the player (can be linked to a User entity if applicable)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProgressStatus status; // IN_PROGRESS, COMPLETED, FAILED

    @Column(name = "attempts")
    private int attempts; // Number of attempts the player has made on the puzzle

    @Column(name = "hints_used")
    private int hintsUsed; // Number of hints the player has used

    @Column(name = "completion_time")
    private LocalDateTime completionTime; // Timestamp when the player completed the puzzle

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
}
