package example.com.service;

import example.com.model.RoomDifficulty;
import example.com.model.RoomServiceModel;
import example.com.model.RoomStatus;
import example.com.model.UserServiceModel;
import example.com.repository.RoomServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomServiceRepository roomRepository;

    @Autowired
    public RoomService(RoomServiceRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // Method to create a new room
    public RoomServiceModel createRoom(RoomServiceModel room) {
        // Validate room capacity
        if (room.getMaxPlayers() <= 0 || room.getMaxPlayers() > 2) {
            throw new IllegalArgumentException("Maximum players must be between 1 and 2.");
        }
        return roomRepository.save(room);
    }

    // Method to update an existing room
    public RoomServiceModel updateRoom(Long roomId, RoomServiceModel updatedRoom) {
        Optional<RoomServiceModel> existingRoomOpt = roomRepository.findById(roomId);
        if (existingRoomOpt.isPresent()) {
            RoomServiceModel existingRoom = existingRoomOpt.get();
            // Update the fields of the existing room
            existingRoom.setName(updatedRoom.getName());
            existingRoom.setDifficultyLevel(updatedRoom.getDifficultyLevel());
            existingRoom.setStatus(updatedRoom.getStatus());
            return roomRepository.save(existingRoom);
        } else {
            throw new IllegalArgumentException("Room not found with ID: " + roomId);
        }
    }

    // Method to manage room status (open/close)
    public RoomServiceModel changeRoomStatus(Long roomId, RoomStatus newStatus) {
        Optional<RoomServiceModel> existingRoomOpt = roomRepository.findById(roomId);
        if (existingRoomOpt.isPresent()) {
            RoomServiceModel existingRoom = existingRoomOpt.get();
            existingRoom.setStatus(newStatus);
            return roomRepository.save(existingRoom);
        } else {
            throw new IllegalArgumentException("Room not found with ID: " + roomId);
        }
    }

    // Method to get all rooms
    public List<RoomServiceModel> getAllRooms() {
        return roomRepository.findAll();
    }

    // Method to get rooms by status
    public List<RoomServiceModel> getRoomsByStatus(RoomStatus status) {
        return roomRepository.findByStatus(status);
    }

    // Method to get rooms by difficulty level
    public List<RoomServiceModel> getRoomsByDifficulty(RoomDifficulty difficulty) {
        return roomRepository.findByDifficultyLevel(difficulty);
    }

    // Method for a user to join a room
    public RoomServiceModel joinRoom(Long roomId, UserServiceModel user) {
        Optional<RoomServiceModel> roomOpt = roomRepository.findById(roomId);
        if (roomOpt.isPresent()) {
            RoomServiceModel room = roomOpt.get();

            // Check if room is full
            if (room.getPlayers().size() >= room.getMaxPlayers()) {
                throw new IllegalArgumentException("Room is full.");
            }

            // Add user to the room
            room.getPlayers().add(user);
            return roomRepository.save(room); // Save changes
        } else {
            throw new IllegalArgumentException("Room not found with ID: " + roomId);
        }
    }

    public RoomServiceModel leaveRoom(Long roomId, UserServiceModel user) {
        Optional<RoomServiceModel> roomOpt = roomRepository.findById(roomId);
        if (roomOpt.isPresent()) {
            RoomServiceModel room = roomOpt.get();

            // Remove user from the room
            room.getPlayers().remove(user); // This assumes user equals() is properly overridden
            return roomRepository.save(room); // Save changes
        } else {
            throw new IllegalArgumentException("Room not found with ID: " + roomId);
        }
    }

    // Method to get a room by its ID
    public RoomServiceModel getRoomById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found with ID: " + roomId));
    }


}
