package com.example.sgs.Entities;

public class User {
    private int userId;
    private String email;
    private String passwordHash;
    private UserType userType;
    private String firstName;
    private String lastName;

    // Enum for UserType
    public enum UserType {
        STUDENT,
        INSTRUCTOR,
        ADMIN;

        public static UserType fromString(String value) {
            return UserType.valueOf(value.toUpperCase());

        }    }


    // Constructor
    public User(int userId, String email, String passwordHash, UserType userType, String firstName, String lastName) {
        this.userId = userId;
        this.email = email;
        this.passwordHash = passwordHash;
        this.userType = userType;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Override toString Method
    @Override
    public String toString() {
        return "ID: " + userId + " | Email: " + email;
    }
}
