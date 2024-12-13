// Prerequisite.java
package model;

/**
 * Represents a Prerequisite relationship for a course.
 * dersin ön koşul ilişkisini temsil eder.
 * ders kodunu alabilmek için dersin ön koşulunu alma olayı, olmasa da olur.
 */
public class Prerequisite {
    private String courseId;
    private String prerequisiteId;

    // Constructor
    public Prerequisite(String courseId, String prerequisiteId) {
        this.courseId = courseId;
        this.prerequisiteId = prerequisiteId;
    }

    // Getters and Setters
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getPrerequisiteId() {
        return prerequisiteId;
    }

    public void setPrerequisiteId(String prerequisiteId) {
        this.prerequisiteId = prerequisiteId;
    }

    @Override
    public String toString() {
        return "Prerequisite{" +
                "courseId='" + courseId + '\'' +
                ", prerequisiteId='" + prerequisiteId + '\'' +
                '}';
    }
}