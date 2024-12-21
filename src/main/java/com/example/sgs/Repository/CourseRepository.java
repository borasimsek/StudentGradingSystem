package com.example.sgs.Repository;

import com.example.sgs.Entities.Course;
import com.example.sgs.Entities.Prerequisite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseRepository {

    private final Connection connection;

    public CourseRepository(Connection connection) {
        this.connection = connection;
    }

    // Find a course by ID
    public Optional<Course> findById(int courseId) {
        String query = "SELECT * FROM courses WHERE course_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToCourse(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding course by ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    // Find a course by name
    public List<Course> findByCourseName(String courseName) {
        String query = "SELECT * FROM courses WHERE course_name LIKE ?";
        List<Course> courses = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + courseName + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    courses.add(mapResultSetToCourse(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding courses by name: " + e.getMessage());
        }
        return courses;
    }

    // Find all courses
    public List<Course> findAll() {
        String query = "SELECT * FROM courses";
        List<Course> courses = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                courses.add(mapResultSetToCourse(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all courses: " + e.getMessage());
        }
        return courses;
    }

    // Save a new course
    public boolean save(Course course) {
        String query = "INSERT INTO courses (course_name, course_code, instructor_id, credits, quota, year, term, faculty) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, course.getCourseName());
            stmt.setString(2, course.getCourseCode());
            stmt.setInt(3, course.getInstructor().getUserId());
            stmt.setInt(4, course.getCredits());
            stmt.setInt(5, course.getQuota());
            stmt.setInt(6, course.getYear());
            stmt.setString(7, course.getTerm().toString());
            stmt.setString(8, course.getFaculty());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error saving course: " + e.getMessage());
        }
        return false;
    }

    // Find prerequisites for a specific course
    public List<Prerequisite> findPrerequisitesByCourseId(int courseId) {
        String query = "SELECT p.course_id, p.prerequisite_id, " +
                "c1.course_name AS course_name, c1.course_code AS course_code, " +
                "c2.course_name AS prerequisite_name, c2.course_code AS prerequisite_code " +
                "FROM prerequisites p " +
                "JOIN courses c1 ON p.course_id = c1.course_id " +
                "JOIN courses c2 ON p.prerequisite_id = c2.course_id " +
                "WHERE p.course_id = ?";
        List<Prerequisite> prerequisites = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Course course = new Course(
                            rs.getInt("course_id"),
                            rs.getString("course_name"),
                            rs.getString("course_code"),
                            null, // Instructor can be resolved using UserRepository if needed
                            0,    // Credits are not retrieved in this query
                            0,    // Quota is not retrieved in this query
                            0,    // Year is not retrieved in this query
                            null, // Term is not retrieved in this query
                            null  // Faculty is not retrieved in this query
                    );

                    Course prerequisite = new Course(
                            rs.getInt("prerequisite_id"),
                            rs.getString("prerequisite_name"),
                            rs.getString("prerequisite_code"),
                            null, // Instructor can be resolved using UserRepository if needed
                            0,    // Credits are not retrieved in this query
                            0,    // Quota is not retrieved in this query
                            0,    // Year is not retrieved in this query
                            null, // Term is not retrieved in this query
                            null  // Faculty is not retrieved in this query
                    );

                    prerequisites.add(new Prerequisite(course, prerequisite));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching prerequisites for course ID: " + courseId + " - " + e.getMessage());
        }
        return prerequisites;
    }


    // Helper method to map a ResultSet to a Course object
    private Course mapResultSetToCourse(ResultSet rs) throws SQLException {
        return new Course(
                rs.getInt("course_id"),
                rs.getString("course_name"),
                rs.getString("course_code"),
                null, // Instructor mapping should use UserRepository if necessary
                rs.getInt("credits"),
                rs.getInt("quota"),
                rs.getInt("year"),
                Course.Term.valueOf(rs.getString("term")),
                rs.getString("faculty")
        );
    }
}
