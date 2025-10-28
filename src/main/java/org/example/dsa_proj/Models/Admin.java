package org.example.dsa_proj.Models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "admins", schema = "public")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    private String username;
    private String password;
    private String role; // e.g., "SUPER_ADMIN", "HOSTEL_MANAGER"


}


