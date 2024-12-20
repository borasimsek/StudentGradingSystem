package com.example.sgs;

import com.example.sgs.Entities.User;
import com.example.sgs.Repository.UserRepository;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {
        // Establish the database connection
        Connection connection = DatabaseConnection.getConnection();

        // Pass the connection to the repository
        UserRepository userRepository = new UserRepository(connection);

        // Example: Save a new user
        User newUser = new User(0, "selam@example.com", "hashedPassword", User.UserType.STUDENT, "Yigit", "Mert");
        boolean isSaved = userRepository.save(newUser);
        System.out.println("User saved: " + isSaved);

        // Example: Find a user by email
        String emailToSearch = "selam@example.com";
        userRepository.findByEmail(emailToSearch).ifPresentOrElse(
                user -> System.out.println("User found: " + user.getFirstName() + " " + user.getLastName()),
                () -> System.out.println("User not found.")
        );

        // Close the connection when done
        DatabaseConnection.closeConnection();
    }
}
