package com.example.sgs.View;

import javax.swing.*;
import java.awt.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.example.sgs.Entities.Course;
import com.example.sgs.Entities.User;
import com.example.sgs.Repository.CourseRepository;
import com.example.sgs.Repository.UserRepository;
import com.example.sgs.DatabaseConnection;

import static javax.swing.JOptionPane.YES_OPTION;

public class AdminDashboard extends JFrame {
    private static final Map<String, Integer> facultyCourseCounter = new HashMap<>() {{
        put("Engineering", 100);
        put("Law", 200);
        put("Business", 300);
        put("Language", 400);
        put("Aviation", 500);
        put("Others", 600);
    }};

    public void show() {
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
            if (confirm == YES_OPTION) System.exit(0);
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
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        manageCoursesPanel.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ===== Course Details Bölümü =====
        formPanel.setBorder(BorderFactory.createTitledBorder("Course Details"));

        JTextField courseNameField = new JTextField(20);
        JComboBox<String> facultyComboBox = new JComboBox<>(new String[]{"Engineering", "Law", "Business", "Language", "Aviation", "Others"});
        JTextField courseCodePrefixField = new JTextField(5); // Kullanıcıdan alınan kısaltma
        JLabel generatedIdLabel = new JLabel("Generated ID: N/A");
        JTextField creditField = new JTextField(10);
        JTextField quotaField = new JTextField(10);
        JTextField yearField = new JTextField(10);
        JComboBox<String> termComboBox = new JComboBox<>(new String[]{"Fall", "Spring", "Summer"});

        // Course Details Alanlarını Yerleştirme
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Course Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(courseNameField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Faculty:"), gbc);
        gbc.gridx = 1;
        formPanel.add(facultyComboBox, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Course Prefix:"), gbc);
        gbc.gridx = 1;
        formPanel.add(courseCodePrefixField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Generated ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(generatedIdLabel, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Credits:"), gbc);
        gbc.gridx = 1;
        formPanel.add(creditField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Quota:"), gbc);
        gbc.gridx = 1;
        formPanel.add(quotaField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Year:"), gbc);
        gbc.gridx = 1;
        formPanel.add(yearField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Term:"), gbc);
        gbc.gridx = 1;
        formPanel.add(termComboBox, gbc);

        // ===== Instructor Bölümü =====
        JPanel instructorPanel = new JPanel(new GridBagLayout());
        instructorPanel.setBorder(BorderFactory.createTitledBorder("Instructor"));

        JComboBox<User> instructorComboBox = new JComboBox<>();
        try {
            UserRepository userRepository = new UserRepository(DatabaseConnection.getConnection());
            List<User> instructors = userRepository.findAllInstructors();
            for (User instructor : instructors) {
                instructorComboBox.addItem(instructor);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading instructors: " + e.getMessage());
        }

        gbc.gridx = 0; gbc.gridy = 0;
        instructorPanel.add(new JLabel("Assigned Instructor:"), gbc);
        gbc.gridx = 1;
        instructorPanel.add(instructorComboBox, gbc);

        // ===== Schedule Bölümü =====
        JPanel schedulePanel = new JPanel(new GridBagLayout());
        schedulePanel.setBorder(BorderFactory.createTitledBorder("Schedule"));

        JComboBox<String> dayComboBox = new JComboBox<>(new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"});
        JComboBox<String> startTimeComboBox = new JComboBox<>(new String[]{"8:40", "9:40", "10:40", "11:40", "12:40", "13:40", "14:40", "15:40", "16:40", "17:40"});
        JComboBox<String> endTimeComboBox = new JComboBox<>(new String[]{"9:30", "10:30", "11:30", "12:30", "13:30", "14:30", "15:30", "16:30", "17:30", "19:30"});
        JComboBox<String> buildingComboBox = new JComboBox<>(new String[]{"AB1", "AB2", "AB3", "AB4", "AB5"});
        JComboBox<String> roomComboBox = new JComboBox<>();

        // Room Seçeneklerini Dinamik Güncelleme
        buildingComboBox.addActionListener(e -> {
            roomComboBox.removeAllItems();
            for (int i = 100; i <= 140; i++) roomComboBox.addItem(String.valueOf(i));
            for (int i = 200; i <= 240; i++) roomComboBox.addItem(String.valueOf(i));
            for (int i = 300; i <= 340; i++) roomComboBox.addItem(String.valueOf(i));
            for (int i = 400; i <= 440; i++) roomComboBox.addItem(String.valueOf(i));
        });

        // Schedule Alanlarını Yerleştirme
        gbc.gridx = 0; gbc.gridy = 0;
        schedulePanel.add(new JLabel("Day:"), gbc);
        gbc.gridx = 1;
        schedulePanel.add(dayComboBox, gbc);

        gbc.gridx = 0; gbc.gridy++;
        schedulePanel.add(new JLabel("Start Time:"), gbc);
        gbc.gridx = 1;
        schedulePanel.add(startTimeComboBox, gbc);

        gbc.gridx = 0; gbc.gridy++;
        schedulePanel.add(new JLabel("End Time:"), gbc);
        gbc.gridx = 1;
        schedulePanel.add(endTimeComboBox, gbc);

        gbc.gridx = 0; gbc.gridy++;
        schedulePanel.add(new JLabel("Building:"), gbc);
        gbc.gridx = 1;
        schedulePanel.add(buildingComboBox, gbc);

        gbc.gridx = 0; gbc.gridy++;
        schedulePanel.add(new JLabel("Room:"), gbc);
        gbc.gridx = 1;
        schedulePanel.add(roomComboBox, gbc);

        // ===== Course Code ID Oluşturma =====
        facultyComboBox.addActionListener(e -> {
            String faculty = (String) facultyComboBox.getSelectedItem();
            try {
                CourseRepository courseRepository = new CourseRepository(DatabaseConnection.getConnection());
                int generatedId = courseRepository.getNextCourseId(faculty); // Fakülteye göre ID al.
                generatedIdLabel.setText("Generated ID: " + courseCodePrefixField.getText().toUpperCase() + generatedId);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error generating course ID: " + ex.getMessage());
            }
        });

        // ===== Buton Paneli =====
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton backButton = new JButton("Back");

        buttonsPanel.add(saveButton);
        buttonsPanel.add(backButton);

        saveButton.addActionListener(e -> {
            String courseName = courseNameField.getText();
            String faculty = (String) facultyComboBox.getSelectedItem();
            String courseCodePrefix = courseCodePrefixField.getText().toUpperCase();
            String generatedId = generatedIdLabel.getText().replace("Generated ID: ", "");
            int credits = Integer.parseInt(creditField.getText());
            int quota = Integer.parseInt(quotaField.getText());
            int year = Integer.parseInt(yearField.getText());
            String term = (String) termComboBox.getSelectedItem();
            String day = (String) dayComboBox.getSelectedItem();
            String startTime = (String) startTimeComboBox.getSelectedItem();
            String endTime = (String) endTimeComboBox.getSelectedItem();
            String building = (String) buildingComboBox.getSelectedItem();
            String room = (String) roomComboBox.getSelectedItem();
            User instructor = (User) instructorComboBox.getSelectedItem();

            // Veritabanına kaydetme işlemi
            CourseRepository courseRepository = new CourseRepository(DatabaseConnection.getConnection());
            Course newCourse = new Course(
                    Integer.parseInt(generatedId), courseName, courseCodePrefix + generatedId, instructor, credits, quota, year,
                    Course.Term.valueOf(term.toUpperCase()), faculty
            );
            if (courseRepository.save(newCourse)) {
                JOptionPane.showMessageDialog(null, "Course saved successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to save course.");
            }
        });

        // Ana Paneli Düzenleme
        manageCoursesPanel.add(formPanel, BorderLayout.WEST);
        manageCoursesPanel.add(schedulePanel, BorderLayout.CENTER);
        manageCoursesPanel.add(instructorPanel, BorderLayout.EAST);
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

        // Kullanıcı adı ve soyadı değişiminde dinamik e-posta güncelleme
        userNameField.addCaretListener(e -> updateUserInfo(userNameField, userSurnameField, userTypeDropdown, emailLabel, userNumberLabel));
        userSurnameField.addCaretListener(e -> updateUserInfo(userNameField, userSurnameField, userTypeDropdown, emailLabel, userNumberLabel));

        // Kullanıcı ekleme işlemi
        addUserButton.addActionListener(e -> {
            String userName = userNameField.getText();
            String userSurname = userSurnameField.getText();
            String email = emailLabel.getText();
            String userType = (String) userTypeDropdown.getSelectedItem();

            if (userName.isEmpty() || userSurname.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String generatedPassword = generatePassword(userName, userSurname);

            try {
                UserRepository userRepository = new UserRepository(DatabaseConnection.getConnection());
                String hashedPassword = hashPassword(generatedPassword);

                // Yeni kullanıcı oluştur
                User newUser = new User(0, email, hashedPassword, User.UserType.valueOf(userType.toUpperCase()), userName, userSurname);
                boolean isSaved = userRepository.save(newUser); // Kullanıcıyı kaydet

                if (isSaved) {
                    // Son eklenen user_id'yi al
                    int userId = userRepository.getLastInsertedUserId();
                    if (userId != -1) {
                        userNumberLabel.setText("ID: " + userId); // Doğru ID'yi göster
                        JOptionPane.showMessageDialog(null, "User Added Successfully!\nGenerated Password: " + generatedPassword + "\nUser ID: " + userId);
                    } else {
                        JOptionPane.showMessageDialog(null, "User added but ID could not be retrieved.", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add user. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred while adding the user.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Formu sıfırla
            userNameField.setText("");
            userSurnameField.setText("");
            emailLabel.setText("Email:");
            userNumberLabel.setText("User Number:");
        });



        // Kullanıcı silme işlemi
        deleteUserButton.addActionListener(e -> {
            try {
                UserRepository userRepository = new UserRepository(DatabaseConnection.getConnection());
                List<User> users = userRepository.findAll(); // Tüm kullanıcıları getir

                JFrame deleteFrame = new JFrame("Delete User");
                deleteFrame.setSize(400, 300);
                deleteFrame.setLayout(new BorderLayout());

                JList<User> userList = new JList<>(users.toArray(new User[0])); // Kullanıcıları listele
                JScrollPane scrollPane = new JScrollPane(userList);

                JButton confirmDeleteButton = new JButton("Delete Selected User");
                confirmDeleteButton.addActionListener(event -> {
                    User selectedUser = userList.getSelectedValue();
                    if (selectedUser != null) {
                        boolean isDeleted = userRepository.delete(selectedUser.getUserId());
                        if (isDeleted) {
                            JOptionPane.showMessageDialog(deleteFrame, "User Deleted Successfully!");
                            deleteFrame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(deleteFrame, "Failed to delete user.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                deleteFrame.add(scrollPane, BorderLayout.CENTER);
                deleteFrame.add(confirmDeleteButton, BorderLayout.SOUTH);
                deleteFrame.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred while fetching users.", "Error", JOptionPane.ERROR_MESSAGE);
            }
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

    private static void updateUserInfo(JTextField nameField, JTextField surnameField, JComboBox<String> typeDropdown, JLabel emailLabel, JLabel numberLabel) {
        String name = nameField.getText().trim().toLowerCase().replaceAll("\\s", "");
        String surname = surnameField.getText().trim().toLowerCase().replaceAll("\\s", "");

        // E-posta oluşturma
        if (!name.isEmpty() || !surname.isEmpty()) { // Sadece biri dolu olsa bile işlem yap
            String email = name + "." + surname + "@edu.tr";
            emailLabel.setText(email);

            // Kullanıcı numarasını yalnızca bir şeyler yazılmaya başlanırsa güncelle
            try {
                UserRepository userRepository = new UserRepository(DatabaseConnection.getConnection());
                int nextUserId = userRepository.getNextUserId(); // Tahmini sonraki ID
                numberLabel.setText(String.valueOf(nextUserId)); // Yalnızca ID'yi göster
            } catch (Exception ex) {
                ex.printStackTrace();
                numberLabel.setText("Error"); // Hata durumu
            }
        } else {
            emailLabel.setText("Email:");
            numberLabel.setText(""); // Ad ve soyad tamamen boşsa User Number kısmını boş bırak
        }
    }




    private static String generatePassword(String firstName, String lastName) {
        String namePart = firstName.toLowerCase() + lastName.toLowerCase();
        int randomNumber = ThreadLocalRandom.current().nextInt(10000, 99999); // 5 haneli rastgele sayı
        return namePart + randomNumber;
    }

    private static void switchCard(JPanel mainPanel, String cardName) {
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, cardName);
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}