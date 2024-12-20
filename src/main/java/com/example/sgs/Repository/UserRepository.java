package com.example.sgs.Repository;

import com.example.sgs.Entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRepository {

    private final Connection connection;

    // Constructor to accept a database connection
    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    public Optional<User> findByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new User(
                            rs.getInt("user_id"),
                            rs.getString("email"),
                            rs.getString("password_hash"),
                            User.UserType.valueOf(rs.getString("user_type").toUpperCase()),
                            rs.getString("first_name"),
                            rs.getString("last_name")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding user by email: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty(); // Return empty Optional if no user is found
    }


    public boolean save(User user) {
        String query = "INSERT INTO users (email, password_hash, user_type, first_name, last_name) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getUserType().toString());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Error saving user: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
