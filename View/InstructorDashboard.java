package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstructorDashboard {
    public static void show() {
        // Create the main frame
        JFrame frame = new JFrame("Instructor Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Create a CardLayout for switching panels
        JPanel mainPanel = new JPanel(new CardLayout());
        frame.add(mainPanel);

        // Main Menu Panel
        JPanel mainMenu = new JPanel();
        mainMenu.setLayout(new GridLayout(4, 1, 10, 10));

        JLabel title = new JLabel("Instructor Dashboard - Grade Students and Manage Courses", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JButton myCoursesButton = new JButton("My Courses");
        JButton gradingButton = new JButton("Grading");
        JButton logOutButton = new JButton("Log Out");

        mainMenu.add(title);
        mainMenu.add(myCoursesButton);
        mainMenu.add(gradingButton);
        mainMenu.add(logOutButton);

        mainPanel.add(mainMenu, "Main Menu");

        // My Courses Panel
        JPanel myCoursesPanel = new JPanel(new BorderLayout());
        JLabel myCoursesTitle = new JLabel("My Courses", SwingConstants.CENTER);
        myCoursesTitle.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel myCoursesButtons = new JPanel();
        JButton viewRequestsButton = new JButton("View Requests");
        JButton assignedStudentsButton = new JButton("Assigned Students");
        JButton backToMainFromCourses = new JButton("Back");

        myCoursesButtons.add(viewRequestsButton);
        myCoursesButtons.add(assignedStudentsButton);
        myCoursesButtons.add(backToMainFromCourses);

        myCoursesPanel.add(myCoursesTitle, BorderLayout.NORTH);
        myCoursesPanel.add(myCoursesButtons, BorderLayout.CENTER);

        mainPanel.add(myCoursesPanel, "My Courses");

        // Grading Panel
        JPanel gradingPanel = new JPanel(new BorderLayout());
        JLabel gradingTitle = new JLabel("Grading", SwingConstants.CENTER);
        gradingTitle.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel gradingContent = new JPanel();
        JComboBox<String> coursesDropdown = new JComboBox<>(new String[]{"Course 1", "Course 2", "Course 3"});
        JButton selectCourseButton = new JButton("Select");

        gradingContent.add(new JLabel("Select a Course:"));
        gradingContent.add(coursesDropdown);
        gradingContent.add(selectCourseButton);

        JPanel gradeTablePanel = new JPanel();
        String[] columnNames = {"Student", "MT1", "MT2", "Final"};
        Object[][] data = {{"Student 1", "", "", ""}, {"Student 2", "", "", ""}};
        JTable gradeTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(gradeTable);

        JButton saveGradesButton = new JButton("Save Grades");
        JButton backToMainFromGrading = new JButton("Back");

        gradingContent.add(scrollPane);
        gradeTablePanel.add(saveGradesButton);

        gradingPanel.add(gradingTitle, BorderLayout.NORTH);
        gradingPanel.add(gradingContent, BorderLayout.CENTER);
        gradingPanel.add(backToMainFromGrading, BorderLayout.SOUTH);

        mainPanel.add(gradingPanel, "Grading");

        // Logout Panel
        JPanel logoutPanel = new JPanel();
        JLabel logoutMessage = new JLabel("Logged Out. Please login again.", SwingConstants.CENTER);
        logoutMessage.setFont(new Font("Arial", Font.BOLD, 16));
        logoutPanel.add(logoutMessage);
        mainPanel.add(logoutPanel, "Logout");

        // Action Listeners for Buttons
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

        myCoursesButton.addActionListener(e -> cardLayout.show(mainPanel, "My Courses"));
        gradingButton.addActionListener(e -> cardLayout.show(mainPanel, "Grading"));
        logOutButton.addActionListener(e -> cardLayout.show(mainPanel, "Logout"));

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
