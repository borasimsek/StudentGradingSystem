package com.example.sgs.View;

import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends JFrame {
    public void show() {
        // Create the main frame
        JFrame frame = new JFrame("Student Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the frame to full screen and remove window decorations
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);

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

        JLabel title = new JLabel("Student Dashboard - Manage Courses and View Information", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32)); // Adjusted for full screen
        gbc.gridy = 0;
        mainMenu.add(title, gbc);

        JButton courseRegistrationButton = new JButton("Course Registration");
        courseRegistrationButton.setPreferredSize(new Dimension(300, 50));
        gbc.gridy = 1;
        mainMenu.add(courseRegistrationButton, gbc);

        JButton myGradesButton = new JButton("My Grades");
        myGradesButton.setPreferredSize(new Dimension(300, 50));
        gbc.gridy = 2;
        mainMenu.add(myGradesButton, gbc);

        JButton viewTimetableButton = new JButton("View Timetable");
        viewTimetableButton.setPreferredSize(new Dimension(300, 50));
        gbc.gridy = 3;
        mainMenu.add(viewTimetableButton, gbc);

        JButton studentInfoButton = new JButton("Student Information");
        studentInfoButton.setPreferredSize(new Dimension(300, 50));
        gbc.gridy = 4;
        mainMenu.add(studentInfoButton, gbc);

        JButton logOutButton = new JButton("Log Out");
        logOutButton.setPreferredSize(new Dimension(300, 50));
        gbc.gridy = 5;
        mainMenu.add(logOutButton, gbc);

        mainPanel.add(mainMenu, "Main Menu");

        // Course Registration Panel
        JPanel courseRegistrationPanel = new JPanel(new BorderLayout());
        JLabel courseRegTitle = new JLabel("Course Registration", SwingConstants.CENTER);
        courseRegTitle.setFont(new Font("Arial", Font.BOLD, 28));
        courseRegistrationPanel.add(courseRegTitle, BorderLayout.NORTH);

        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        filterPanel.setPreferredSize(new Dimension(1000, 100));
        JTextField searchCourseNameField = new JTextField(15);
        searchCourseNameField.setToolTipText("Search by course name...");
        JTextField searchCourseCodeField = new JTextField(15);
        searchCourseCodeField.setToolTipText("Search by course code...");
        JTextField searchFacultyField = new JTextField(15);
        searchFacultyField.setToolTipText("Search by faculty...");
        JButton applyFilterButton = new JButton("Apply Filter");

        filterPanel.add(new JLabel("Course Name:"));
        filterPanel.add(searchCourseNameField);
        filterPanel.add(new JLabel("Course Code:"));
        filterPanel.add(searchCourseCodeField);
        filterPanel.add(new JLabel("Faculty:"));
        filterPanel.add(searchFacultyField);
        filterPanel.add(applyFilterButton);

        courseRegistrationPanel.add(filterPanel, BorderLayout.NORTH);

        JTable coursesTable = new JTable(new Object[][] {
                {"CS101", "Introduction to Programming", "Faculty of Computer Science", 55, 60},
                {"CS102", "Data Structures", "Faculty of Computer Science", 40, 40},
                {"MATH101", "Calculus I", "Faculty of Mathematics", 30, 30}
        }, new String[] {"Course Code", "Course Name", "Faculty", "Enrolled", "Quota"}) {
            public boolean isCellEditable(int row, int column) {
                return false; // Disable editing
            }
        };

        JScrollPane courseScrollPane = new JScrollPane(coursesTable);
        coursesTable.setRowHeight(30); // Adjust for full screen
        coursesTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        courseScrollPane.setPreferredSize(new Dimension(800, 400));

        courseRegistrationPanel.add(courseScrollPane, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JButton addCourseButton = new JButton("Add Course");
        JButton requestQuotaButton = new JButton("Request Quota Increase");
        JButton backToMainFromCourseReg = new JButton("Back");

        addCourseButton.setPreferredSize(new Dimension(200, 50));
        requestQuotaButton.setPreferredSize(new Dimension(200, 50));
        backToMainFromCourseReg.setPreferredSize(new Dimension(200, 50));

        actionPanel.add(addCourseButton);
        actionPanel.add(requestQuotaButton);
        actionPanel.add(backToMainFromCourseReg);

        courseRegistrationPanel.add(actionPanel, BorderLayout.SOUTH);
        mainPanel.add(courseRegistrationPanel, "Course Registration");

        // Action Listeners for Navigation
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        courseRegistrationButton.addActionListener(e -> cardLayout.show(mainPanel, "Course Registration"));
        logOutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                frame.dispose();
                LoginPage.main(null); // Redirect back to Login Page
            }
        });
        backToMainFromCourseReg.addActionListener(e -> cardLayout.show(mainPanel, "Main Menu"));

        // Show the frame
        frame.setVisible(true);
    }
}
