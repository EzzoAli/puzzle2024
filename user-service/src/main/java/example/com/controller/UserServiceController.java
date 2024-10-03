package example.com.controller;

import example.com.model.UserServiceModel;
import example.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserServiceController {

    private final UserService userService;

    @Autowired
    public UserServiceController(UserService userService) {
        this.userService = userService;
    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<UserServiceModel> registerUser(@Valid @RequestBody UserServiceModel user) {
        // Call the service to register the user
        UserServiceModel savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }

    // Get a user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserServiceModel> getUserByEmail(@PathVariable String email) {
        Optional<UserServiceModel> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all users (optional, for testing purposes)
    @GetMapping
    public ResponseEntity<List<UserServiceModel>> getAllUsers() {
        List<UserServiceModel> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    // Delete a user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
