package example.com.controller;

import example.com.model.RoomServiceModel;
import example.com.model.RoomStatus;
import example.com.model.UserServiceModel;
import example.com.service.RoomService;
import example.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomServiceController {

    private final RoomService roomService;
    private final UserService userService;

    @Autowired
    public RoomServiceController(RoomService roomService, UserService userService) {
        this.roomService = roomService;
        this.userService = userService;
    }

    // Show the room creation form
    @GetMapping("/create")
    public String showCreateRoomForm(Model model) {
        model.addAttribute("room", new RoomServiceModel());
        return "create-room"; // Returns the create-room.html template
    }

    // Handle the form submission for creating a new room
    @PostMapping("/create")
    public String createRoom(@ModelAttribute RoomServiceModel room) {
        roomService.createRoom(room);
        return "redirect:/rooms"; // Redirect to list rooms after creation
    }

    // Display a list of rooms
    @GetMapping
    public String listRooms(Model model) {
        List<RoomServiceModel> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        return "list-rooms"; // Returns the list-rooms.html template
    }

    // Display room details
    @GetMapping("/{roomId}")
    public String roomDetails(@PathVariable Long roomId, Model model) {
        RoomServiceModel room = roomService.getRoomById(roomId);
        model.addAttribute("room", room);
        return "room-details"; // Returns the room-details.html template
    }

    // Join a room
    @PostMapping("/join")
    public String joinRoom(@RequestParam Long roomId, Principal principal) {
        UserServiceModel user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found: " + principal.getName())); // Fetch user by username
        roomService.joinRoom(roomId, user);
        return "redirect:/rooms/" + roomId; // Redirect back to room details after joining
    }

    // Leave a room
    @PostMapping("/leave")
    public String leaveRoom(@RequestParam Long roomId, Principal principal) {
        UserServiceModel user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found: " + principal.getName())); // Fetch user by username
        roomService.leaveRoom(roomId, user);
        return "redirect:/rooms"; // Redirect to list rooms after leaving
    }

    // **NEW**: Update an existing room
    @PostMapping("/update")
    public String updateRoom(@RequestParam Long roomId, @ModelAttribute RoomServiceModel updatedRoom) {
        roomService.updateRoom(roomId, updatedRoom);
        return "redirect:/rooms/" + roomId; // Redirect to room details after updating
    }

    // **NEW**: Change the status of a room (open/close)
    @PostMapping("/change-status")
    public String changeRoomStatus(@RequestParam Long roomId, @RequestParam RoomStatus status) {
        roomService.changeRoomStatus(roomId, status);
        return "redirect:/rooms/" + roomId; // Redirect to room details after changing status
    }

    // **NEW**: Get rooms by status
    @GetMapping("/status/{status}")
    public String getRoomsByStatus(@PathVariable RoomStatus status, Model model) {
        List<RoomServiceModel> rooms = roomService.getRoomsByStatus(status);
        model.addAttribute("rooms", rooms);
        return "list-rooms"; // Returns the list-rooms.html template with filtered rooms
    }
}
