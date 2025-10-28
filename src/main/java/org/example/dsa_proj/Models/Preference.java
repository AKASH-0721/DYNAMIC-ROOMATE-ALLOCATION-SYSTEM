package org.example.dsa_proj.Models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "preferences")
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long preferenceId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private String studyTime;          // Morning / Night
    private String sleepTime;          // Early / Late
    private String interests;          // Reading, Music, etc.
    private String roommateGenderPreference; // Same / Any

    // Constructors, Getters, Setters
}
