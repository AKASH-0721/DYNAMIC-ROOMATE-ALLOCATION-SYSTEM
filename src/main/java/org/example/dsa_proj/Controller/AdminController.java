package org.example.dsa_proj.Controller;

import org.example.dsa_proj.Models.*;
import org.example.dsa_proj.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Admin Controller
 * 
 * Handles admin authentication, dashboard, and student management operations.
 * Provides comprehensive admin portal functionality for hostel management.
 * 
 * @author DSA Project Team
 * @version 1.0
 * @since 2024
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private RoomService roomService;
    
    @Autowired
    private waitlistservice waitlistService;

    /**
     * Admin login page
     */
    @GetMapping("/login")
    public String loginPage() {
        return "admin/login";
    }

    /**
     * Process admin login
     */
    @PostMapping("/login")
    public String processLogin(@RequestParam String username, 
                             @RequestParam String password, 
                             RedirectAttributes redirectAttributes) {
        Optional<Admin> adminOpt = adminService.login(username, password);
        
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            if (admin.isAccountValid()) {
                admin.recordSuccessfulLogin();
                adminService.save(admin);
                return "redirect:/admin/dashboard";
            } else {
                redirectAttributes.addFlashAttribute("error", "Account is locked or inactive");
                return "redirect:/admin/login";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/admin/login";
        }
    }

    /**
     * Admin dashboard
     */
    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        try {
            // Get comprehensive statistics
            List<Student> students = studentService.getAllStudents();
            List<Room> rooms = roomService.getAllRooms();
            List<WaitList> waitlist = waitlistService.getAll();
            
            // Student statistics
            long totalStudents = students.size();
            long allocatedStudents = students.stream().filter(Student::isAllocated).count();
            long waitingStudents = students.stream().filter(Student::isWaiting).count();
            
            // Room statistics
            long totalRooms = rooms.size();
            long availableRooms = rooms.stream().filter(Room::hasAvailableSpace).count();
            long fullRooms = rooms.stream().filter(Room::isFull).count();
            
            // Add all data to model
            model.addAttribute("totalStudents", totalStudents);
            model.addAttribute("allocatedStudents", allocatedStudents);
            model.addAttribute("waitingStudents", waitingStudents);
            model.addAttribute("totalRooms", totalRooms);
            model.addAttribute("availableRooms", availableRooms);
            model.addAttribute("fullRooms", fullRooms);
            model.addAttribute("waitlistSize", waitlist.size());
            
            // Recent data for quick access
            model.addAttribute("recentStudents", students.stream().limit(10).toArray());
            model.addAttribute("recentRooms", rooms.stream().limit(10).toArray());
            
            return "admin/dashboard";
            
        } catch (Exception e) {
            model.addAttribute("error", "Unable to load dashboard data: " + e.getMessage());
            return "admin/dashboard";
        }
    }

    /**
     * Student management page
     */
    @GetMapping("/students")
    public String manageStudents(Model model) {
        List<Student> students = studentService.getAllStudents();
        List<Room> availableRooms = roomService.getAllRooms().stream()
            .filter(Room::hasAvailableSpace)
            .collect(Collectors.toList());
        
        model.addAttribute("students", students);
        model.addAttribute("availableRooms", availableRooms);
        model.addAttribute("newStudent", new Student());
        
        return "admin/students";
    }

    /**
     * Add new student
     */
    @PostMapping("/students/add")
    public String addStudent(@Valid @ModelAttribute("newStudent") Student student, 
                           BindingResult result, 
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Please correct the form errors");
            return "redirect:/admin/students";
        }
        
        try {
            studentService.save(student);
            redirectAttributes.addFlashAttribute("success", 
                "Student " + student.getName() + " added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error adding student: " + e.getMessage());
        }
        
        return "redirect:/admin/students";
    }

    /**
     * Edit student form
     */
    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        Optional<Student> studentOpt = studentService.findById(id);
        if (studentOpt.isEmpty()) {
            return "redirect:/admin/students";
        }
        
        List<Room> availableRooms = roomService.getAllRooms().stream()
            .filter(Room::hasAvailableSpace)
            .collect(Collectors.toList());
        
        model.addAttribute("student", studentOpt.get());
        model.addAttribute("availableRooms", availableRooms);
        
        return "admin/edit-student";
    }

    /**
     * Update student
     */
    @PostMapping("/students/update/{id}")
    public String updateStudent(@PathVariable Long id, 
                              @Valid @ModelAttribute Student student, 
                              BindingResult result, 
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Please correct the form errors");
            return "redirect:/admin/students/edit/" + id;
        }
        
        try {
            student.setStudentId(id);
            studentService.save(student);
            redirectAttributes.addFlashAttribute("success", 
                "Student " + student.getName() + " updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error updating student: " + e.getMessage());
        }
        
        return "redirect:/admin/students";
    }

    /**
     * Delete student
     */
    @PostMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id, 
                              RedirectAttributes redirectAttributes) {
        try {
            Optional<Student> studentOpt = studentService.findById(id);
            if (studentOpt.isPresent()) {
                studentService.delete(id);
                redirectAttributes.addFlashAttribute("success", 
                    "Student " + studentOpt.get().getName() + " deleted successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Student not found");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error deleting student: " + e.getMessage());
        }
        
        return "redirect:/admin/students";
    }

    /**
     * Allocate room to student
     */
    @PostMapping("/students/allocate")
    public String allocateRoom(@RequestParam Long studentId, 
                             @RequestParam Long roomId, 
                             RedirectAttributes redirectAttributes) {
        try {
            // This would call a more comprehensive allocation service
            studentService.allocateStudentToRoom(studentId, roomId);
            redirectAttributes.addFlashAttribute("success", "Room allocated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error allocating room: " + e.getMessage());
        }
        
        return "redirect:/admin/students";
    }

    /**
     * Room management page
     */
    @GetMapping("/rooms")
    public String manageRooms(Model model) {
        List<Room> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        model.addAttribute("newRoom", new Room());
        
        return "admin/rooms";
    }

    /**
     * Add new room
     */
    @PostMapping("/rooms/add")
    public String addRoom(@Valid @ModelAttribute("newRoom") Room room, 
                        BindingResult result, 
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Please correct the form errors");
            return "redirect:/admin/rooms";
        }
        
        try {
            // Note: Implement room saving in RoomService
            redirectAttributes.addFlashAttribute("success", 
                "Room functionality needs to be implemented in RoomService");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error adding room: " + e.getMessage());
        }
        
        return "redirect:/admin/rooms";
    }

    /**
     * Waitlist management
     */
    @GetMapping("/waitlist")
    public String manageWaitlist(Model model) {
        List<WaitList> waitlist = waitlistService.getAll();
        model.addAttribute("waitlist", waitlist);
        
        return "admin/waitlist";
    }

    /**
     * Logout
     */
    @GetMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "You have been logged out successfully");
        return "redirect:/admin/login";
    }
}