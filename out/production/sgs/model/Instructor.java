package model;

import java.util.List;

/**
 * Represents an Instructor in the system.
 * Extends the abstract User class.
 */
public class Instructor extends User {
    private String instructorId;
    private String department;
    private List<String> assignedCourses;

    // Constructor
    public Instructor(String id, String name, String email, String password, 
                        String instructorId, String department, List<String> assignedCourses) {
        super(id, name, email, password);
        this.instructorId = instructorId;
        this.department = department;
        this.assignedCourses = assignedCourses;
    }

    // Getters and Setters
    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<String> getAssignedCourses() {
        return assignedCourses;
    }

    public void setAssignedCourses(List<String> assignedCourses) {
        this.assignedCourses = assignedCourses;
    }

    // Implement abstract method from User
    @Override
    public String getRole() {
        return "Instructor";
    }

    /**
     * Assign a course to the instructor.
     * @param courseId The ID of the course to assign.
     */
    public void assignCourse(String courseId) {
        if (!assignedCourses.contains(courseId)) {
            assignedCourses.add(courseId);
        }
    }

    /**
     * Remove a course assignment from the instructor.
     * @param courseId The ID of the course to remove.
     * Instructor sınıfı, User sınıfından türetilmiştir. Instructor sınıfı
     * User sınıfının tüm özelliklerini ve metodlarını miras alır. Instructor sınıfı, bir eğitmeni temsil eder ve eğitmenin özelliklerini ve davranışlarını tanımlar.
     *
     *
     * Instructor sınıfı, User sınıfından türetilmiştir ve aşağıdaki özelliklere sahiptir:
     */
    public void unassignCourse(String courseId) {
        assignedCourses.remove(courseId);
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "instructorId='" + instructorId + '\'' +
                ", department='" + department + '\'' +
                ", assignedCourses=" + assignedCourses +
                ", user=" + super.toString() +
                '}';
    }
}
