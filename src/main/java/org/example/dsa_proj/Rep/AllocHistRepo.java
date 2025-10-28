package org.example.dsa_proj.Rep;

import org.example.dsa_proj.Models.allochist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllocHistRepo extends JpaRepository<allochist, Long> {

    List<allochist> findByStudent_StudentId(Long studentId);

    List<allochist> findByRoom_RoomId(Long roomId);
}

