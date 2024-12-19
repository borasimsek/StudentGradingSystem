
package com.example.sgs;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import com.example.sgs.model.Admin;
//import com.example.sgs.model.Course;
//import com.example.sgs.model.Student;
//

import java.sql.Connection;

///**
// * Main class to test the Student and User classes.
// */
//public class Main {
//    public static void main(String[] args) {
//        // Create a list of enrolled courses for the student
//        List<String> initialCourses = new ArrayList<>();
//        initialCourses.add("MATH101");
//        initialCourses.add("CS320XD");
//
//        // Create a Student object
//        Student student = new Student(
//                "1",                          // User ID
//                "bora simsek",                   // Name
//                "bora.simsek@ozu.edu.tr",        // Email
//                "password123",                // Password
//                "S12345",                     // Student ID
//                "ME",           // Major
//                4,                            // Year
//                initialCourses                // Initial courses
//        );
//
//        // Display the student's details
//        System.out.println("Initial Student Details:");
//        System.out.println(student);
//
//        // Test adding a course
//        System.out.println("\nAdding a new course: PHYS103");
//        student.addCourse("PHYS103");
//        System.out.println("Updated Student Details:");
//        System.out.println(student);
//
//        // Test removing a course
//        System.out.println("\nRemoving a course: MATH101");
//        student.removeCourse("MATH101");
//        System.out.println("Updated Student Details:");
//        System.out.println(student);
//
//        // Test role retrieval
//        System.out.println("\nStudent Role:");
//        System.out.println(student.getRole());
//
//        // Create Admin object
//        Admin admin = new Admin(
//            "A001",
//            "foo adamı",
//            "ben.foo@example.com",
//            "securePassword",
//            "ADMIN123",
//            Arrays.asList("CREATE", "UPDATE", "DELETE")
//        );
//
//        // Use Admin methods
//        System.out.println("Admin ID: " + admin.getAdminId());
//        admin.setAdminId("ADMIN456");
//        System.out.println("Updated Admin ID: " + admin.getAdminId());
//
//        System.out.println("Role: " + admin.getRole());
//        System.out.println("Permissions: " + admin.getPermissions());
//
//        // Create Course object
//        Course course = new Course(
//            "CSE101",
//            "Introduction to Computer Science",
//            4,
//            Arrays.asList("CSE100")
//        );
//
//        // Use Course methods
//        System.out.println("Course ID: " + course.getCourseId());
//        course.setCourseId("CSE102");
//        System.out.println("Updated Course ID: " + course.getCourseId());
//
//        System.out.println("Course Name: " + course.getCourseName());
//        // Assuming getters for creditHours and prerequisites are implemented
//        // System.out.println("Credit Hours: " + course.getCreditHours());
//        // System.out.println("Prerequisites: " + course.getPrerequisites());
//    }
//}

public class Main {
    public static void main(String[] args) {
        // Get connection to the database
        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.getConnection();

        // Example of using the connection
        if (connection != null) {
            System.out.println("Connection established successfully!");
            // You can now use the connection for queries, etc.
        } else {
            System.out.println("Failed to establish connection.");
        }

        // Close the connection when done
        DatabaseConnection.closeConnection();
    }
}


