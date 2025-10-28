package org.example.dsa_proj.Rep;


import org.example.dsa_proj.Models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long> {

    Optional<Admin> findByUsername(String username);

    Optional<Admin> findByUsernameAndPassword(String username, String password);
}

