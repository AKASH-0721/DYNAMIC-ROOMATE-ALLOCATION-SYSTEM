package org.example.dsa_proj.Rep;

import org.example.dsa_proj.Models.AllocationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllocHistRepo extends JpaRepository<AllocationHistory, Long> {

    List<AllocationHistory> findByStudent_StudentId(Long studentId);

    List<AllocationHistory> findByRoom_RoomId(Long roomId);
    
    /**
     * Find allocation history for a specific student
     * @param student The student entity
     * @return List of allocation history entries
     */
    List<AllocationHistory> findByStudentOrderByAllocationDateDesc(org.example.dsa_proj.Models.Student student);
    
    /**
     * Find allocation history for a specific room
     * @param room The room entity
     * @return List of allocation history entries
     */
    List<AllocationHistory> findByRoomOrderByAllocationDateDesc(org.example.dsa_proj.Models.Room room);
    
    /**
     * Find active allocations (no deallocation date)
     * @return List of active allocations
     */
    List<AllocationHistory> findByDeallocationDateIsNull();
    
    /**
     * Find current allocation for a student
     * @param student The student
     * @return Current active allocation if any
     */
    AllocationHistory findByStudentAndDeallocationDateIsNull(org.example.dsa_proj.Models.Student student);
}

