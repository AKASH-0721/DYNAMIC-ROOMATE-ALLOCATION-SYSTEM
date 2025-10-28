package org.example.dsa_proj.Service;


import org.example.dsa_proj.Models.Room;
import org.example.dsa_proj.Models.Roomate;
import org.example.dsa_proj.Models.Student;
import org.example.dsa_proj.Rep.RoomateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class RoomateService {

    @Autowired
    private RoomateRepo roommateRepo;

    public void assignStudentToRoom(Student student, Room room) {
        Roomate rm = new Roomate();
        rm.setRoom(room);
        rm.setStudent(student);
        rm.setJoinedDate(LocalDate.now());

        student.setAllocatedRoom(room);
        student.setStatus("Allocated");

        roommateRepo.save(rm);
    }
}
