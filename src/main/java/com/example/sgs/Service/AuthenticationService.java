package com.example.sgs.Service;

import com.example.sgs.Entities.User;
import com.example.sgs.Repository.UserRepository;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthenticationService {

    private UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean register(String email, String password, User.UserType userType, String firstName, String lastName) {
        // Check if email already exists
        if (userRepository.findByEmail(email).isPresent()) {  // Using isPresent() to check if the Optional contains a value
            System.out.println("Error: Email already exists.");
            return false;
        }

        // Hash the password
        String passwordHash = hashPassword(password);

        // Create a new user object
        User newUser = new User(0, email, passwordHash, userType, firstName, lastName);

        // Save the user to the database
        return userRepository.save(newUser);
    }

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
}
