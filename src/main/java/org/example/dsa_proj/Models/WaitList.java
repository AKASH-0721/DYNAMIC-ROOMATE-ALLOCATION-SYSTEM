package org.example.dsa_proj.Models;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

/**
 * WaitList Entity
 * 
 * Represents students waiting for room allocation in priority order.
 * Uses priority scoring algorithm for fair allocation.
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
@Table(name = "wait_list", indexes = {
    @Index(name = "idx_waitlist_priority", columnList = "priority_score DESC"),
    @Index(name = "idx_waitlist_room_type", columnList = "preferred_room_type"),
    @Index(name = "idx_waitlist_waiting_since", columnList = "waiting_since")
})
public class WaitList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wait_id")
    private Long waitId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false, unique = true, 
                foreignKey = @ForeignKey(name = "FK_waitlist_student"))
    private Student student;

    @Size(max = 20, message = "Preferred room type cannot exceed 20 characters")
    @Column(name = "preferred_room_type", length = 20)
    private String preferredRoomType;

    @NotNull(message = "Waiting since date is required")
    @Column(name = "waiting_since", nullable = false)
    private LocalDate waitingSince;

    @DecimalMin(value = "0.0", message = "Priority score cannot be negative")
    @DecimalMax(value = "100.0", message = "Priority score cannot exceed 100")
    @Column(name = "priority_score", nullable = false, precision = 5, scale = 2)
    private double priorityScore;

    /**
     * Calculate priority score based on waiting time and student factors
     */
    public void calculatePriorityScore() {
        if (student != null && waitingSince != null) {
            double baseScore = 10.0; // Base priority
            
            // Add points for waiting duration (1 point per week)
            long daysSinceWaiting = java.time.temporal.ChronoUnit.DAYS.between(waitingSince, LocalDate.now());
            baseScore += (daysSinceWaiting / 7.0);
            
            // Add points for senior students
            if (student.getYear() >= 3) {
                baseScore += 5.0;
            }
            
            // Cap the maximum score
            this.priorityScore = Math.min(baseScore, 100.0);
        }
    }

    @Override
    public String toString() {
        return "WaitList{" +
                "waitId=" + waitId +
                ", student=" + (student != null ? student.getName() : "null") +
                ", preferredRoomType='" + preferredRoomType + '\'' +
                ", waitingSince=" + waitingSince +
                ", priorityScore=" + priorityScore +
                '}';
    }
}

