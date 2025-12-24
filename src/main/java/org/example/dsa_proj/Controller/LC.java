package org.example.dsa_proj.Controller;

import org.example.dsa_proj.Models.Room;
import org.example.dsa_proj.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LC {

    @Autowired
    private RoomService roomService;

    @GetMapping("/rooms")
    public String showRooms(Model model) {
        List<Room> rooms = roomService.getAllRooms();

        System.out.println("DEBUG: Rooms fetched = " + rooms.size());
        for (Room r : rooms) {
            System.out.println("Room: " + r.getRoomNumber() + " - " + r.getStatus());
        }

        model.addAttribute("rooms", rooms);
        return "rooms";
    }

}
