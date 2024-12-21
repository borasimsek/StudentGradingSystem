package com.example.sgs.Service;

import com.example.sgs.Repository.GradeRepositoryImpl;
import com.example.sgs.model.Grade;
import java.sql.SQLException;

public class GradeService {
    private final GradeRepositoryImpl gradeRepository;

    public GradeService(GradeRepositoryImpl gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    // Belirli bir öğrencinin belirli bir ders için notunu bulma
    public Grade findGradeByStudentAndCourse(int studentId, int courseId) {
        try {
            return gradeRepository.findByStudentAndCourse(studentId, courseId);
        } catch (SQLException e) {
            System.err.println("Error finding grade: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Yeni bir not ekleme
    public void saveGrade(Grade grade) {
        try {
            gradeRepository.save(grade);
            System.out.println("Grade saved successfully.");
        } catch (SQLException e) {
            System.err.println("Error saving grade: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Mevcut bir notu güncelleme
    public void updateGrade(Grade grade) {
        try {
            gradeRepository.update(grade);
            System.out.println("Grade updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating grade: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
