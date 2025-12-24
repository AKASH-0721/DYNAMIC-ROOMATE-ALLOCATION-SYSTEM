package org.example.dsa_proj.Service;

import org.example.dsa_proj.Models.Room;
import org.example.dsa_proj.Models.Roommate;
import org.example.dsa_proj.Models.Student;
import org.example.dsa_proj.Rep.RoomateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Roommate Service
 * Handles business logic related to roommate assignments and management
 * 
 * @author DSA Project Team
 * @version 1.0
 */
@Service
public class RoomateService {
    
    @Autowired
    private RoomateRepo roomateRepo;
    
    /**
     * Assign a student to a room
     * @param student The student to assign
     * @param room The room to assign to
     */
    public void assignStudentToRoom(Student student, Room room) {
        Roommate roommate = new Roommate();
        roommate.setStudent(student);
        roommate.setRoom(room);
        roommate.setJoinedDate(LocalDate.now());
        roomateRepo.save(roommate);
        
        // Update student's allocated room
        student.setAllocatedRoom(room);
        student.setStatus("Allocated");
    }
    
    /**
     * Remove a student from their current room
     * @param student The student to remove
     */
    public void removeStudentFromRoom(Student student) {
        List<Roommate> activeAssignments = roomateRepo.findByStudentAndLeftDateIsNull(student);
        for (Roommate roommate : activeAssignments) {
            roommate.setLeftDate(LocalDate.now());
            roomateRepo.save(roommate);
        }
        
        student.setAllocatedRoom(null);
        student.setStatus("Waiting");
    }
    
    /**
     * Get all roommates for a specific room
     * @param room The room
     * @return List of current roommates
     */
    public List<Roommate> getCurrentRoommates(Room room) {
        return roomateRepo.findByRoomAndLeftDateIsNull(room);
    }
}

