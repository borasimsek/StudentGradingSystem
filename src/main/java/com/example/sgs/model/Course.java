package com.example.sgs.model;

import java.util.List;

/**
 * Represents a Course in the system.
 */
public class Course {
    private String courseId;
    private String courseName;
    private int creditHours;
    private List<String> prerequisites;

    // Constructor
    public Course(String courseId, String courseName, int creditHours, List<String> prerequisites) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.creditHours = creditHours;
        this.prerequisites = prerequisites;
    }

    // Getters and Setters
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public List<String> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<String> prerequisites) {
        this.prerequisites = prerequisites;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", creditHours=" + creditHours +
                ", prerequisites=" + prerequisites +
                '}';
    }
}
