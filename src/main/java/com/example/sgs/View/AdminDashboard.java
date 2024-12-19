package com.example.sgs.View;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class AdminDashboard {
    private static int courseCodeNumber = 100; // Ders kodu başlangıç numarası
    private static final Set<String> usedCourseCodes = new HashSet<>(); // Kullanılmış ders kodları
    private static final Map<String, String> savedCourses = new LinkedHashMap<>(); // Kaydedilen dersler

    private static int adminNumber = 1; // Admin numarası
    private static int instructorNumber = 1; // Eğitmen numarası
    private static int studentNumber = 1; // Öğrenci numarası
    private static final List<String> savedUsers = new ArrayList<>(); // Kaydedilen kullanıcılar

    public static void show() {
        JFrame frame = new JFrame("Admin Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        // Ana Panel
        JPanel mainPanel = new JPanel(new CardLayout());
        frame.add(mainPanel);

        // ==== Ana Menü ====
        JPanel mainMenu = createMainMenu(mainPanel);
        mainPanel.add(mainMenu, "Main Menu");

        // ==== Manage Courses Paneli ====
        JPanel manageCoursesPanel = createManageCoursesPanel(mainPanel);
        mainPanel.add(manageCoursesPanel, "Manage Courses");

        // ==== Manage Users Paneli ====
        JPanel manageUsersPanel = createManageUsersPanel(mainPanel);
        mainPanel.add(manageUsersPanel, "Manage Users");

        frame.setVisible(true);
    }

    private static JPanel createMainMenu(JPanel mainPanel) {
        JPanel mainMenu = new JPanel(new GridLayout(3, 1, 10, 10));
        JLabel title = new JLabel("Admin Dashboard - Main Menu", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JButton manageCoursesButton = new JButton("Manage Courses");
        JButton manageUsersButton = new JButton("Manage Users");
        JButton logOutButton = new JButton("Log Out");

        manageCoursesButton.addActionListener(e -> switchCard(mainPanel, "Manage Courses"));
        manageUsersButton.addActionListener(e -> switchCard(mainPanel, "Manage Users"));
        logOutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) System.exit(0);
        });

        mainMenu.add(title);
        mainMenu.add(manageCoursesButton);
        mainMenu.add(manageUsersButton);
        mainMenu.add(logOutButton);
        return mainMenu;
    }

    private static JPanel createManageCoursesPanel(JPanel mainPanel) {
        JPanel manageCoursesPanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Manage Courses", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel courseForm = new JPanel(new GridLayout(10, 2, 10, 10));
        JTextField courseNameField = new JTextField();
        JComboBox<String> facultyDropdown = new JComboBox<>(new String[]{"Engineering", "Business", "Law", "Architecture", "Others"});
        JTextField courseCodeField = new JTextField();
        JLabel generatedCodeLabel = new JLabel("Generated Code:");
        JTextField quotaField = new JTextField();

        JComboBox<String> instructorDropdown = new JComboBox<>(new String[]{"Instructor A", "Instructor B", "Instructor C"});
        JComboBox<String> buildingDropdown = new JComboBox<>(new String[]{"AB1", "AB2", "AB3", "AB4", "AB5"});
        JComboBox<String> classDropdown = new JComboBox<>(generateClassOptions());

        JComboBox<String> startTimeDropdown = new JComboBox<>(generateTimeOptions(8, 40, 16, 40, 60));
        JComboBox<String> endTimeDropdown = new JComboBox<>(generateTimeOptions(9, 30, 18, 30, 60));

        JButton saveCourseButton = new JButton("Save Course");
        JButton deleteCourseButton = new JButton("Delete Course");
        JButton backButton = new JButton("Back to Main Menu");

        // Ders kaydetme işlemi
        saveCourseButton.addActionListener(e -> {
            String courseName = courseNameField.getText();
            String faculty = (String) facultyDropdown.getSelectedItem();
            String coursePrefix = courseCodeField.getText().toUpperCase();
            String quota = quotaField.getText();
            String instructor = (String) instructorDropdown.getSelectedItem();
            String building = (String) buildingDropdown.getSelectedItem();
            String classroom = (String) classDropdown.getSelectedItem();
            String startTime = (String) startTimeDropdown.getSelectedItem();
            String endTime = (String) endTimeDropdown.getSelectedItem();

            if (coursePrefix.length() < 2 || coursePrefix.length() > 3 || usedCourseCodes.contains(coursePrefix)) {
                JOptionPane.showMessageDialog(null, "Invalid or duplicate course code.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String generatedCode = coursePrefix + courseCodeNumber++;
            usedCourseCodes.add(coursePrefix);

            String courseDetails = String.format("%s (%s) - %s - %s - %s - %s - %s", courseName, faculty, generatedCode, building, classroom, startTime, endTime);
            savedCourses.put(generatedCode, courseDetails);

            JOptionPane.showMessageDialog(null, "Course Created:\n" + courseDetails);

            // Formu sıfırla
            courseNameField.setText("");
            courseCodeField.setText("");
            quotaField.setText("");
        });

        // Ders silme işlemi
        deleteCourseButton.addActionListener(e -> {
            JFrame deleteFrame = new JFrame("Delete Course");
            deleteFrame.setSize(400, 300);
            deleteFrame.setLayout(new BorderLayout());

            JList<String> courseList = new JList<>(savedCourses.values().toArray(new String[0]));
            JScrollPane scrollPane = new JScrollPane(courseList);

            JButton deleteButton = new JButton("Delete Selected Course");
            deleteButton.addActionListener(ev -> {
                String selectedCourse = courseList.getSelectedValue();
                if (selectedCourse != null) {
                    String codeToDelete = savedCourses.entrySet().stream()
                            .filter(entry -> entry.getValue().equals(selectedCourse))
                            .map(Map.Entry::getKey)
                            .findFirst().orElse(null);

                    if (codeToDelete != null) {
                        savedCourses.remove(codeToDelete);
                        usedCourseCodes.remove(codeToDelete.substring(0, 2));
                        courseList.setListData(savedCourses.values().toArray(new String[0]));
                        JOptionPane.showMessageDialog(deleteFrame, "Course Deleted: " + selectedCourse);
                    }
                }
            });

            deleteFrame.add(scrollPane, BorderLayout.CENTER);
            deleteFrame.add(deleteButton, BorderLayout.SOUTH);
            deleteFrame.setVisible(true);
        });

        backButton.addActionListener(e -> switchCard(mainPanel, "Main Menu"));

        courseForm.add(new JLabel("Course Name:"));
        courseForm.add(courseNameField);
        courseForm.add(new JLabel("Faculty:"));
        courseForm.add(facultyDropdown);
        courseForm.add(new JLabel("Course Code:"));
        courseForm.add(courseCodeField);
        courseForm.add(new JLabel("Generated Code:"));
        courseForm.add(generatedCodeLabel);
        courseForm.add(new JLabel("Quota:"));
        courseForm.add(quotaField);
        courseForm.add(new JLabel("Instructor:"));
        courseForm.add(instructorDropdown);
        courseForm.add(new JLabel("Building:"));
        courseForm.add(buildingDropdown);
        courseForm.add(new JLabel("Classroom:"));
        courseForm.add(classDropdown);
        courseForm.add(new JLabel("Start Time:"));
        courseForm.add(startTimeDropdown);
        courseForm.add(new JLabel("End Time:"));
        courseForm.add(endTimeDropdown);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(saveCourseButton);
        buttonsPanel.add(deleteCourseButton);
        buttonsPanel.add(backButton);

        manageCoursesPanel.add(title, BorderLayout.NORTH);
        manageCoursesPanel.add(courseForm, BorderLayout.CENTER);
        manageCoursesPanel.add(buttonsPanel, BorderLayout.SOUTH);

        return manageCoursesPanel;
    }

    private static JPanel createManageUsersPanel(JPanel mainPanel) {
        JPanel manageUsersPanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Manage Users", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel userForm = new JPanel(new GridLayout(7, 2, 10, 10));
        JTextField userNameField = new JTextField();
        JTextField userSurnameField = new JTextField();
        JComboBox<String> userTypeDropdown = new JComboBox<>(new String[]{"Student", "Instructor", "Admin"});
        JLabel emailLabel = new JLabel("Email:");
        JLabel userNumberLabel = new JLabel("User Number:");

        JButton addUserButton = new JButton("Add User");
        JButton deleteUserButton = new JButton("Delete User");
        JButton backButton = new JButton("Back to Main Menu");

        // Kullanıcı adı ve soyadı değişiminde e-posta ve numara oluşturma
        userNameField.addCaretListener(e -> updateUserInfo(userNameField, userSurnameField, userTypeDropdown, emailLabel, userNumberLabel, false));
        userSurnameField.addCaretListener(e -> updateUserInfo(userNameField, userSurnameField, userTypeDropdown, emailLabel, userNumberLabel, false));
        userTypeDropdown.addActionListener(e -> updateUserInfo(userNameField, userSurnameField, userTypeDropdown, emailLabel, userNumberLabel, false));

        // Kullanıcı ekleme
        addUserButton.addActionListener(e -> {
            String userName = userNameField.getText();
            String userSurname = userSurnameField.getText();
            String email = emailLabel.getText();
            String userNumber = userNumberLabel.getText();

            if (userName.isEmpty() || userSurname.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            savedUsers.add(String.format("%s %s - %s - %s", userName, userSurname, email, userNumber));
            JOptionPane.showMessageDialog(null, "User Added:\nName: " + userName + " " + userSurname + "\nEmail: " + email + "\nNumber: " + userNumber);

            // Numara sadece kaydetme sırasında artırılır
            String type = (String) userTypeDropdown.getSelectedItem();
            if (type.equals("Student")) studentNumber++;
            if (type.equals("Instructor")) instructorNumber++;
            if (type.equals("Admin")) adminNumber++;

            // Formu sıfırla
            userNameField.setText("");
            userSurnameField.setText("");
        });

        // Kullanıcı silme işlemi
        deleteUserButton.addActionListener(e -> {
            JFrame deleteFrame = new JFrame("Delete User");
            deleteFrame.setSize(400, 300);
            deleteFrame.setLayout(new BorderLayout());

            JList<String> userList = new JList<>(savedUsers.toArray(new String[0]));
            JScrollPane scrollPane = new JScrollPane(userList);

            JButton deleteButton = new JButton("Delete Selected User");
            deleteButton.addActionListener(ev -> {
                String selectedUser = userList.getSelectedValue();
                if (selectedUser != null) {
                    savedUsers.remove(selectedUser);
                    userList.setListData(savedUsers.toArray(new String[0]));
                    JOptionPane.showMessageDialog(deleteFrame, "User Deleted: " + selectedUser);
                }
            });

            deleteFrame.add(scrollPane, BorderLayout.CENTER);
            deleteFrame.add(deleteButton, BorderLayout.SOUTH);
            deleteFrame.setVisible(true);
        });

        backButton.addActionListener(e -> switchCard(mainPanel, "Main Menu"));

        userForm.add(new JLabel("First Name:"));
        userForm.add(userNameField);
        userForm.add(new JLabel("Last Name:"));
        userForm.add(userSurnameField);
        userForm.add(new JLabel("User Type:"));
        userForm.add(userTypeDropdown);
        userForm.add(new JLabel("Email:"));
        userForm.add(emailLabel);
        userForm.add(new JLabel("User Number:"));
        userForm.add(userNumberLabel);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(addUserButton);
        buttonsPanel.add(deleteUserButton);
        buttonsPanel.add(backButton);

        manageUsersPanel.add(title, BorderLayout.NORTH);
        manageUsersPanel.add(userForm, BorderLayout.CENTER);
        manageUsersPanel.add(buttonsPanel, BorderLayout.SOUTH);

        return manageUsersPanel;
    }

    private static void updateUserInfo(JTextField nameField, JTextField surnameField, JComboBox<String> typeDropdown, JLabel emailLabel, JLabel numberLabel, boolean increment) {
        String name = nameField.getText().toLowerCase().replaceAll("\\s", "");
        String surname = surnameField.getText().toLowerCase();
        String email = name + "." + surname + "@edu.tr";
        emailLabel.setText(email);

        String type = (String) typeDropdown.getSelectedItem();
        String number = switch (type) {
            case "Student" -> "S" + String.format("%05d", studentNumber);
            case "Instructor" -> "I" + String.format("%05d", instructorNumber);
            case "Admin" -> "A" + String.format("%05d", adminNumber);
            default -> "U00000";
        };
        numberLabel.setText(number);
    }

    private static String[] generateTimeOptions(int startHour, int startMinute, int endHour, int endMinute, int incrementMinutes) {
        List<String> times = new ArrayList<>();
        for (int hour = startHour; hour <= endHour; hour++) {
            int minute = (hour == startHour) ? startMinute : 0;
            while (minute < 60 && (hour < endHour || (hour == endHour && minute <= endMinute))) {
                times.add(String.format("%02d:%02d", hour, minute));
                minute += incrementMinutes;
            }
        }
        return times.toArray(new String[0]);
    }

    private static String[] generateClassOptions() {
        List<String> classes = new ArrayList<>();
        for (int block = 100; block <= 400; block += 100) {
            for (int room = block; room <= block + 30; room++) {
                classes.add(String.valueOf(room));
            }
        }
        return classes.toArray(new String[0]);
    }

    private static void switchCard(JPanel mainPanel, String cardName) {
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, cardName);
    }
}
