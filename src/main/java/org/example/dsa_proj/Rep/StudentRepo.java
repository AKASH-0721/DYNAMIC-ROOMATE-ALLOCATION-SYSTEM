package org.example.dsa_proj.Rep;


import org.example.dsa_proj.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
    List<Student> findByStatus(String status);
    List<Student> findByGender(String gender);
    List<Student> findByRoomTypePreference(String roomTypePreference);
    // To find students allocated in a particular room
    List<Student> findByAllocatedRoom_RoomId(Long roomId);
}

