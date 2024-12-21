package com.example.sgs.View;

import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends JFrame {
    public void show() {
        // Create the main frame
        JFrame frame = new JFrame("Student Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Create a CardLayout for switching panels
        JPanel mainPanel = new JPanel(new CardLayout());
        frame.add(mainPanel);

        // Main Menu Panel
        JPanel mainMenu = new JPanel();
        mainMenu.setLayout(new GridLayout(4, 1, 10, 10));

        JLabel title = new JLabel("Student Dashboard - Manage Courses and View Information", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JButton courseRegistrationButton = new JButton("Course Registration");
        JButton myGradesButton = new JButton("My Grades");
        JButton viewTimetableButton = new JButton("View Timetable");
        JButton studentInfoButton = new JButton("Student Information");
        JButton logOutButton = new JButton("Log Out");

        mainMenu.add(title);
        mainMenu.add(courseRegistrationButton);
        mainMenu.add(myGradesButton);
        mainMenu.add(viewTimetableButton);
        mainMenu.add(studentInfoButton);
        mainMenu.add(logOutButton);

        mainPanel.add(mainMenu, "Main Menu");

        // Course Registration Panel
        JPanel courseRegistrationPanel = new JPanel(new BorderLayout());
        JLabel courseRegTitle = new JLabel("Course Registration", SwingConstants.CENTER);
        courseRegTitle.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel filterPanel = new JPanel();
        JTextField searchCourseNameField = new JTextField(10);
        searchCourseNameField.setToolTipText("Search by course name...");
        JTextField searchCourseCodeField = new JTextField(10);
        searchCourseCodeField.setToolTipText("Search by course code...");
        JTextField searchFacultyField = new JTextField(10);
        searchFacultyField.setToolTipText("Search by faculty...");
        JButton applyFilterButton = new JButton("Apply Filter");

        filterPanel.add(new JLabel("Course Name:"));
        filterPanel.add(searchCourseNameField);
        filterPanel.add(new JLabel("Course Code:"));
        filterPanel.add(searchCourseCodeField);
        filterPanel.add(new JLabel("Faculty:"));
        filterPanel.add(searchFacultyField);
        filterPanel.add(applyFilterButton);

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

        JPanel actionPanel = new JPanel();
        JButton addCourseButton = new JButton("Add Course");
        JButton requestQuotaButton = new JButton("Request Quota Increase");
        JButton backToMainFromCourseReg = new JButton("Back");

        actionPanel.add(addCourseButton);
        actionPanel.add(requestQuotaButton);
        actionPanel.add(backToMainFromCourseReg);

        // Enrolled Courses Panel
        JTable enrolledCoursesTable = new JTable(new Object[][] {
                {"CS101", "Introduction to Programming"},
                {"MATH101", "Calculus I"}
        }, new String[] {"Course Code", "Course Name"}) {
            public boolean isCellEditable(int row, int column) {
                return false; // Disable editing
            }
        };

        JScrollPane enrolledCoursesScrollPane = new JScrollPane(enrolledCoursesTable);
        JButton dropCourseButton = new JButton("Drop Course");

        JPanel enrolledCoursesPanel = new JPanel(new BorderLayout());
        enrolledCoursesPanel.add(new JLabel("Enrolled Courses", SwingConstants.CENTER), BorderLayout.NORTH);
        enrolledCoursesPanel.add(enrolledCoursesScrollPane, BorderLayout.CENTER);
        enrolledCoursesPanel.add(dropCourseButton, BorderLayout.SOUTH);

        courseRegistrationPanel.add(courseRegTitle, BorderLayout.NORTH);
        courseRegistrationPanel.add(filterPanel, BorderLayout.NORTH);
        courseRegistrationPanel.add(courseScrollPane, BorderLayout.CENTER);
        courseRegistrationPanel.add(actionPanel, BorderLayout.SOUTH);
        courseRegistrationPanel.add(enrolledCoursesPanel, BorderLayout.EAST);

        mainPanel.add(courseRegistrationPanel, "Course Registration");

        // My Grades Panel
        JPanel myGradesPanel = new JPanel(new BorderLayout());
        JLabel myGradesTitle = new JLabel("My Grades", SwingConstants.CENTER);
        myGradesTitle.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel cumulativeGPA = new JLabel("Cumulative GPA: 3.75", SwingConstants.CENTER);
        cumulativeGPA.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel gradesOptions = new JPanel();
        JButton selectYearTermButton = new JButton("Select Year & Term");
        JButton backToMainFromGrades = new JButton("Back");

        gradesOptions.add(selectYearTermButton);
        gradesOptions.add(backToMainFromGrades);

        myGradesPanel.add(myGradesTitle, BorderLayout.NORTH);
        myGradesPanel.add(cumulativeGPA, BorderLayout.CENTER);
        myGradesPanel.add(gradesOptions, BorderLayout.SOUTH);

        mainPanel.add(myGradesPanel, "My Grades");

        // View Timetable Panel
        JPanel timetablePanel = new JPanel(new BorderLayout());
        JLabel timetableTitle = new JLabel("View Timetable", SwingConstants.CENTER);
        timetableTitle.setFont(new Font("Arial", Font.BOLD, 16));

        JButton backToMainFromTimetable = new JButton("Back");

        timetablePanel.add(timetableTitle, BorderLayout.NORTH);
        timetablePanel.add(new JLabel("Timetable Placeholder", SwingConstants.CENTER), BorderLayout.CENTER);
        timetablePanel.add(backToMainFromTimetable, BorderLayout.SOUTH);

        mainPanel.add(timetablePanel, "View Timetable");

        // Student Information Panel
        JPanel studentInfoPanel = new JPanel(new BorderLayout());
        JLabel studentInfoTitle = new JLabel("Student Information", SwingConstants.CENTER);
        studentInfoTitle.setFont(new Font("Arial", Font.BOLD, 16));

        JTextArea studentInfoArea = new JTextArea(10, 30);
        studentInfoArea.setEditable(false);
        studentInfoArea.setText("Name: John Doe\nDepartment: Computer Science\nCompleted Credits: 90\nCumulative GPA: 3.75");

        JButton backToMainFromStudentInfo = new JButton("Back");

        studentInfoPanel.add(studentInfoTitle, BorderLayout.NORTH);
        studentInfoPanel.add(new JScrollPane(studentInfoArea), BorderLayout.CENTER);
        studentInfoPanel.add(backToMainFromStudentInfo, BorderLayout.SOUTH);

        mainPanel.add(studentInfoPanel, "Student Information");

        // Action Listeners for Buttons
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

        courseRegistrationButton.addActionListener(e -> cardLayout.show(mainPanel, "Course Registration"));
        myGradesButton.addActionListener(e -> cardLayout.show(mainPanel, "My Grades"));
        viewTimetableButton.addActionListener(e -> cardLayout.show(mainPanel, "View Timetable"));
        studentInfoButton.addActionListener(e -> cardLayout.show(mainPanel, "Student Information"));

        logOutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                frame.dispose(); // Close the current StudentDashboard frame
                LoginPage.main(null); // Redirect back to the Login Page
            }
        });

        backToMainFromCourseReg.addActionListener(e -> cardLayout.show(mainPanel, "Main Menu"));
        backToMainFromGrades.addActionListener(e -> cardLayout.show(mainPanel, "Main Menu"));
        backToMainFromTimetable.addActionListener(e -> cardLayout.show(mainPanel, "Main Menu"));
        backToMainFromStudentInfo.addActionListener(e -> cardLayout.show(mainPanel, "Main Menu"));

        addCourseButton.addActionListener(e -> {
            int selectedRow = coursesTable.getSelectedRow();
            if (selectedRow != -1) {
                String courseName = (String) coursesTable.getValueAt(selectedRow, 1);
                JOptionPane.showMessageDialog(frame, "Course added: " + courseName);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a course to add.");
            }
        });

        requestQuotaButton.addActionListener(e -> {
            int selectedRow = coursesTable.getSelectedRow();
            if (selectedRow != -1) {
                int enrolled = (int) coursesTable.getValueAt(selectedRow, 3);
                int quota = (int) coursesTable.getValueAt(selectedRow, 4);
                if (enrolled < quota) {
                    JOptionPane.showMessageDialog(frame, "Quota request not allowed: Course quota is not full.");
                } else {
                    String courseName = (String) coursesTable.getValueAt(selectedRow, 1);
                    String message = JOptionPane.showInputDialog(frame, "Enter your message for quota increase request:");
                    if (message != null && !message.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Quota increase requested for: " + courseName + "\nMessage: " + message);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Request canceled. Message cannot be empty.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a course to request quota increase.");
            }
        });

        dropCourseButton.addActionListener(e -> {
            int selectedRow = enrolledCoursesTable.getSelectedRow();
            if (selectedRow != -1) {
                String courseName = (String) enrolledCoursesTable.getValueAt(selectedRow, 1);
                JOptionPane.showMessageDialog(frame, "Course dropped: " + courseName);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a course to drop.");
            }
        });

        applyFilterButton.addActionListener(e -> {
            String nameFilter = searchCourseNameField.getText().toLowerCase();
            String codeFilter = searchCourseCodeField.getText().toLowerCase();
            String facultyFilter = searchFacultyField.getText().toLowerCase();

            for (int i = 0; i < coursesTable.getRowCount(); i++) {
                boolean matchesName = nameFilter.isEmpty() || ((String) coursesTable.getValueAt(i, 1)).toLowerCase().contains(nameFilter);
                boolean matchesCode = codeFilter.isEmpty() || ((String) coursesTable.getValueAt(i, 0)).toLowerCase().contains(codeFilter);
                boolean matchesFaculty = facultyFilter.isEmpty() || ((String) coursesTable.getValueAt(i, 2)).toLowerCase().contains(facultyFilter);

                if (matchesName && matchesCode && matchesFaculty) {
                    coursesTable.setRowSelectionInterval(i, i);
                }
            }
        });

        // Show the frame
        frame.setVisible(true);
    }
}
