package com.example.sgs.Entities;

public class Course {

    private int courseId;
    private String courseName;
    private String courseCode;
    private User instructor;
    private int credits;
    private int quota;
    private int year;
    private Term term;
    private String faculty;

    // Constructor
    public Course(int courseId, String courseName, String courseCode, User instructor, int credits, int quota, int year, Term term, String faculty) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.instructor = instructor;
        this.credits = credits;
        this.quota = quota;
        this.year = year;
        this.term = term;
        this.faculty = faculty;
    }

    // Getters and Setters


    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public User getInstructor() { return instructor; }
    public void setInstructor(User instructor) { this.instructor = instructor; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public int getQuota() { return quota; }
    public void setQuota(int quota) { this.quota = quota; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public Term getTerm() { return term; }
    public void setTerm(Term term) { this.term = term; }

    public String getFaculty() { return faculty; }
    public void setFaculty(String faculty) { this.faculty = faculty; }

    // Enum for Term (Fall, Spring, Summer)
    public enum Term {
        FALL,
        SPRING,
        SUMMER
    }
}
