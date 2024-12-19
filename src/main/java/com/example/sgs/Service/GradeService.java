package com.example.sgs.Service;

public interface GradeService {
    void saveGrade(String studentID, String courseID, double grade);
    double getGrade(String studentID, String courseID);
    double calculateGPA(String studentID);
}
