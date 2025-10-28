package org.example.dsa_proj.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    private String roomNumber;
    private int capacity;
    private int occupancy;
    private String hostelBlock;
    private String roomType;
    private String status;

    @OneToMany(mappedBy = "allocatedRoom", cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<>();

}
