package org.example.dsa_proj.Models;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * Admin Entity
 * 
 * Represents system administrators and their access levels.
 * Manages authentication and authorization for hostel management operations.
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
@Table(name = "system_admins", indexes = {
    @Index(name = "idx_admin_username", columnList = "username", unique = true),
    @Index(name = "idx_admin_role", columnList = "role"),
    @Index(name = "idx_admin_active", columnList = "is_active")
})
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers, and underscores")
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Column(name = "password_hash", nullable = false, length = 255)
    private String password; // Should be hashed

    @NotBlank(message = "Role is required")
    @Pattern(regexp = "^(SUPER_ADMIN|HOSTEL_MANAGER|ROOM_COORDINATOR|MAINTENANCE_STAFF)$", 
             message = "Role must be SUPER_ADMIN, HOSTEL_MANAGER, ROOM_COORDINATOR, or MAINTENANCE_STAFF")
    @Column(name = "role", nullable = false, length = 30)
    private String role;

    @Size(max = 100, message = "Full name cannot exceed 100 characters")
    @Column(name = "full_name", length = 100)
    private String fullName;

    @Email(message = "Please provide a valid email address")
    @Column(name = "email", length = 100)
    private String email;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Please provide a valid phone number")
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts = 0;

    @Column(name = "account_locked_until")
    private LocalDateTime accountLockedUntil;

    /**
     * Check if the admin account is currently active and not locked
     * @return true if account is active and not locked
     */
    public boolean isAccountValid() {
        return isActive && (accountLockedUntil == null || accountLockedUntil.isBefore(LocalDateTime.now()));
    }

    /**
     * Check if admin has super admin privileges
     * @return true if role is SUPER_ADMIN
     */
    public boolean isSuperAdmin() {
        return "SUPER_ADMIN".equals(role);
    }

    /**
     * Check if admin can manage rooms
     * @return true if role allows room management
     */
    public boolean canManageRooms() {
        return "SUPER_ADMIN".equals(role) || "HOSTEL_MANAGER".equals(role) || "ROOM_COORDINATOR".equals(role);
    }

    /**
     * Check if admin can manage students
     * @return true if role allows student management
     */
    public boolean canManageStudents() {
        return "SUPER_ADMIN".equals(role) || "HOSTEL_MANAGER".equals(role);
    }

    /**
     * Record a successful login
     */
    public void recordSuccessfulLogin() {
        this.lastLogin = LocalDateTime.now();
        this.failedLoginAttempts = 0;
        this.accountLockedUntil = null;
    }

    /**
     * Record a failed login attempt
     */
    public void recordFailedLogin() {
        this.failedLoginAttempts++;
        if (this.failedLoginAttempts >= 5) {
            // Lock account for 30 minutes after 5 failed attempts
            this.accountLockedUntil = LocalDateTime.now().plusMinutes(30);
        }
    }

    /**
     * Get display name for the admin
     * @return full name if available, otherwise username
     */
    public String getDisplayName() {
        return fullName != null && !fullName.trim().isEmpty() ? fullName : username;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", fullName='" + fullName + '\'' +
                ", isActive=" + isActive +
                ", lastLogin=" + lastLogin +
                '}';
    }
}


