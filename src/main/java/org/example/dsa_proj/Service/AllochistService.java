package org.example.dsa_proj.Service;


import org.example.dsa_proj.Models.Room;
import org.example.dsa_proj.Models.Student;
import org.example.dsa_proj.Models.AllocationHistory;
import org.example.dsa_proj.Rep.AllocHistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class AllochistService {

    @Autowired
    private AllocHistRepo historyRepo;

    public void logAllocation(Student s, Room r, String reason) {
        AllocationHistory h = new AllocationHistory();
        h.setStudent(s);
        h.setRoom(r);
        h.setAllocationDate(LocalDate.now());
        h.setReason(reason);
        historyRepo.save(h);
    }

    public void logDeallocation(Student s, Room r, String reason) {
        AllocationHistory h = new AllocationHistory();
        h.setStudent(s);
        h.setRoom(r);
        h.setDeallocationDate(LocalDate.now());
        h.setReason(reason);
        historyRepo.save(h);
    }
    
    /**
     * Get all allocation history
     * @return List of all allocation history records
     */
    public List<AllocationHistory> getAllocHistory() {
        return historyRepo.findAll();
    }

    /**
     * Save allocation history record
     * @param ah Allocation history record
     */
    public void saveAllocHistory(AllocationHistory ah) {
        historyRepo.save(ah);
    }
}

