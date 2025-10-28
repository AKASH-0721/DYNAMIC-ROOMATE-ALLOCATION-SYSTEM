package org.example.dsa_proj.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    private String name;
    private String gender;
    private String branch;
    private int year;
    private String preferenceType;        // quiet, gaming, etc.
    private String roomTypePreference;    // single, double, etc.
    private String status;                // Allocated, Waiting, Left

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room allocatedRoom;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Preference> preferences = new ArrayList<>();

    // Constructors, Getters, Setters
}
