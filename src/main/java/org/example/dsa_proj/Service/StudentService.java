package org.example.dsa_proj.Service;


import org.example.dsa_proj.Models.Student;
import org.example.dsa_proj.Rep.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepo.findById(id);
    }

    public Student saveStudent(Student student) {
        return studentRepo.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepo.deleteById(id);
    }

    // Compatibility scoring between two students
    public int calculateCompatibility(Student a, Student b) {
        int score = 0;
        if (a.getGender().equalsIgnoreCase(b.getGender())) score += 2;
        if (a.getBranch().equalsIgnoreCase(b.getBranch())) score += 1;
        if (a.getPreferenceType().equalsIgnoreCase(b.getPreferenceType())) score += 3;
        return score;
    }

    public void allocateRoom(Long studentId, String roomType) {
    }
}
