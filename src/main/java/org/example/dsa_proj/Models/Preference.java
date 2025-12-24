package org.example.dsa_proj.Models;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Preference Entity
 * 
 * Represents student lifestyle preferences for better roommate matching.
 * Used by the compatibility algorithm to find suitable room assignments.
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
@Table(name = "student_preferences", indexes = {
    @Index(name = "idx_preference_student", columnList = "student_id"),
    @Index(name = "idx_preference_study_time", columnList = "study_time"),
    @Index(name = "idx_preference_sleep_time", columnList = "sleep_time"),
    @Index(name = "idx_preference_gender", columnList = "roommate_gender_preference")
})
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "preference_id")
    private Long preferenceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false, 
                foreignKey = @ForeignKey(name = "FK_preference_student"))
    private Student student;

    @Pattern(regexp = "^(Morning|Afternoon|Evening|Night|Flexible)$", 
             message = "Study time must be Morning, Afternoon, Evening, Night, or Flexible")
    @Column(name = "study_time", length = 20)
    private String studyTime;

    @Pattern(regexp = "^(Early|Normal|Late|Very Late|Flexible)$", 
             message = "Sleep time must be Early, Normal, Late, Very Late, or Flexible")
    @Column(name = "sleep_time", length = 20)
    private String sleepTime;

    @Size(max = 200, message = "Interests cannot exceed 200 characters")
    @Column(name = "interests", length = 200)
    private String interests;

    @Pattern(regexp = "^(Same|Different|Any)$", 
             message = "Roommate gender preference must be Same, Different, or Any")
    @Column(name = "roommate_gender_preference", length = 15)
    private String roommateGenderPreference;

    @Pattern(regexp = "^(Quiet|Moderate|Social|Very Social)$", 
             message = "Noise level must be Quiet, Moderate, Social, or Very Social")
    @Column(name = "noise_level_preference", length = 20)
    private String noiseLevelPreference;

    @Pattern(regexp = "^(Very Clean|Clean|Normal|Flexible)$", 
             message = "Cleanliness level must be Very Clean, Clean, Normal, or Flexible")
    @Column(name = "cleanliness_level", length = 20)
    private String cleanlinessLevel;

    @Column(name = "guest_policy_preference", length = 30)
    private String guestPolicyPreference; // Frequent, Occasional, Rare, None

    @Column(name = "study_group_preference", length = 20)
    private String studyGroupPreference; // Individual, Small Group, Large Group, Any

    /**
     * Calculate compatibility score with another student's preferences
     * @param otherPrefs Other student's preferences
     * @return Compatibility score (0-100)
     */
    public int calculateCompatibilityScore(Preference otherPrefs) {
        if (otherPrefs == null) return 0;
        
        int score = 0;
        int totalFactors = 0;

        // Study time compatibility
        if (this.studyTime != null && otherPrefs.studyTime != null) {
            totalFactors++;
            if (this.studyTime.equals(otherPrefs.studyTime) || 
                "Flexible".equals(this.studyTime) || 
                "Flexible".equals(otherPrefs.studyTime)) {
                score += 20;
            }
        }

        // Sleep time compatibility
        if (this.sleepTime != null && otherPrefs.sleepTime != null) {
            totalFactors++;
            if (this.sleepTime.equals(otherPrefs.sleepTime) || 
                "Flexible".equals(this.sleepTime) || 
                "Flexible".equals(otherPrefs.sleepTime)) {
                score += 15;
            }
        }

        // Noise level compatibility
        if (this.noiseLevelPreference != null && otherPrefs.noiseLevelPreference != null) {
            totalFactors++;
            if (this.noiseLevelPreference.equals(otherPrefs.noiseLevelPreference)) {
                score += 25;
            }
        }

        // Cleanliness compatibility
        if (this.cleanlinessLevel != null && otherPrefs.cleanlinessLevel != null) {
            totalFactors++;
            if (this.cleanlinessLevel.equals(otherPrefs.cleanlinessLevel) || 
                "Flexible".equals(this.cleanlinessLevel) || 
                "Flexible".equals(otherPrefs.cleanlinessLevel)) {
                score += 20;
            }
        }

        // Gender preference compatibility
        if (this.roommateGenderPreference != null && otherPrefs.roommateGenderPreference != null) {
            totalFactors++;
            if ("Any".equals(this.roommateGenderPreference) || 
                "Any".equals(otherPrefs.roommateGenderPreference) ||
                this.roommateGenderPreference.equals(otherPrefs.roommateGenderPreference)) {
                score += 20;
            }
        }

        return totalFactors > 0 ? score / totalFactors : 0;
    }

    /**
     * Get a summary of preferences for display
     * @return formatted preference summary
     */
    public String getPreferenceSummary() {
        return String.format("Study: %s, Sleep: %s, Noise: %s, Cleanliness: %s", 
                           studyTime != null ? studyTime : "Any",
                           sleepTime != null ? sleepTime : "Any",
                           noiseLevelPreference != null ? noiseLevelPreference : "Any",
                           cleanlinessLevel != null ? cleanlinessLevel : "Any");
    }

    @Override
    public String toString() {
        return "Preference{" +
                "preferenceId=" + preferenceId +
                ", student=" + (student != null ? student.getName() : "null") +
                ", studyTime='" + studyTime + '\'' +
                ", sleepTime='" + sleepTime + '\'' +
                ", noiseLevelPreference='" + noiseLevelPreference + '\'' +
                ", cleanlinessLevel='" + cleanlinessLevel + '\'' +
                '}';
    }
}
