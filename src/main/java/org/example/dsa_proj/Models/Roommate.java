package org.example.dsa_proj.Models;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

/**
 * Roommate Entity
 * 
 * Represents the relationship between students and their rooms.
 * Tracks when students join and leave rooms, supporting room sharing functionality.
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
@Table(name = "roommates", indexes = {
    @Index(name = "idx_roommate_room", columnList = "room_id"),
    @Index(name = "idx_roommate_student", columnList = "student_id"),
    @Index(name = "idx_roommate_active", columnList = "left_date"),
    @Index(name = "idx_roommate_joined", columnList = "joined_date")
}, uniqueConstraints = {
    @UniqueConstraint(name = "UK_active_roommate", columnNames = {"student_id", "room_id"})
})
public class Roommate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roommate_id")
    private Long roommateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false, 
                foreignKey = @ForeignKey(name = "FK_roommate_room"))
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false, 
                foreignKey = @ForeignKey(name = "FK_roommate_student"))
    private Student student;

    @NotNull(message = "Joined date is required")
    @Column(name = "joined_date", nullable = false)
    private LocalDate joinedDate;

    @Column(name = "left_date")
    private LocalDate leftDate;

    @Size(max = 200, message = "Notes cannot exceed 200 characters")
    @Column(name = "notes", length = 200)
    private String notes;

    @Column(name = "compatibility_score")
    private Integer compatibilityScore; // Score with other roommates

    /**
     * Check if the roommate assignment is currently active
     * @return true if student is still in the room
     */
    public boolean isActive() {
        return leftDate == null;
    }

    /**
     * Get duration of stay in the room
     * @return number of days in the room (or until now if still active)
     */
    public long getStayDurationDays() {
        LocalDate endDate = leftDate != null ? leftDate : LocalDate.now();
        return java.time.temporal.ChronoUnit.DAYS.between(joinedDate, endDate);
    }

    /**
     * Mark the roommate as having left
     * @param reason Reason for leaving
     */
    public void markAsLeft(String reason) {
        this.leftDate = LocalDate.now();
        this.notes = reason;
    }

    /**
     * Get display information about the roommate relationship
     * @return formatted string with roommate details
     */
    public String getDisplayInfo() {
        return String.format("%s in Room %s (from %s%s)", 
                           student != null ? student.getName() : "Unknown Student",
                           room != null ? room.getRoomNumber() : "Unknown Room",
                           joinedDate,
                           leftDate != null ? " to " + leftDate : " - ongoing");
    }

    @Override
    public String toString() {
        return "Roommate{" +
                "roommateId=" + roommateId +
                ", room=" + (room != null ? room.getRoomNumber() : "null") +
                ", student=" + (student != null ? student.getName() : "null") +
                ", joinedDate=" + joinedDate +
                ", leftDate=" + leftDate +
                ", active=" + isActive() +
                '}';
    }
}
