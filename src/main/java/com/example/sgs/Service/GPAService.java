package com.example.sgs.Service;

public interface GPAService {
    double getGPA();
    void setGPA(double gpa);
    double calculateGPA(String studentId);
    void updateGPA(String studentId);
}
