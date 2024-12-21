package com.example.sgs.Service;

import com.example.sgs.Entities.User;
import com.example.sgs.Repository.UserRepository;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class AuthenticationService {

    private UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Register a new user with hashed password
     */
    public boolean register(String email, String password, User.UserType userType, String firstName, String lastName) {
        // Check if email already exists
        if (userRepository.findByEmail(email).isPresent()) {
            System.out.println("Error: Email already exists for: " + email);
            return false;
        }

        // Hash the password
        String passwordHash = hashPassword(password);
        System.out.println("Registering user. Hashed password: " + passwordHash); // Debug log

        // Create a new user object
        User newUser = new User(0, email, passwordHash, userType, firstName, lastName);

        // Save the user to the database
        boolean success = userRepository.save(newUser);
        if (success) {
            System.out.println("User registered successfully for email: " + email);
        } else {
            System.out.println("Error saving user to the database for email: " + email);
        }
        return success;
    }

    /**
     * Hashes the plain text password using SHA-256
     */
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * Login a user and validate the password
     */
    public User login(String email, String password) {
        // Find user by email
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Verify the password
            String hashedPassword = hashPassword(password);
            System.out.println("Logging in. Hashed input password: " + hashedPassword);
            System.out.println("Database password hash: " + user.getPasswordHash());

            if (verifyPassword(password, user.getPasswordHash())) {
                System.out.println("Authentication successful for email: " + email);
                return user; // Authentication successful
            } else {
                System.out.println("Authentication failed: Passwords do not match for email: " + email);
            }
        } else {
            System.out.println("Authentication failed: User not found for email: " + email);
        }

        return null; // Authentication failed
    }

    /**
     * Verifies if the provided plain text password matches the hashed password in the database
     */
    private boolean verifyPassword(String plainPassword, String hashedPassword) {
        // Compare the hashed input password with the stored hashed password
        return hashPassword(plainPassword).equals(hashedPassword);
    }
}
