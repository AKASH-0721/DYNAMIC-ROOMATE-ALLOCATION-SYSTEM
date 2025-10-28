package org.example.dsa_proj.Rep;



import org.example.dsa_proj.Models.Roomate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomateRepo extends JpaRepository<Roomate, Long> {

    List<Roomate> findByRoom_RoomId(Long roomId);

    List<Roomate> findByStudent_StudentId(Long studentId);
}

