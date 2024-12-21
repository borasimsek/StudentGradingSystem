package com.example.sgs.Repository;


import com.example.sgs.model.Course;
import com.example.sgs.model.Prerequisite;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class CourseRepositoryImpl {
    private Connection connection;

    public CourseRepositoryImpl(Connection connection) {
        this.connection = connection;
    }


    //PREREQUISITES KISMI HATALI OLABILIR BIR DAHA BAKILACAK***
    public List<String> findPrerequisites(String courseId) throws SQLException {
        List<String> prerequisites = new ArrayList<>();
        String query = "SELECT prerequisite_id FROM prerequisites WHERE course_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, courseId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                prerequisites.add(rs.getString("prerequisite_id"));
            }
        }
        return prerequisites;
    }
    public Course findById(String courseId) throws SQLException {
        String query = "SELECT course_id, course_name, credits FROM courses WHERE course_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, courseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                List<String> prerequisites = findPrerequisites(courseId);
                return new Course(
                        rs.getString("course_id"),
                        rs.getString("course_name"),
                        rs.getInt("credits"),
                        prerequisites
                );
            }
        }
        return null;
    }

    public List<Course> findAll() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT course_id, course_name, credits FROM courses";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String courseId = rs.getString("course_id");
                List<String> prerequisites = findPrerequisites(courseId);
                courses.add(new Course(
                        courseId,
                        rs.getString("course_name"),
                        rs.getInt("credits"),
                        prerequisites
                ));
            }
        }
        return courses;
    }


}
