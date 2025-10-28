package org.example.dsa_proj.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "allocation_history")
public class allochist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long allocationId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    private LocalDate allocationDate;
    private LocalDate deallocationDate;
    private String reason;   // e.g., "Reallocation", "Graduated"

    // Constructors, Getters, Setters
}
