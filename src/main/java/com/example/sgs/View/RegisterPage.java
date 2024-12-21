package com.example.sgs.View;

import com.example.sgs.DatabaseConnection;
import com.example.sgs.Service.AuthenticationService;
import com.example.sgs.Entities.User;
import com.example.sgs.Repository.UserRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RegisterPage {

    public RegisterPage() {
        // Create the register frame
        JFrame registerFrame = new JFrame("Student Grading System - Register");
        registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registerFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen

        // Background Panel
        Image backgroundImage = new ImageIcon(LoginPage.class.getClassLoader().getResource("arkaplan.jpg")).getImage();
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;

        // Title Label
        JLabel headerLabel = new JLabel("Register", SwingConstants.CENTER);
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

        // First Name Label
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        firstNameLabel.setForeground(Color.WHITE);
        formGbc.gridx = 0;
        formGbc.gridy = 0;
        formBackgroundPanel.add(firstNameLabel, formGbc);

        // First Name Text Field
        JTextField firstNameField = new JTextField(20);
        firstNameField.setFont(new Font("Arial", Font.PLAIN, 18));
        formGbc.gridx = 1;
        formGbc.gridy = 0;
        formBackgroundPanel.add(firstNameField, formGbc);

        // Last Name Label
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        lastNameLabel.setForeground(Color.WHITE);
        formGbc.gridx = 0;
        formGbc.gridy = 1;
        formBackgroundPanel.add(lastNameLabel, formGbc);

        // Last Name Text Field
        JTextField lastNameField = new JTextField(20);
        lastNameField.setFont(new Font("Arial", Font.PLAIN, 18));
        formGbc.gridx = 1;
        formGbc.gridy = 1;
        formBackgroundPanel.add(lastNameField, formGbc);

        // Email Label
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        emailLabel.setForeground(Color.WHITE);
        formGbc.gridx = 0;
        formGbc.gridy = 2;
        formBackgroundPanel.add(emailLabel, formGbc);

        // Email Text Field
        JTextField emailField = new JTextField(20);
        emailField.setFont(new Font("Arial", Font.PLAIN, 18));
        formGbc.gridx = 1;
        formGbc.gridy = 2;
        formBackgroundPanel.add(emailField, formGbc);

        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordLabel.setForeground(Color.WHITE);
        formGbc.gridx = 0;
        formGbc.gridy = 3;
        formBackgroundPanel.add(passwordLabel, formGbc);

        // Password Field
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        formGbc.gridx = 1;
        formGbc.gridy = 3;
        formBackgroundPanel.add(passwordField, formGbc);

        // User Type Label
        JLabel userTypeLabel = new JLabel("User Type:");
        userTypeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        userTypeLabel.setForeground(Color.WHITE);
        formGbc.gridx = 0;
        formGbc.gridy = 4;
        formBackgroundPanel.add(userTypeLabel, formGbc);

        // User Type Combo Box (Student, Instructor, Admin)
        String[] userTypes = {"STUDENT", "INSTRUCTOR", "ADMIN"};
        JComboBox<String> userTypeComboBox = new JComboBox<>(userTypes);
        userTypeComboBox.setFont(new Font("Arial", Font.PLAIN, 18));
        formGbc.gridx = 1;
        formGbc.gridy = 4;
        formBackgroundPanel.add(userTypeComboBox, formGbc);

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 18));
        formGbc.gridx = 0;
        formGbc.gridy = 5;
        formGbc.gridwidth = 2;
        formGbc.anchor = GridBagConstraints.CENTER;
        formBackgroundPanel.add(registerButton, formGbc);

        // Cancel Button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 18));
        formGbc.gridx = 1;
        formGbc.gridy = 6;
        formBackgroundPanel.add(cancelButton, formGbc);

        // Footer Label
        JLabel footerLabel = new JLabel("Please fill in the details to register.");
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        footerLabel.setForeground(Color.WHITE);
        formGbc.gridx = 0;
        formGbc.gridy = 7;
        formGbc.gridwidth = 2;
        formBackgroundPanel.add(footerLabel, formGbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 1;
        backgroundPanel.add(formBackgroundPanel, gbc);

        registerFrame.add(backgroundPanel);

        // Set up database connection and AuthenticationService
        try {
            Connection connection = DatabaseConnection.getConnection();
            UserRepository userRepository = new UserRepository(connection);
            AuthenticationService authenticationService = new AuthenticationService(userRepository);

            // Action listener for the register button
            registerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String firstName = firstNameField.getText();
                    String lastName = lastNameField.getText();
                    String email = emailField.getText();
                    String password = new String(passwordField.getPassword());
                    String userTypeStr = (String) userTypeComboBox.getSelectedItem();
                    User.UserType userType = User.UserType.valueOf(userTypeStr);

                    // Simple validation
                    if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(registerFrame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Register the user via AuthenticationService
                    boolean registrationSuccessful = authenticationService.register(email, password, userType, firstName, lastName);

                    if (registrationSuccessful) {
                        JOptionPane.showMessageDialog(registerFrame, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        registerFrame.dispose();  // Close the registration window
                    } else {
                        JOptionPane.showMessageDialog(registerFrame, "Registration failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            // Action listener for the cancel button
            cancelButton.addActionListener(e -> registerFrame.dispose());  // Close the registration window

            registerFrame.setVisible(true); // Make the registration frame visible
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(registerFrame, "Error connecting to the database", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
