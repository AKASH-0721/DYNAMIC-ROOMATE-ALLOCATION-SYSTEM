package org.example.dsa_proj.Controller;

import org.example.dsa_proj.Models.*;
import org.example.dsa_proj.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/hostel")
public class HostelController {

    // 🏠 Root redirect to rooms
    @GetMapping("/")
    public String home() {
        return "redirect:/hostel/rooms";
    }

    @Autowired
    private StudentService studentService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private waitlistservice waitlistService;

    // 🏠 Homepage - show all rooms
    @GetMapping("/rooms")
    public String showRooms(Model model) {
        List<Room> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        return "rooms";
    }

    // 👨‍🎓 Show all students
    @GetMapping("/students")
    public String showStudents(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "students";
    }

    // 🕒 Waitlist page
    @GetMapping("/waitlist")
    public String showWaitlist(Model model) {
        List<WaitList> waitlist = waitlistService.getAll();
        model.addAttribute("waitlist", waitlist);
        return "waitlist";
    }

    // 🧩 Room Allocation UI handler
    @PostMapping("/allocate")
    public String allocateRoom(@RequestParam Long studentId, @RequestParam String roomType) {
        studentService.allocateRoom(studentId, roomType);
        return "redirect:/hostel/students";
    }
}
