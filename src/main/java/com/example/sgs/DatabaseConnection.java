package com.example.sgs;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Database URL, username, and password
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/sgs";
    private static final String USER = "root"; //
    private static final String PASSWORD = "root"; //
    private static Connection connection = null;
    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Load and register MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish the connection
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to establish a database connection.");
            }
        }
        return connection;
    }

    // Close the connection (good practice to close it when done)
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
