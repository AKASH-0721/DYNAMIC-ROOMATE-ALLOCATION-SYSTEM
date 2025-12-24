package org.example.dsa_proj.Rep;



import org.example.dsa_proj.Models.Roommate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomateRepo extends JpaRepository<Roommate, Long> {

    List<Roommate> findByRoom_RoomIdOrderByJoinedDateDesc(Long roomId);

    List<Roommate> findByStudent_StudentIdOrderByJoinedDateDesc(Long studentId);
    
    /**
     * Find active roommate assignments for a student (no left date)
     */
    List<Roommate> findByStudentAndLeftDateIsNull(org.example.dsa_proj.Models.Student student);
    
    /**
     * Find current roommates in a room (no left date)
     */
    List<Roommate> findByRoomAndLeftDateIsNull(org.example.dsa_proj.Models.Room room);
    
    /**
     * Find all assignments for a student
     */
    List<Roommate> findByStudentOrderByJoinedDateDesc(org.example.dsa_proj.Models.Student student);
    
    /**
     * Find all assignments for a room
     */
    List<Roommate> findByRoomOrderByJoinedDateDesc(org.example.dsa_proj.Models.Room room);
}

