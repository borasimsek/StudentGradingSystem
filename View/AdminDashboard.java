package View;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class AdminDashboard {
    private static int courseCodeNumber = 100; // Başlangıç 3 rakamlı sayı
    private static final Set<String> usedCourseCodes = new HashSet<>(); // Kullanılmış kısaltmaları takip eder

    public static void show() {
        JFrame frame = new JFrame("Admin Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        // Ana Panel
        JPanel mainPanel = new JPanel(new CardLayout());
        frame.add(mainPanel);

        // ==== Ana Menü Paneli ====
        JPanel mainMenu = new JPanel(new GridLayout(3, 1, 10, 10));
        JLabel title = new JLabel("Admin Dashboard - Manage Courses and Users", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JButton manageCoursesButton = new JButton("Manage Courses");
        JButton logOutButton = new JButton("Log Out");

        mainMenu.add(title);
        mainMenu.add(manageCoursesButton);
        mainMenu.add(logOutButton);
        mainPanel.add(mainMenu, "Main Menu");

        // ==== Manage Courses Paneli ====
        JPanel manageCoursesPanel = new JPanel(new BorderLayout());
        JLabel manageCoursesTitle = new JLabel("Create a New Course", SwingConstants.CENTER);
        manageCoursesTitle.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel courseForm = new JPanel(new GridLayout(7, 2, 10, 10));

        // Course Name
        courseForm.add(new JLabel("Course Name:"));
        JTextField courseNameField = new JTextField();
        courseForm.add(courseNameField);

        // Course Faculty
        courseForm.add(new JLabel("Course Faculty:"));
        JComboBox<String> facultyDropdown = new JComboBox<>(new String[]{
                "Engineering", "Business", "Law", "Architecture", "Literature", "Foreign Languages", "Others"
        });
        courseForm.add(facultyDropdown);

        // Course Code
        courseForm.add(new JLabel("Course Code (3 Letters):"));
        JTextField courseCodeField = new JTextField();
        courseForm.add(courseCodeField);

        // Course Number (Auto-generated)
        JLabel generatedCourseNumber = new JLabel("Generated Code: ");
        courseForm.add(new JLabel("Course Number:"));
        courseForm.add(generatedCourseNumber);

        // Course Start Time
        courseForm.add(new JLabel("Course Start Time:"));
        JComboBox<String> startTimeDropdown = new JComboBox<>(generateTimeOptions(8, 40, 17, 40));
        courseForm.add(startTimeDropdown);

        // Course End Time
        courseForm.add(new JLabel("Course End Time:"));
        JComboBox<String> endTimeDropdown = new JComboBox<>(generateTimeOptions(9, 30, 19, 30));
        courseForm.add(endTimeDropdown);

        JButton saveCourseButton = new JButton("Save Course");
        JButton backButton = new JButton("Back to Main Menu");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveCourseButton);
        buttonPanel.add(backButton);

        manageCoursesPanel.add(manageCoursesTitle, BorderLayout.NORTH);
        manageCoursesPanel.add(courseForm, BorderLayout.CENTER);
        manageCoursesPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(manageCoursesPanel, "Manage Courses");

        // ==== Action Listeners ====
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

        // Menüden Manage Courses'a geçiş
        manageCoursesButton.addActionListener(e -> cardLayout.show(mainPanel, "Manage Courses"));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Main Menu"));

        // Course Code kontrolü
        courseCodeField.addActionListener(e -> {
            String codePrefix = courseCodeField.getText().toUpperCase();
            if (codePrefix.length() == 3 && !usedCourseCodes.contains(codePrefix)) {
                generatedCourseNumber.setText("Generated Code: " + codePrefix + courseCodeNumber);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid code or already used. Enter 3 unique letters.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Save Course Button
        saveCourseButton.addActionListener(e -> {
            String courseName = courseNameField.getText();
            String faculty = (String) facultyDropdown.getSelectedItem();
            String coursePrefix = courseCodeField.getText().toUpperCase();
            String courseNumberCode = generatedCourseNumber.getText();
            String startTime = (String) startTimeDropdown.getSelectedItem();
            String endTime = (String) endTimeDropdown.getSelectedItem();

            if (coursePrefix.length() != 3 || usedCourseCodes.contains(coursePrefix)) {
                JOptionPane.showMessageDialog(frame, "Invalid course code! It must be unique and 3 letters.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isTimeValid(startTime, endTime)) {
                JOptionPane.showMessageDialog(frame, "End time must be after start time!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Course Saved
            JOptionPane.showMessageDialog(frame, "Course Created Successfully:\n" +
                    "Name: " + courseName + "\nFaculty: " + faculty + "\nCode: " + courseNumberCode + "\nStart: " + startTime + "\nEnd: " + endTime);
            usedCourseCodes.add(coursePrefix); // Add used prefix
            courseCodeNumber++; // Increment number
        });

        // Logout Action
        logOutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                frame.dispose();
                LoginPage.main(null);
            }
        });

        frame.setVisible(true);
    }

    private static String[] generateTimeOptions(int startHour, int startMinute, int endHour, int endMinute) {
        java.util.List<String> times = new java.util.ArrayList<>();
        for (int h = startHour; h <= endHour; h++) {
            for (int m = startMinute; m < 60; m += 60) {
                if (h == endHour && m > endMinute) break;
                times.add(String.format("%02d:%02d", h, m));
            }
        }
        return times.toArray(new String[0]);
    }

    private static boolean isTimeValid(String start, String end) {
        return start.compareTo(end) < 0;
    }
}
