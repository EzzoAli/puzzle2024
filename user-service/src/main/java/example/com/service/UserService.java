package example.com.service;

import example.com.model.UserServiceModel;
import example.com.repository.UserServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserServiceRepository userRepository;

    @Autowired
    public UserService(UserServiceRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Register a new user
    public UserServiceModel registerUser(UserServiceModel user) {
        // Add logic to hash the password (if needed) and save the user
        return userRepository.save(user);
    }

    // Find user by email
    public Optional<UserServiceModel> findByEmail(String email) {
        return userRepository.findByEmail(email);
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
