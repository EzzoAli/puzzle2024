package example.com.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserServiceModel {

    // Primary key, auto-generated
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User's name, required
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 15, message = "Name should be between 2 and 15 characters")
    @Column(name = "name", nullable = false)
    private String name;

    // User's email, required and must be a valid email format
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // User's password, required, with basic length validation
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Column(name = "password", nullable = false)
    private String password;

    // User role (could be "USER", "ADMIN", etc.)
    @NotBlank(message = "Role is required")
    @Column(name = "role", nullable = false)
    private String role;

    // Timestamp when the user was created
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Timestamp when the user was last updated
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Default constructor
    public UserServiceModel() {
    }

    // Constructor for setting initial values
    public UserServiceModel(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = LocalDateTime.now(); // Automatically set to current date-time
    }

    // Getters and Setters

    // Helper method to update timestamps
    @PreUpdate
    public void setLastUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
