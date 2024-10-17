package example.com.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "rooms")
public class RoomServiceModel {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
    private int difficultyLevel;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomStatus status;

    @Column(nullable = false)
    private int maxPlayers = 2; // Maximum number of players in the room

    @Setter
    @ManyToMany
    @JoinTable(
            name = "room_users", // Join table name
            joinColumns = @JoinColumn(name = "room_id"), // Column in the join table referencing the room
            inverseJoinColumns = @JoinColumn(name = "user_id") // Column in the join table referencing the user
    )
    private List<UserServiceModel> players = new ArrayList<>(); // Initialize the players list

    // Default constructor
    public RoomServiceModel() {
        // Initialize players to avoid null references
        this.players = new ArrayList<>();
    }

    // Parameterized constructor
    public RoomServiceModel(String name, int difficultyLevel, RoomStatus status) {
        this.name = name;
        this.difficultyLevel = difficultyLevel;
        this.status = status;
        this.players = new ArrayList<>(); // Initialize players list
    }

    @Override
    public String toString() {
        return "RoomServiceModel{" +
                "roomId=" + roomId +
                ", name='" + name + '\'' +
                ", difficultyLevel=" + difficultyLevel +
                ", status=" + status +
                ", maxPlayers=" + maxPlayers +
                ", players=" + players +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoomServiceModel)) return false;
        RoomServiceModel that = (RoomServiceModel) o;
        return roomId != null && roomId.equals(that.roomId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
