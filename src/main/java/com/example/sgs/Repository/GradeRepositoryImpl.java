package com.example.sgs.Repository;

import com.example.sgs.model.Grade;
import com.example.sgs.Entities.*;
import java.sql.*;

public class GradeRepositoryImpl {
    private Connection connection;
    public GradeRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    public Grade findByStudentAndCourse(int studentId, int courseId) throws SQLException {
        String query = "SELECT * FROM grades WHERE student_id = ? AND course_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Grade(rs.getString("student_id"), rs.getString("course_id"), rs.getDouble("grade"));
            }
        }
        return null;
    }

    public void save(Grade grade) throws SQLException {
        String query = "INSERT INTO grades (student_id, course_id, grade) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, grade.getStudentId());
            stmt.setString(2, grade.getCourseId());
            stmt.setDouble(3, grade.getGradeValue());
            stmt.executeUpdate();
        }
    }

    public void update(Grade grade) throws SQLException {
        String query = "UPDATE grades SET grade = ? WHERE student_id = ? AND course_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, grade.getGradeValue());
            stmt.setString(2, grade.getStudentId());
            stmt.setString(3, grade.getCourseId());
            stmt.executeUpdate();
        }
    }
}
