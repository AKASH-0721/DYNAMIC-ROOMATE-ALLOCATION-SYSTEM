package org.example.dsa_proj.Rep;


import org.example.dsa_proj.Models.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrefRepo extends JpaRepository<Preference, Long> {

    List<Preference> findByStudent_StudentId(Long studentId);
}

