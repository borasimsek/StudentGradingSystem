package com.example.sgs.View;

import com.example.sgs.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class LoginPage {

    public static void main(String[] args) {
        // Attempt to establish a connection to the database
        try {
            Connection connection = DatabaseConnection.getConnection(); // Connect to the database

            if (connection != null) {
                System.out.println("Database connected successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Error connecting to the database.", "Database Connection Error", JOptionPane.ERROR_MESSAGE);
            }

            // Create the main frame for login
            JFrame frame = new JFrame("Student Grading System - Login");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen

            // Background Panel with an image
            Image backgroundImage = new ImageIcon(LoginPage.class.getClassLoader().getResource("arkaplan.jpg")).getImage();
            JPanel backgroundPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            };
            backgroundPanel.setLayout(new GridBagLayout()); // Set GridBagLayout for the background

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.NONE;

            // Title Label
            JLabel headerLabel = new JLabel("Student Grading System (SGS)", SwingConstants.CENTER);
            headerLabel.setFont(new Font("Arial", Font.BOLD, 48));
            headerLabel.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.NORTH;
            gbc.weighty = 0.1;
            backgroundPanel.add(headerLabel, gbc);

            // Form Background Panel
            JPanel formBackgroundPanel = new JPanel();
            formBackgroundPanel.setBackground(new Color(0, 51, 102, 200));
            formBackgroundPanel.setLayout(new GridBagLayout());
            formBackgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            GridBagConstraints formGbc = new GridBagConstraints();
            formGbc.insets = new Insets(10, 10, 10, 10);
            formGbc.fill = GridBagConstraints.HORIZONTAL;

            // Email Label
            JLabel emailLabel = new JLabel("Email:");
            emailLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            emailLabel.setForeground(Color.WHITE);
            formGbc.gridx = 0;
            formGbc.gridy = 0;
            formBackgroundPanel.add(emailLabel, formGbc);

            // Email Text Field
            JTextField emailField = new JTextField(20);
            emailField.setFont(new Font("Arial", Font.PLAIN, 18));
            formGbc.gridx = 1;
            formGbc.gridy = 0;
            formBackgroundPanel.add(emailField, formGbc);

            // Password Label
            JLabel passwordLabel = new JLabel("Password:");
            passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            passwordLabel.setForeground(Color.WHITE);
            formGbc.gridx = 0;
            formGbc.gridy = 1;
            formBackgroundPanel.add(passwordLabel, formGbc);

            // Password Field
            JPasswordField passwordField = new JPasswordField(20);
            passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
            formGbc.gridx = 1;
            formGbc.gridy = 1;
            formBackgroundPanel.add(passwordField, formGbc);

            // Login Button
            JButton loginButton = new JButton("Login");
            loginButton.setFont(new Font("Arial", Font.BOLD, 18));
            formGbc.gridx = 0;
            formGbc.gridy = 2;
            formGbc.gridwidth = 2;
            formGbc.anchor = GridBagConstraints.CENTER;
            formBackgroundPanel.add(loginButton, formGbc);

            // Register Button (New Button)
            JButton registerButton = new JButton("Register");
            registerButton.setFont(new Font("Arial", Font.BOLD, 18));
            formGbc.gridx = 0;
            formGbc.gridy = 3;
            formGbc.gridwidth = 2;
            formGbc.anchor = GridBagConstraints.CENTER;
            formBackgroundPanel.add(registerButton, formGbc);

            // Cancel Button
            JButton cancelButton = new JButton("Cancel");
            cancelButton.setFont(new Font("Arial", Font.BOLD, 18));
            formGbc.gridx = 1;
            formGbc.gridy = 4;
            formBackgroundPanel.add(cancelButton, formGbc);

            // Footer Label
            JLabel footerLabel = new JLabel("Welcome to the SGS system. Please login to continue.");
            footerLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            footerLabel.setForeground(Color.WHITE);
            formGbc.gridx = 0;
            formGbc.gridy = 5;
            formGbc.gridwidth = 2;
            formBackgroundPanel.add(footerLabel, formGbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.weighty = 1;
            backgroundPanel.add(formBackgroundPanel, gbc);

            frame.add(backgroundPanel);
            frame.setVisible(true); // Make the login frame visible

            // Register Button ActionListener
            registerButton.addActionListener(e -> {
                // Open Registration Page when "Register" is clicked
                new RegisterPage(); // This will open the RegisterPage in the same window
                frame.dispose(); // Close the login page when register button is clicked
            });

            // Cancel Button ActionListener
            cancelButton.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    frame.dispose(); // Close the login page when cancelled
                }
            });

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error connecting to the database: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
