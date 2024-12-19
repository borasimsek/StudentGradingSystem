package com.example.sgs.model;

import java.util.List;

/**
 * Represents a Student in the system.
 * Extends the abstract User class.
 */
public class Student extends User {
    private String studentId;
    private String major;
    private int year;
    private List<String> enrolledCourses;

    // Constructor
    public Student(String id, String name, String email, String password, 
                    String studentId, String major, int year, List<String> enrolledCourses) {
        super(id, name, email, password);
        this.studentId = studentId;
        this.major = major;
        this.year = year;
        this.enrolledCourses = enrolledCourses;
    }

    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<String> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<String> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    @Override
    public String getRole() {
        return "Student";
    }

    public void addCourse(String courseId) {
        if (!enrolledCourses.contains(courseId)) {
            enrolledCourses.add(courseId);
        }
    }

    public void removeCourse(String courseId) {
        enrolledCourses.remove(courseId);
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", major='" + major + '\'' +
                ", year=" + year +
                ", enrolledCourses=" + enrolledCourses +
                ", user=" + super.toString() +
                '}';
    }
}
