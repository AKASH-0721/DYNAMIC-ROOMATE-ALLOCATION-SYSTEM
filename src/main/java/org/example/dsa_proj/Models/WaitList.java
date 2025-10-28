package org.example.dsa_proj.Models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.service.annotation.GetExchange;

import java.time.LocalDate;

@Entity
@Getter
@Setter

@Table(name = "waitlist")
public class WaitList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long waitId;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private String preferredRoomType;
    private LocalDate waitingSince;
    private double priorityScore;

}

