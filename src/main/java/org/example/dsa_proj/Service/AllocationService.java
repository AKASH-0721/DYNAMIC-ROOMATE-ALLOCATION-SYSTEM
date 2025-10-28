package org.example.dsa_proj.Service;



import org.example.dsa_proj.Models.Room;
import org.example.dsa_proj.Models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
public class AllocationService {

    @Autowired
    private StudentService studentService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomateService roommateService;
    @Autowired
    private AllochistService historyService;
    @Autowired
    private waitlistservice waitlistService;

    public String allocateRooms(String roomType) {
        List<Student> waitingStudents = waitlistService.getWaitingStudentsByRoomType(roomType);
        List<Room> availableRooms = roomService.getAvailableRoomsByType(roomType);

        if (availableRooms.isEmpty()) return "❌ No available rooms";

        int roomIndex = 0;
        for (Student s : waitingStudents) {
            if (roomIndex >= availableRooms.size()) break;

            Room r = availableRooms.get(roomIndex);
            roommateService.assignStudentToRoom(s, r);

            roomService.updateOccupancy(r, 1);
            waitlistService.removeFromWaitlist(s);

            historyService.logAllocation(s, r, "New Allocation");

            if (r.getOccupancy() >= r.getCapacity()) roomIndex++;
        }

        return "✅ Allocation completed successfully!";
    }

    public void deallocateStudent(Student s, String reason) {
        Room room = s.getAllocatedRoom();
        if (room != null) {
            roomService.updateOccupancy(room, -1);
            s.setAllocatedRoom(null);
            s.setStatus("Left");
            studentService.saveStudent(s);

            historyService.logDeallocation(s, room, reason);
        }
    }
}
