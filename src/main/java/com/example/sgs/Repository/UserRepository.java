package com.example.sgs.Repository;

import com.example.sgs.Entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
                    String userTypeString = rs.getString("user_type").replace("i","ı").toUpperCase();

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

    public List<User> findStudentsByInstructorAndTerm(int instructorId, int year, String term) {
        List<User> students = new ArrayList<>();
        String query = """
        SELECT u.user_id, u.email, u.first_name, u.last_name, u.user_type
        FROM users u
        INNER JOIN enrollments e ON u.user_id = e.student_id
        INNER JOIN courses c ON e.course_id = c.course_id
        WHERE c.instructor_id = ? AND c.year = ? AND c.term = ?
    """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, instructorId); // Öğretmen ID'si
            stmt.setInt(2, year);         // Dönem yılı
            stmt.setString(3, term);      // Dönem bilgisi (örneğin "Fall", "Spring")

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User student = new User(
                            rs.getInt("user_id"),            // Kullanıcı ID'si
                            rs.getString("email"),          // Email
                            null,                           // Şifreyi getirmiyoruz
                            User.UserType.valueOf(rs.getString("user_type").toUpperCase()), // UserType Enum
                            rs.getString("first_name"),     // Ad
                            rs.getString("last_name")       // Soyad
                    );
                    students.add(student);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding students by instructor and term: " + e.getMessage());
            e.printStackTrace();
        }

        return students;
    }
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT user_id, email, first_name, last_name, user_type FROM users";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                        rs.getInt("user_id"),                      // Kullanıcı ID
                        rs.getString("email"),                    // Email
                        null,                                      // Şifre, burada alınmıyor
                        User.UserType.valueOf(rs.getString("user_type").toUpperCase()), // UserType Enum
                        rs.getString("first_name"),               // Ad
                        rs.getString("last_name")                 // Soyad
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching users: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    public boolean delete(int userId) {
        String query = "DELETE FROM users WHERE user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Eğer etkilenen satır varsa silme başarılıdır
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
