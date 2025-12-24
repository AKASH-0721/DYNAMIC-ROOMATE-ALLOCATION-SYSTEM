package org.example.dsa_proj.Models;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Room Swap Request Entity
 * 
 * Represents requests from students to change their current room allocation.
 * Tracks the status and processing of room change requests.
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
@Table(name = "room_swap_requests", indexes = {
    @Index(name = "idx_swap_student", columnList = "student_id"),
    @Index(name = "idx_swap_status", columnList = "status"),
    @Index(name = "idx_swap_request_date", columnList = "request_date"),
    @Index(name = "idx_swap_current_room", columnList = "current_room_id"),
    @Index(name = "idx_swap_requested_type", columnList = "requested_room_type")
})
public class RoomSwapRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "swap_id")
    private Long swapId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false, 
                foreignKey = @ForeignKey(name = "FK_swap_student"))
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_room_id", nullable = false, 
                foreignKey = @ForeignKey(name = "FK_swap_current_room"))
    private Room currentRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_room_id", 
                foreignKey = @ForeignKey(name = "FK_swap_requested_room"))
    private Room requestedRoom; // Specific room if requested

    @Size(max = 20, message = "Requested room type cannot exceed 20 characters")
    @Column(name = "requested_room_type", length = 20)
    private String requestedRoomType;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "^(Pending|Under Review|Approved|Rejected|Completed|Cancelled)$", 
             message = "Status must be Pending, Under Review, Approved, Rejected, Completed, or Cancelled")
    @Column(name = "status", nullable = false, length = 20)
    private String status = "Pending";

    @NotNull(message = "Request date is required")
    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;

    @Column(name = "processed_date")
    private LocalDateTime processedDate;

    @Size(max = 500, message = "Reason cannot exceed 500 characters")
    @Column(name = "reason", length = 500)
    private String reason; // Why the swap is requested

    @Size(max = 500, message = "Admin notes cannot exceed 500 characters")
    @Column(name = "admin_notes", length = 500)
    private String adminNotes; // Admin comments on the request

    @Column(name = "processed_by", length = 50)
    private String processedBy; // Admin who processed the request

    @Min(value = 1, message = "Priority level must be at least 1")
    @Max(value = 5, message = "Priority level cannot exceed 5")
    @Column(name = "priority_level")
    private Integer priorityLevel = 3; // 1 = Low, 3 = Normal, 5 = High

    /**
     * Check if the request is still pending
     * @return true if request is pending or under review
     */
    public boolean isPending() {
        return "Pending".equals(status) || "Under Review".equals(status);
    }

    /**
     * Check if the request has been processed
     * @return true if request is approved, rejected, completed, or cancelled
     */
    public boolean isProcessed() {
        return "Approved".equals(status) || "Rejected".equals(status) || 
               "Completed".equals(status) || "Cancelled".equals(status);
    }

    /**
     * Mark the request as approved
     * @param processedBy Admin who approved the request
     * @param notes Additional notes
     */
    public void approve(String processedBy, String notes) {
        this.status = "Approved";
        this.processedBy = processedBy;
        this.processedDate = LocalDateTime.now();
        this.adminNotes = notes;
    }

    /**
     * Mark the request as rejected
     * @param processedBy Admin who rejected the request
     * @param reason Reason for rejection
     */
    public void reject(String processedBy, String reason) {
        this.status = "Rejected";
        this.processedBy = processedBy;
        this.processedDate = LocalDateTime.now();
        this.adminNotes = reason;
    }

    /**
     * Mark the request as completed
     */
    public void complete() {
        this.status = "Completed";
        this.processedDate = LocalDateTime.now();
    }

    /**
     * Get the number of days since the request was made
     * @return days since request
     */
    public long getDaysSinceRequest() {
        return java.time.temporal.ChronoUnit.DAYS.between(requestDate, LocalDate.now());
    }

    /**
     * Get display information for the swap request
     * @return formatted string with request details
     */
    public String getDisplayInfo() {
        return String.format("Swap Request #%d: %s wants to move from Room %s to %s (%s)", 
                           swapId,
                           student != null ? student.getName() : "Unknown",
                           currentRoom != null ? currentRoom.getRoomNumber() : "Unknown",
                           requestedRoom != null ? requestedRoom.getRoomNumber() : requestedRoomType,
                           status);
    }

    @Override
    public String toString() {
        return "RoomSwapRequest{" +
                "swapId=" + swapId +
                ", student=" + (student != null ? student.getName() : "null") +
                ", currentRoom=" + (currentRoom != null ? currentRoom.getRoomNumber() : "null") +
                ", requestedRoomType='" + requestedRoomType + '\'' +
                ", status='" + status + '\'' +
                ", requestDate=" + requestDate +
                ", priorityLevel=" + priorityLevel +
                '}';
    }
}

