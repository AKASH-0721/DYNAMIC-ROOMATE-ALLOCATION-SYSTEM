package org.example.dsa_proj.Service;

import org.example.dsa_proj.Models.Student;
import org.example.dsa_proj.Models.WaitList;
import org.example.dsa_proj.Rep.waitlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class waitlistservice {

    @Autowired
    private waitlist waitRepo;

    // ✅ Get all waitlist entries
    public List<WaitList> getAll() {
        return waitRepo.findAll();
    }

    // ✅ Get all students waiting for a specific room type, ordered by priority
    public List<Student> getWaitingStudentsByRoomType(String roomType) {
        return waitRepo.findByPreferredRoomTypeOrderByPriorityScoreDesc(roomType)
                .stream()
                .map(WaitList::getStudent)
                .toList();
    }

    // ✅ Add student to waitlist
    public void addToWaitlist(Student s, String roomType, double score) {
        WaitList w = new WaitList();
        w.setStudent(s);
        w.setPreferredRoomType(roomType);
        w.setPriorityScore(score);
        w.setWaitingSince(LocalDate.now());
        waitRepo.save(w);
    }

    // ✅ Remove student from waitlist
    public void removeFromWaitlist(Student s) {
        waitRepo.deleteByStudent(s);
    }
}
