package org.example.dsa_proj.Rep;


import org.example.dsa_proj.Models.Student;
import org.example.dsa_proj.Models.WaitList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface waitlist extends JpaRepository<WaitList, Long> {

    // Find waitlisted students based on room type
    List<WaitList> findByPreferredRoomType(String roomType);

    // Find by priority (useful for Priority Queue-based allocation)
    List<WaitList> findAllByOrderByPriorityScoreDesc();

    List<WaitList> findByPreferredRoomTypeOrderByPriorityScoreDesc(String roomType);

    void deleteByStudent(Student s);
}

