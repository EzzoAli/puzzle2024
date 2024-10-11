package example.com.controller;

import example.com.model.UserServiceModel;
import example.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    // Serve the login page with error and success handling
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        @RequestParam(value = "success", required = false) String success,
                        Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password!");
        }
        if (logout != null) {
            model.addAttribute("successMessage", "You have been logged out successfully.");
        }
        if (success != null) {
            model.addAttribute("successMessage", "Registration successful! Please log in.");
        }
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
    public String registerUser(@ModelAttribute UserServiceModel user, Model model) {
        // Call the service to register the user
        String resultMessage = userService.registerUser(user);

        // If registration was successful, redirect to the login page with a success message
        if (resultMessage.equals("Registration successful!")) {
            return "redirect:/api/users/login?success=true"; // Add a success flag in the URL
        } else {
            // If there is an error, display the error message on the registration page
            model.addAttribute("errorMessage", resultMessage);
            return "register"; // Stay on the registration page if there's an error
        }
    }

    // Serve the home page after login
    @GetMapping("/home")
    public String home() {
        return "home"; // Points to src/main/resources/templates/home.html
    }

    // --- Existing API Endpoints for Users ---

    // Get a user by email (API)
    @GetMapping("/email/{email}")
    public Optional<UserServiceModel> getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    // Get all users (API for testing purposes)
    @GetMapping
    public List<UserServiceModel> getAllUsers() {
        return userService.findAllUsers();
    }

    // Delete a user by ID (API)
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
