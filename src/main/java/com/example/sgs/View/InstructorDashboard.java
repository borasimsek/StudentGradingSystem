package com.example.sgs.View;

import javax.swing.*;
import java.awt.*;

public class InstructorDashboard extends JFrame {
    public void show() {
        // Create the main frame
        JFrame frame = new JFrame("Instructor Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the frame to full screen
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false); // Pencere çerçevesi için

        // Create a CardLayout for switching panels
        JPanel mainPanel = new JPanel(new CardLayout());
        frame.add(mainPanel);

        // Main Menu Panel
        JPanel mainMenu = new JPanel();
        mainMenu.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel title = new JLabel("Instructor Dashboard - Grade Students and Manage Courses", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridy = 0;
        mainMenu.add(title, gbc);

        JButton myCoursesButton = new JButton("My Courses");
        myCoursesButton.setPreferredSize(new Dimension(200, 50));
        gbc.gridy = 1;
        mainMenu.add(myCoursesButton, gbc);

        JButton gradingButton = new JButton("Grading");
        gradingButton.setPreferredSize(new Dimension(200, 50));
        gbc.gridy = 2;
        mainMenu.add(gradingButton, gbc);

        JButton logOutButton = new JButton("Log Out");
        logOutButton.setPreferredSize(new Dimension(200, 50));
        gbc.gridy = 3;
        mainMenu.add(logOutButton, gbc);

        mainPanel.add(mainMenu, "Main Menu");

        // My Courses Panel
        JPanel myCoursesPanel = new JPanel(new BorderLayout());
        JLabel myCoursesTitle = new JLabel("My Courses", SwingConstants.CENTER);
        myCoursesTitle.setFont(new Font("Arial", Font.BOLD, 20));
        myCoursesPanel.add(myCoursesTitle, BorderLayout.NORTH);

        JPanel myCoursesButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JButton viewRequestsButton = new JButton("View Requests");
        JButton assignedStudentsButton = new JButton("Assigned Students");
        JButton backToMainFromCourses = new JButton("Back");

        myCoursesButtons.add(viewRequestsButton);
        myCoursesButtons.add(assignedStudentsButton);
        myCoursesButtons.add(backToMainFromCourses);
        myCoursesPanel.add(myCoursesButtons, BorderLayout.CENTER);

        mainPanel.add(myCoursesPanel, "My Courses");

        // Grading Panel
        JPanel gradingPanel = new JPanel(new BorderLayout());
        JLabel gradingTitle = new JLabel("Grading", SwingConstants.CENTER);
        gradingTitle.setFont(new Font("Arial", Font.BOLD, 20));
        gradingPanel.add(gradingTitle, BorderLayout.NORTH);

        JPanel gradingContent = new JPanel(new GridLayout(2, 1, 10, 10));
        gradingContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JComboBox<String> coursesDropdown = new JComboBox<>(new String[]{"Course 1", "Course 2", "Course 3"});
        JPanel dropdownPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dropdownPanel.add(new JLabel("Select a Course:"));
        dropdownPanel.add(coursesDropdown);
        JButton selectCourseButton = new JButton("Select");
        dropdownPanel.add(selectCourseButton);
        gradingContent.add(dropdownPanel);

        String[] columnNames = {"Student", "MT1", "MT2", "Final"};
        Object[][] data = {{"Student 1", "", "", ""}, {"Student 2", "", "", ""}};
        JTable gradeTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(gradeTable);
        gradeTable.setFillsViewportHeight(true);
        gradingContent.add(scrollPane);

        JPanel gradeTablePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JButton saveGradesButton = new JButton("Save Grades");
        JButton backToMainFromGrading = new JButton("Back");

        gradeTablePanel.add(saveGradesButton);
        gradeTablePanel.add(backToMainFromGrading);

        gradingPanel.add(gradingContent, BorderLayout.CENTER);
        gradingPanel.add(gradeTablePanel, BorderLayout.SOUTH);

        mainPanel.add(gradingPanel, "Grading");

        // Action Listeners for Buttons
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

        myCoursesButton.addActionListener(e -> cardLayout.show(mainPanel, "My Courses"));
        gradingButton.addActionListener(e -> cardLayout.show(mainPanel, "Grading"));
        logOutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                frame.dispose(); // Close the current InstructorDashboard frame
                LoginPage.main(null); // Redirect back to the Login Page
            }
        });

        viewRequestsButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "View Requests Placeholder"));
        assignedStudentsButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Assigned Students Placeholder"));
        backToMainFromCourses.addActionListener(e -> cardLayout.show(mainPanel, "Main Menu"));

        selectCourseButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Selected course: " + coursesDropdown.getSelectedItem()));
        saveGradesButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Grades Saved!"));
        backToMainFromGrading.addActionListener(e -> cardLayout.show(mainPanel, "Main Menu"));

        // Show the frame
        frame.setVisible(true);
    }
}
