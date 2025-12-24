package org.example.dsa_proj.Models;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

/**
 * Allocation History Entity
 * 
 * Tracks the complete history of room allocations and deallocations for students.
 * Provides audit trail for all room assignments and changes.
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
@Table(name = "allocation_history", indexes = {
    @Index(name = "idx_alloc_history_student", columnList = "student_id"),
    @Index(name = "idx_alloc_history_room", columnList = "room_id"),
    @Index(name = "idx_alloc_history_date", columnList = "allocation_date"),
    @Index(name = "idx_alloc_history_active", columnList = "deallocation_date")
})
public class AllocationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "allocation_id")
    private Long allocationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false, 
                foreignKey = @ForeignKey(name = "FK_allocation_student"))
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false, 
                foreignKey = @ForeignKey(name = "FK_allocation_room"))
    private Room room;

    @NotNull(message = "Allocation date is required")
    @Column(name = "allocation_date", nullable = false)
    private LocalDate allocationDate;

    @Column(name = "deallocation_date")
    private LocalDate deallocationDate;

    @Size(max = 100, message = "Reason cannot exceed 100 characters")
    @Column(name = "reason", length = 100)
    private String reason;   // e.g., "Initial Allocation", "Room Change", "Graduated", "Disciplinary"

    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    @Column(name = "notes", length = 500)
    private String notes;    // Additional details about the allocation/deallocation

    @Column(name = "created_by", length = 50)
    private String createdBy; // Admin who made the allocation

    /**
     * Check if this allocation is currently active
     * @return true if student is still allocated to this room
     */
    public boolean isActive() {
        return deallocationDate == null;
    }

    /**
     * Get duration of allocation in days
     * @return number of days allocated, or days until now if still active
     */
    public long getAllocationDurationDays() {
        LocalDate endDate = deallocationDate != null ? deallocationDate : LocalDate.now();
        return java.time.temporal.ChronoUnit.DAYS.between(allocationDate, endDate);
    }

    /**
     * Mark this allocation as ended
     * @param reason Reason for deallocation
     * @param deallocatedBy Admin who performed deallocation
     */
    public void markAsEnded(String reason, String deallocatedBy) {
        this.deallocationDate = LocalDate.now();
        this.reason = reason;
        this.createdBy = deallocatedBy;
    }

    @Override
    public String toString() {
        return "AllocationHistory{" +
                "allocationId=" + allocationId +
                ", student=" + (student != null ? student.getName() : "null") +
                ", room=" + (room != null ? room.getRoomNumber() : "null") +
                ", allocationDate=" + allocationDate +
                ", deallocationDate=" + deallocationDate +
                ", reason='" + reason + '\'' +
                ", active=" + isActive() +
                '}';
    }
}
