package example.com.repository;

import example.com.model.UserServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserServiceRepository extends JpaRepository<UserServiceModel, Long> {
    Optional<UserServiceModel> findByEmail(String email);
    Optional<UserServiceModel> findByUsername(String username);  // New method for finding by username
}