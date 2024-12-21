package com.example.sgs.Repository;

import com.example.sgs.Entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRepository {

    private final Connection connection;

    /**
     * Constructor to accept a database connection
     */
    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    /**
     * Finds a user by their email address
     */
    public Optional<User> findByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String userTypeString = rs.getString("user_type").toUpperCase();

                    // Enum güvenli dönüşümü
                    User.UserType userType;
                    try {
                        userType = User.UserType.valueOf(userTypeString);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error: Invalid user type '" + userTypeString + "' in database for email: " + email);
                        e.printStackTrace();
                        return Optional.empty();
                    }

                    return Optional.of(new User(
                            rs.getInt("user_id"),
                            rs.getString("email"),
                            rs.getString("password_hash"),
                            userType,
                            rs.getString("first_name"),
                            rs.getString("last_name")
                    ));
                } else {
                    System.out.println("No user found with email: " + email);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding user by email: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty(); // Return empty Optional if no user is found
    }

    /**
     * Saves a user to the database
     */
    public boolean save(User user) {
        String query = "INSERT INTO users (email, password_hash, user_type, first_name, last_name) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Set parameters
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getUserType().toString());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());

            // Execute update
            int rowsInserted = stmt.executeUpdate();

            // Log success or failure
            if (rowsInserted > 0) {
                System.out.println("User saved successfully: " + user.getEmail());
            } else {
                System.err.println("Failed to save user: " + user.getEmail());
            }

            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Error saving user: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
