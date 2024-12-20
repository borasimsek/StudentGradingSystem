package com.example.sgs.Entities;

import java.sql.Timestamp;

public class Enrollment {

    private int enrollmentId;
    private User student;
    private Course course;
    private Timestamp enrollmentDate;

    // Constructor
    public Enrollment(int enrollmentId, User student, Course course, Timestamp enrollmentDate) {
        this.enrollmentId = enrollmentId;
        this.student = student;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
    }

    // Getters and Setters
    public int getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(int enrollmentId) { this.enrollmentId = enrollmentId; }

    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public Timestamp getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(Timestamp enrollmentDate) { this.enrollmentDate = enrollmentDate; }
}
