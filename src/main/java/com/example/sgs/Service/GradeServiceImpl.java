package com.example.sgs.Service;
import com.example.sgs.Repository.GradeRepository;

import java.util.List;
public abstract class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;
    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }
    @Override
    public void saveGrade(String studentID, String courseID, double grade) {
        // Save or update the grade for a student in a course
        gradeRepository.saveGrade(studentID, courseID, grade);
        System.out.println("Grade saved for Student ID: " + studentID + ", Course ID: " + courseID + ", Grade: " + grade);
    }
    @Override
    public double getGrade(String studentID, String courseID) {
        // Fetch the grade for a specific student and course
        double grade = gradeRepository.findGrade(studentID, courseID);
        System.out.println("Retrieved grade: " + grade + " for Student ID: " + studentID + ", Course ID: " + courseID);
        return grade;
    }
    @Override
    public double calculateGPA(String studentID) {
        // GPA calculation requires fetching all grades. GradeRepository currently lacks this method,
        // so we'll assume all grades are stored somewhere accessible (extend if needed).
        List<Double> allGrades = gradeRepository.findGradesByStudentId(studentID);

        if (allGrades.isEmpty()) {
            System.out.println("No grades available for Student ID: " + studentID);
            return 0.0;
        }

        // Calculate GPA as the average of all grades
        double total = 0.0;
        for (double grade : allGrades) {
            total += grade;
        }

        double gpa = total / allGrades.size();
        System.out.println("Calculated GPA: " + gpa + " for Student ID: " + studentID);
        return gpa;
    }
}

