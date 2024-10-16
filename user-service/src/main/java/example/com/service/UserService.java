package example.com.service;

import example.com.model.UserServiceModel;
import example.com.repository.UserServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserServiceRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserServiceRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register a new user with validation
    public String registerUser(UserServiceModel user) {
        // Check if the username or email already exists
        Optional<UserServiceModel> existingUser = userRepository.findByUsername(user.getUsername());
        Optional<UserServiceModel> existingEmail = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            return "Username already exists!";
        }
        if (existingEmail.isPresent()) {
            return "An account with this email already exists!";
        }

        // Set default role if not provided
        assignDefaultRoleIfNotSet(user);

        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the new user
        userRepository.save(user);
        return "Registration successful!";
    }

    // Method to assign a default role if not provided
    private void assignDefaultRoleIfNotSet(UserServiceModel user) {
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");  // Default role is USER
        }
    }

    // Implement the UserDetailsService interface method for Spring Security authentication
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserServiceModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        String role = "ROLE_" + user.getRole().toUpperCase();

        return User.withUsername(user.getUsername())
                .password(user.getPassword()) // Use the encoded password from DB
                .authorities(role)
                .build();
    }

    // Find user by email
    public Optional<UserServiceModel> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Find user by username
    public Optional<UserServiceModel> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Find all users
    public List<UserServiceModel> findAllUsers() {
        return userRepository.findAll();
    }

    // Delete user by ID
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
