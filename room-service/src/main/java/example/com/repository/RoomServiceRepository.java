package example.com.repository;

import example.com.model.RoomServiceModel;
import example.com.model.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomServiceRepository extends JpaRepository<RoomServiceModel, Long> {

    // Custom query to find rooms by their status
    List<RoomServiceModel> findByStatus(RoomStatus status);

    // Custom query to find rooms by difficulty level
    List<RoomServiceModel> findByDifficultyLevel(int difficultyLevel);

    // You can add more custom query methods as needed
}
