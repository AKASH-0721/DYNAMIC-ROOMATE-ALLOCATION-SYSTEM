package org.example.dsa_proj.Models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "room_swap_requests")
public class RSR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long swapId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private Long currentRoomId;
    private String requestedRoomType;
    private String status;
    private LocalDate requestDate;


}

