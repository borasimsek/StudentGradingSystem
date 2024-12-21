package com.example.sgs.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GPARepository {
    private final Connection connection;

    public GPARepository(Connection connection) {
        this.connection = connection;
    }

    // Calculate Cumulative GPA for a Student
    public double calculateCumulativeGPA(int studentId) {
        String query = "SELECT SUM(grade * weight) / SUM(weight) AS gpa FROM grades WHERE student_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("gpa");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error calculating cumulative GPA for studentId: " + studentId + " - " + e.getMessage());
        }
        return 0.0; // Default if no grades exist
    }

    // Calculate Term GPA for a Student
    public double calculateTermGPA(int studentId, int year, String term) {
        String query = "SELECT SUM(grade * weight) / SUM(weight) AS gpa FROM grades WHERE student_id = ? AND year = ? AND term = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, year);
            stmt.setString(3, term);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("gpa");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error calculating term GPA for studentId: " + studentId + ", year: " + year + ", term: " + term + " - " + e.getMessage());
        }
        return 0.0; // Default if no grades exist
    }
}
