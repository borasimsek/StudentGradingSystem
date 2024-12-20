package com.example.sgs.Entities;

public class Grade {

    private int gradeId;
    private User student;
    private Course course;
    private Double grade;
    private String comments;
    private int year;
    private Course.Term term;
    private Double weight;

    // Constructor
    public Grade(int gradeId, User student, Course course, Double grade, String comments, int year, Course.Term term, Double weight) {
        this.gradeId = gradeId;
        this.student = student;
        this.course = course;
        this.grade = grade;
        this.comments = comments;
        this.year = year;
        this.term = term;
        this.weight = weight;
    }

    // Getters and Setters
    public int getGradeId() { return gradeId; }
    public void setGradeId(int gradeId) { this.gradeId = gradeId; }

    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public Double getGrade() { return grade; }
    public void setGrade(Double grade) { this.grade = grade; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public Course.Term getTerm() { return term; }
    public void setTerm(Course.Term term) { this.term = term; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
}
