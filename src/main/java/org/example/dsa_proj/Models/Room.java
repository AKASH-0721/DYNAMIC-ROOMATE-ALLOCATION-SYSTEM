package org.example.dsa_proj.Models;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * Room Entity
 * 
 * Represents a hostel room in the management system.
 * Contains room details, capacity information, and current occupancy status.
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
@Table(name = "rooms", indexes = {
    @Index(name = "idx_room_status", columnList = "status"),
    @Index(name = "idx_room_type", columnList = "room_type"),
    @Index(name = "idx_room_block", columnList = "hostel_block"),
    @Index(name = "idx_room_number", columnList = "room_number", unique = true)
})
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @NotBlank(message = "Room number is required")
    @Pattern(regexp = "^[A-Z]\\d{3}$", message = "Room number must follow format: A101, B205, etc.")
    @Column(name = "room_number", nullable = false, unique = true, length = 10)
    private String roomNumber;

    @Min(value = 1, message = "Room capacity must be at least 1")
    @Max(value = 4, message = "Room capacity cannot exceed 4")
    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Min(value = 0, message = "Occupancy cannot be negative")
    @Column(name = "occupancy", nullable = false)
    private int occupancy = 0;

    @NotBlank(message = "Hostel block is required")
    @Pattern(regexp = "^[A-Z]$", message = "Hostel block must be a single uppercase letter")
    @Column(name = "hostel_block", nullable = false, length = 1)
    private String hostelBlock;

    @NotBlank(message = "Room type is required")
    @Pattern(regexp = "^(Single|Double|Triple|Quad)$", 
             message = "Room type must be Single, Double, Triple, or Quad")
    @Column(name = "room_type", nullable = false, length = 20)
    private String roomType;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "^(Available|Full|Maintenance|Reserved)$", 
             message = "Status must be Available, Full, Maintenance, or Reserved")
    @Column(name = "status", nullable = false, length = 20)
    private String status = "Available";

    @OneToMany(mappedBy = "allocatedRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Student> students = new ArrayList<>();

    /**
     * Check if room has available space for more students
     * @return true if room can accommodate more students, false otherwise
     */
    public boolean hasAvailableSpace() {
        return occupancy < capacity && "Available".equals(status);
    }

    /**
     * Check if room is completely full
     * @return true if room is at maximum capacity, false otherwise
     */
    public boolean isFull() {
        return occupancy >= capacity;
    }

    /**
     * Get remaining capacity of the room
     * @return number of additional students that can be accommodated
     */
    public int getRemainingCapacity() {
        return Math.max(0, capacity - occupancy);
    }

    /**
     * Get occupancy percentage
     * @return percentage of room occupancy (0-100)
     */
    public double getOccupancyPercentage() {
        return capacity > 0 ? (double) occupancy / capacity * 100 : 0;
    }

    /**
     * Get room's display information
     * @return formatted string with room details
     */
    public String getDisplayInfo() {
        return String.format("Room %s (%s) - Block %s [%d/%d occupancy]", 
                           roomNumber, roomType, hostelBlock, occupancy, capacity);
    }

    /**
     * Update room status based on current occupancy
     */
    public void updateStatusBasedOnOccupancy() {
        if (!"Maintenance".equals(status) && !"Reserved".equals(status)) {
            this.status = isFull() ? "Full" : "Available";
        }
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", roomNumber='" + roomNumber + '\'' +
                ", roomType='" + roomType + '\'' +
                ", hostelBlock='" + hostelBlock + '\'' +
                ", capacity=" + capacity +
                ", occupancy=" + occupancy +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return Objects.equals(roomId, room.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId);
    }
}
