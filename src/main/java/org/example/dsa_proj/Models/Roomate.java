package org.example.dsa_proj.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "roommates")
public class Roomate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roommateId;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private LocalDate joinedDate;
    private LocalDate leftDate;

}
