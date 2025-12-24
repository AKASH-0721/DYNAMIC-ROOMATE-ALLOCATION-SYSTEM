package org.example.dsa_proj.Controller;

import org.example.dsa_proj.Models.*;
import org.example.dsa_proj.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Home Controller
 * 
 * Handles the root URL and main dashboard functionality.
 * Provides entry point to the hostel management system.
 * 
 * @author DSA Project Team
 * @version 1.0
 * @since 2024
 */
@Controller
public class HomeController {

    @Autowired
    private StudentService studentService;
    
    @Autowired
    private RoomService roomService;
    
    @Autowired
    private waitlistservice waitlistService;

    /**
     * Root URL handler - redirects to dashboard
     * @return redirect to main dashboard
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    /**
     * Main dashboard with system overview
     * @param model Spring model for view data
     * @return dashboard view
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        try {
            // Get basic statistics
            List<Student> students = studentService.getAllStudents();
            List<Room> rooms = roomService.getAllRooms();
            List<WaitList> waitlist = waitlistService.getAll();
            
            // Calculate statistics
            long totalStudents = students.size();
            long allocatedStudents = students.stream().filter(Student::isAllocated).count();
            long waitingStudents = students.stream().filter(Student::isWaiting).count();
            
            long totalRooms = rooms.size();
            long availableRooms = rooms.stream().filter(Room::hasAvailableSpace).count();
            long fullRooms = rooms.stream().filter(Room::isFull).count();
            
            // Add statistics to model
            model.addAttribute("totalStudents", totalStudents);
            model.addAttribute("allocatedStudents", allocatedStudents);
            model.addAttribute("waitingStudents", waitingStudents);
            model.addAttribute("totalRooms", totalRooms);
            model.addAttribute("availableRooms", availableRooms);
            model.addAttribute("fullRooms", fullRooms);
            model.addAttribute("waitlistSize", waitlist.size());
            
            // Recent students and rooms for display
            model.addAttribute("recentStudents", students.stream().limit(5).toArray());
            model.addAttribute("recentRooms", rooms.stream().limit(5).toArray());
            
            return "dashboard";
            
        } catch (Exception e) {
            // Fallback if there are database issues
            model.addAttribute("error", "Unable to load dashboard data: " + e.getMessage());
            return "dashboard";
        }
    }

    /**
     * Alternative entry points for different sections
     */
    @GetMapping("/students")
    public String redirectToStudents() {
        return "redirect:/hostel/students";
    }

    @GetMapping("/waitlist")
    public String redirectToWaitlist() {
        return "redirect:/hostel/waitlist";
    }
}