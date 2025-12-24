package org.example.dsa_proj.Service;

import org.example.dsa_proj.Models.Student;
import org.example.dsa_proj.Models.Preference;
import org.example.dsa_proj.Rep.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Student Service
 * 
 * Provides business logic for student management in the hostel system.
 * Handles student operations, compatibility scoring, and room allocation processes.
 * 
 * @author DSA Project Team
 * @version 1.0
 * @since 2024
 */
@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    /**
     * Retrieve all students from the database
     * @return List of all students
     */
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    /**
     * Find a student by their unique ID
     * @param id Student ID
     * @return Optional containing student if found, empty otherwise
     */
    public Optional<Student> getStudentById(Long id) {
        return studentRepo.findById(id);
    }

    /**
     * Save or update a student record
     * @param student Student entity to save
     * @return Saved student entity
     * @throws IllegalArgumentException if student data is invalid
     */
    public Student saveStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        return studentRepo.save(student);
    }

    /**
     * Delete a student by ID
     * @param id Student ID to delete
     * @throws IllegalArgumentException if student not found
     */
    public void deleteStudent(Long id) {
        if (!studentRepo.existsById(id)) {
            throw new IllegalArgumentException("Student with ID " + id + " not found");
        }
        studentRepo.deleteById(id);
    }

    /**
     * Find students by their current status
     * @param status Student status (Allocated, Waiting, Left, etc.)
     * @return List of students with the specified status
     */
    public List<Student> getStudentsByStatus(String status) {
        return studentRepo.findByStatus(status);
    }

    /**
     * Find students by gender
     * @param gender Gender filter
     * @return List of students of the specified gender
     */
    public List<Student> getStudentsByGender(String gender) {
        return studentRepo.findByGender(gender);
    }

    /**
     * Find students by room type preference
     * @param roomTypePreference Room type preference
     * @return List of students with the specified room preference
     */
    public List<Student> getStudentsByRoomPreference(String roomTypePreference) {
        return studentRepo.findByRoomTypePreference(roomTypePreference);
    }

    /**
     * Calculate compatibility score between two students
     * 
     * Scoring system:
     * - Same gender: +2 points
     * - Same branch: +1 point  
     * - Same preference type: +3 points
     * - Maximum possible score: 6 points
     * 
     * @param studentA First student
     * @param studentB Second student
     * @return Compatibility score (0-6)
     */
    public int calculateCompatibility(Student studentA, Student studentB) {
        if (studentA == null || studentB == null) {
            return 0;
        }

        int compatibilityScore = 0;

        // Gender compatibility (important for room sharing)
        if (studentA.getGender() != null && studentA.getGender().equalsIgnoreCase(studentB.getGender())) {
            compatibilityScore += 2;
        }

        // Academic branch compatibility  
        if (studentA.getBranch() != null && studentA.getBranch().equalsIgnoreCase(studentB.getBranch())) {
            compatibilityScore += 1;
        }

        // Lifestyle preference compatibility
        if (studentA.getPreferenceType() != null && 
            studentA.getPreferenceType().equalsIgnoreCase(studentB.getPreferenceType())) {
            compatibilityScore += 3;
        }

        return compatibilityScore;
    }

    /**
     * Find the most compatible roommates for a given student
     * @param student Target student
     * @param maxResults Maximum number of compatible students to return
     * @return List of students sorted by compatibility score (highest first)
     */
    public List<Student> findCompatibleRoommates(Student student, int maxResults) {
        List<Student> allStudents = getAllStudents();
        
        return allStudents.stream()
                .filter(s -> !s.equals(student)) // Exclude the student themselves
                .filter(Student::isWaiting) // Only consider students waiting for allocation
                .map(s -> new AbstractMap.SimpleEntry<>(s, calculateCompatibility(student, s)))
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue())) // Sort by score descending
                .limit(maxResults)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Process room allocation for a student
     * @param studentId Student ID
     * @param roomType Requested room type
     * @throws IllegalArgumentException if student not found or already allocated
     */
    public void allocateRoom(Long studentId, String roomType) {
        Optional<Student> studentOpt = getStudentById(studentId);
        if (!studentOpt.isPresent()) {
            throw new IllegalArgumentException("Student with ID " + studentId + " not found");
        }

        Student student = studentOpt.get();
        
        if (student.isAllocated()) {
            throw new IllegalArgumentException("Student is already allocated to a room");
        }

        // Update student status to indicate processing
        student.setStatus("Processing Allocation");
        student.setRoomTypePreference(roomType);
        saveStudent(student);

        // Note: Actual room allocation logic should be handled by AllocationService
        // This method just marks the student as being processed
    }

    /**
     * Get statistics about student distribution
     * @return Map containing various statistics
     */
    public Map<String, Object> getStudentStatistics() {
        List<Student> allStudents = getAllStudents();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalStudents", allStudents.size());
        stats.put("allocatedStudents", allStudents.stream().mapToInt(s -> s.isAllocated() ? 1 : 0).sum());
        stats.put("waitingStudents", allStudents.stream().mapToInt(s -> s.isWaiting() ? 1 : 0).sum());
        
        // Gender distribution
        Map<String, Long> genderDistribution = allStudents.stream()
                .collect(Collectors.groupingBy(Student::getGender, Collectors.counting()));
        stats.put("genderDistribution", genderDistribution);
        
        // Branch distribution  
        Map<String, Long> branchDistribution = allStudents.stream()
                .collect(Collectors.groupingBy(Student::getBranch, Collectors.counting()));
        stats.put("branchDistribution", branchDistribution);
        
        return stats;
    }
}
