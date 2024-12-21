package com.example.sgs.View;

import javax.swing.*;
import java.awt.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.example.sgs.Entities.User;
import com.example.sgs.Repository.UserRepository;
import com.example.sgs.DatabaseConnection;

import static javax.swing.JOptionPane.YES_OPTION;

public class AdminDashboard extends JFrame {
    private static int courseCodeNumber = 100; // Ders kodu başlangıç numarası
    private static final Set<String> usedCourseCodes = new HashSet<>(); // Kullanılmış ders kodları
    private static final Map<String, String> savedCourses = new LinkedHashMap<>(); // Kaydedilen dersler

    private static int adminNumber = 1; // Admin numarası
    private static int instructorNumber = 1; // Eğitmen numarası
    private static int studentNumber = 1; // Öğrenci numarası
    private static final List<String> savedUsers = new ArrayList<>(); // Kaydedilen kullanıcılar

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
        title.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel courseForm = new JPanel(new GridLayout(5, 2, 10, 10));
        JTextField courseNameField = new JTextField();
        JTextField courseCodeField = new JTextField();

        JButton saveCourseButton = new JButton("Save Course");
        JButton backButton = new JButton("Back to Main Menu");

        saveCourseButton.addActionListener(e -> {
            String courseName = courseNameField.getText();
            String courseCode = courseCodeField.getText().toUpperCase();

            if (courseName.isEmpty() || courseCode.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (usedCourseCodes.contains(courseCode)) {
                JOptionPane.showMessageDialog(null, "Course code already used.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String generatedCode = courseCode + courseCodeNumber++;
            usedCourseCodes.add(courseCode);

            savedCourses.put(generatedCode, courseName);
            JOptionPane.showMessageDialog(null, "Course Saved: " + courseName + " (" + generatedCode + ")");

            // Clear fields
            courseNameField.setText("");
            courseCodeField.setText("");
        });

        backButton.addActionListener(e -> switchCard(mainPanel, "Main Menu"));

        courseForm.add(new JLabel("Course Name:"));
        courseForm.add(courseNameField);
        courseForm.add(new JLabel("Course Code:"));
        courseForm.add(courseCodeField);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(saveCourseButton);
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
                        userNumberLabel.setText("ID: " + userId); // User Number alanında göster
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