package org.example.dsa_proj.Models;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * Student Entity
 * 
 * Represents a student in the hostel management system.
 * Contains personal information, preferences, and room allocation status.
 * 
 * @author DSA Project Team
 * @version 1.0
 * @since 2024
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students", indexes = {
    @Index(name = "idx_student_status", columnList = "status"),
    @Index(name = "idx_student_gender", columnList = "gender"),
    @Index(name = "idx_student_branch", columnList = "branch")
})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @NotBlank(message = "Student name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
    @Column(name = "gender", nullable = false, length = 10)
    private String gender;

    @NotBlank(message = "Branch is required")
    @Size(max = 50, message = "Branch name cannot exceed 50 characters")
    @Column(name = "branch", nullable = false, length = 50)
    private String branch;

    @Min(value = 1, message = "Year must be at least 1")
    @Max(value = 4, message = "Year cannot exceed 4")
    @Column(name = "`year`", nullable = false)
    private int year;

    @Size(max = 50, message = "Preference type cannot exceed 50 characters")
    @Column(name = "preference_type", length = 50)
    private String preferenceType;        // quiet, gaming, studious, social, etc.

    @Size(max = 20, message = "Room type preference cannot exceed 20 characters")
    @Column(name = "room_type_preference", length = 20)
    private String roomTypePreference;    // single, double, triple, quad

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "^(Allocated|Waiting|Left|Processing Allocation)$", 
             message = "Status must be Allocated, Waiting, Left, or Processing Allocation")
    @Column(name = "status", nullable = false, length = 25)
    private String status = "Waiting";    // Default status for new students

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", foreignKey = @ForeignKey(name = "FK_student_room"))
    private Room allocatedRoom;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Preference> preferences = new ArrayList<>();

    /**
     * Check if student is currently allocated to a room
     * @return true if student has an allocated room, false otherwise
     */
    public boolean isAllocated() {
        return allocatedRoom != null && "Allocated".equals(status);
    }

    /**
     * Check if student is waiting for room allocation
     * @return true if student is in waiting status, false otherwise
     */
    public boolean isWaiting() {
        return "Waiting".equals(status) || "Processing Allocation".equals(status);
    }

    /**
     * Get student's full display information
     * @return formatted string with student details
     */
    public String getDisplayInfo() {
        return String.format("%s (%s, %s Year %d)", name, branch, gender, year);
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", branch='" + branch + '\'' +
                ", year=" + year +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }
}
