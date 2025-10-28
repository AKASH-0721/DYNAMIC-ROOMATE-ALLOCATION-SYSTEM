package org.example.dsa_proj.Service;


import org.example.dsa_proj.Models.Room;
import org.example.dsa_proj.Models.Student;
import org.example.dsa_proj.Models.allochist;
import org.example.dsa_proj.Rep.AllocHistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class AllochistService {

    @Autowired
    private AllocHistRepo historyRepo;

    public void logAllocation(Student s, Room r, String reason) {
        allochist h = new allochist();
        h.setStudent(s);
        h.setRoom(r);
        h.setAllocationDate(LocalDate.now());
        h.setReason(reason);
        historyRepo.save(h);
    }

    public void logDeallocation(Student s, Room r, String reason) {
        allochist h = new allochist();
        h.setStudent(s);
        h.setRoom(r);
        h.setDeallocationDate(LocalDate.now());
        h.setReason(reason);
        historyRepo.save(h);
    }
}

