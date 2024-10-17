package example.com.controller;

import example.com.model.RoomServiceModel;
import example.com.model.UserServiceModel;
import example.com.service.RoomService;
import example.com.service.UserService; // Import UserService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal; // Import Principal
import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomServiceController {

    private final RoomService roomService;
    private final UserService userService; // Declare UserService

    @Autowired
    public RoomServiceController(RoomService roomService, UserService userService) {
        this.roomService = roomService;
        this.userService = userService;  // Inject userService
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
}
