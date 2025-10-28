package org.example.dsa_proj.Service;


import org.example.dsa_proj.Models.Admin;
import org.example.dsa_proj.Rep.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepo;

    public Optional<Admin> login(String username, String password) {
        return adminRepo.findByUsernameAndPassword(username.trim(), password.trim());
    }

    public Admin save(Admin admin) {
        return adminRepo.save(admin);
    }
}

