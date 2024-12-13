
package model;

/**
 * Represents a Grade assigned to a student for a course.
 */
public class Grade {
    private String studentId;
    private String courseId;
    private double gradeValue;

    // Constructor
    public Grade(String studentId, String courseId, double gradeValue) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.gradeValue = gradeValue;
    }

    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public double getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(double gradeValue) {
        this.gradeValue = gradeValue;
    }

    @Override
    public String toString() {
        return "Grade{" +
            "studentId='" + studentId + '\'' +
            ", courseId='" + courseId + '\'' +
            ", gradeValue=" + gradeValue +
            '}';
    }
}
