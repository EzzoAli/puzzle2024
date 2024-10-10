package example.com.controller;

import example.com.model.UserServiceModel;
import example.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/users")
public class UserServiceController {

    private final UserService userService;

    @Autowired
    public UserServiceController(UserService userService) {
        this.userService = userService;
    }

    // Serve the login page
    @GetMapping("/login")
    public String login() {
        return "login"; // Points to src/main/resources/templates/login.html
    }

    // Serve the registration page
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserServiceModel()); // Empty user object for form binding
        return "register"; // Points to src/main/resources/templates/register.html
    }

    // Handle registration form submission
    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserServiceModel user) {
        userService.registerUser(user); // Save the new user
        return "redirect:/api/users/login"; // Redirect to login after successful registration
    }

    // Serve the home page after login
    @GetMapping("/home")
    public String home() {
        return "home"; // Points to src/main/resources/templates/home.html
    }

    // --- Existing API Endpoints for Users ---

    // Get a user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserServiceModel> getUserByEmail(@PathVariable String email) {
        Optional<UserServiceModel> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all users (for testing purposes)
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
