package org.example.dsa_proj.Rep;



import org.example.dsa_proj.Models.RoomSwapRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomSwapRepo extends JpaRepository<RoomSwapRequest, Long> {

    List<RoomSwapRequest> findByStudent_StudentId(Long studentId);

    List<RoomSwapRequest> findByStatus(String status);
    
    List<RoomSwapRequest> findByStatusOrderByRequestDateAsc(String status);
    
    List<RoomSwapRequest> findByRequestedRoomType(String requestedRoomType);
    
    List<RoomSwapRequest> findByStudent(org.example.dsa_proj.Models.Student student);
}

