package example.com.controller;

import example.com.model.UserServiceModel;
import example.com.service.UserService;
import example.com.repository.UserServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/users")
public class UserServiceController {

    private final UserService userService;
    private final UserServiceRepository userServiceRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceController(UserService userService, UserServiceRepository userServiceRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userServiceRepository = userServiceRepository;
        this.passwordEncoder = passwordEncoder;
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
        // Basic password validation
        if (user.getPassword().length() < 6) {
            model.addAttribute("errorMessage", "Password must be at least 6 characters long.");
            return "register";
        }

        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Call the service to register the user
        String resultMessage = userService.registerUser(user);

        if (resultMessage.equals("Registration successful!")) {
            return "redirect:/api/users/login?success=true";
        } else {
            model.addAttribute("errorMessage", resultMessage);
            return "register";
        }
    }

    // Custom UserDetailsService logic inside the controller
    @GetMapping("/loadUserByUsername/{username}")
    @ResponseBody
    public UserDetails loadUserByUsername(@PathVariable String username) throws UsernameNotFoundException {
        UserServiceModel user = userServiceRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Convert the role to Spring Security format
        String role = "ROLE_" + user.getRole().toUpperCase();

        return User.withUsername(user.getUsername())
                .password(user.getPassword()) // Use the encoded password from DB
                .authorities(role)
                .build();
    }

    // Serve the home page after login
    @GetMapping("/home")
    public String home() {
        return "home"; // Points to src/main/resources/templates/home.html
    }

    // Get a user by email (API)
    @GetMapping("/email/{email}")
    @ResponseBody
    public Optional<UserServiceModel> getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    // Get all users (API for testing purposes)
    @GetMapping
    @ResponseBody
    public List<UserServiceModel> getAllUsers() {
        return userService.findAllUsers();
    }

    // Delete a user by ID (API)
    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
