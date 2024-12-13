package model;

import java.util.Map;

/**
 * Represents a GPA (Grade Point Average) for a student.
 */
public class GPA {
    private String studentId;
    private Map<String, Double> grades;

    // Constructor
    public GPA(String studentId, Map<String, Double> grades) {
        this.studentId = studentId;
        this.grades = grades;
    }

    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Map<String, Double> getGrades() {
        return grades;
    }

    public void setGrades(Map<String, Double> grades) {
        this.grades = grades;
    }

    /**
     * Calculate the GPA based on the grades.
     * @return The calculated GPA.
     * ortalama olarak hesapladım. Krediye göre ağırlıklı ortalama yapılabilir.
     */
    public double calculateGPA() {
        if (grades.isEmpty()) return 0.0;

        double total = 0.0;
        for (double grade : grades.values()) {
            total += grade;
        }
        return total / grades.size();
    }

    @Override
    public String toString() {
        return "GPA{" +
                "studentId='" + studentId + '\'' +
                ", grades=" + grades +
                ", gpa=" + calculateGPA() +
                '}';
    }
}