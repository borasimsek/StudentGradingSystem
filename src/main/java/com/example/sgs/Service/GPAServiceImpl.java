package com.example.sgs.Service;
import com.example.sgs.Repository.GPARepository;
import com.example.sgs.Repository.GradeRepository;
import com.example.sgs.model.Grade;
import java.util.List;
public abstract class GPAServiceImpl implements GPAService {
    private final GPARepository gpaRepository;
    private final GradeRepository gradeRepository;
    private double gpa; // Internal state to hold GPA temporarily

    public GPAServiceImpl(GPARepository gpaRepository, GradeRepository gradeRepository) {
        this.gpaRepository = gpaRepository;
        this.gradeRepository = gradeRepository;
    }

    @Override
    public double getGPA() {
        return gpa;
    }

    @Override
    public void setGPA(double gpa) {
        this.gpa = gpa;
    }
    @Override
    public double calculateGPA(String studentId) {
        // Fetch all grades for the student
        List<Double> grades = gradeRepository.findGradesByStudentId(studentId);

        if (grades == null || grades.isEmpty()) {
            System.out.println("No grades available for student ID: " + studentId);
            return 0.0; // Default GPA if no grades exist
        }

        // Calculate the GPA as the average of all grade values
        double totalGrades = grades.stream().mapToDouble(Double::doubleValue).sum();
        double calculatedGPA = totalGrades / grades.size();

        this.setGPA(calculatedGPA); // Update the internal GPA state
        return calculatedGPA;
    }
    @Override
    public void updateGPA(String studentId) {
        // Calculate the latest GPA
        double updatedGPA = this.calculateGPA(studentId);

        // Save or update the GPA in the repository
        gpaRepository.saveGPA(studentId, updatedGPA);
        System.out.println("Updated GPA for student ID: " + studentId + " to: " + updatedGPA);
    }
}

