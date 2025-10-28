package org.example.dsa_proj.Rep;



import org.example.dsa_proj.Models.RSR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomSwapRepo extends JpaRepository<RSR, Long> {

    List<RSR> findByStudent_StudentId(Long studentId);

    List<RSR> findByStatus(String status);
}

