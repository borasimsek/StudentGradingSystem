package com.example.sgs.Entities;

public class Prerequisite {

    private Course course;
    private Course prerequisite;

    // Constructor
    public Prerequisite(Course course, Course prerequisite) {
        this.course = course;
        this.prerequisite = prerequisite;
    }

    // Getters and Setters
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public Course getPrerequisite() { return prerequisite; }
    public void setPrerequisite(Course prerequisite) { this.prerequisite = prerequisite; }
}
