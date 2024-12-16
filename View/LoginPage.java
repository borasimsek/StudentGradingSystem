package View;

import javax.swing.*;
import java.awt.*;

public class LoginPage {
    public static void main(String[] args) {
        // Ana pencereyi oluştur
        JFrame frame = new JFrame("Student Grading System - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // BackGroundPanel ile arka plan resmi ekle
        Image backgroundImage = new ImageIcon(LoginPage.class.getClassLoader().getResource("arkaplan.jpg")).getImage();
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout()); // Arka plan düzenini ayarla

        // GridBagLayout için ayarlar
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;

        // Üst panel (Başlık)
        JLabel headerLabel = new JLabel("Student Grading System (SGS)", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 48));
        headerLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 0.1;
        backgroundPanel.add(headerLabel, gbc);

        // Form için arka plan paneli
        JPanel formBackgroundPanel = new JPanel();
        formBackgroundPanel.setBackground(new Color(0, 51, 102, 200));
        formBackgroundPanel.setLayout(new GridBagLayout());
        formBackgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(10, 10, 10, 10);
        formGbc.fill = GridBagConstraints.HORIZONTAL;

        // E-posta etiketi
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        emailLabel.setForeground(Color.WHITE);
        formGbc.gridx = 0;
        formGbc.gridy = 0;
        formBackgroundPanel.add(emailLabel, formGbc);

        // E-posta giriş kutusu
        JTextField emailField = new JTextField(20);
        emailField.setFont(new Font("Arial", Font.PLAIN, 18));
        formGbc.gridx = 1;
        formGbc.gridy = 0;
        formBackgroundPanel.add(emailField, formGbc);

        // Şifre etiketi
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordLabel.setForeground(Color.WHITE);
        formGbc.gridx = 0;
        formGbc.gridy = 1;
        formBackgroundPanel.add(passwordLabel, formGbc);

        // Şifre giriş kutusu
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        formGbc.gridx = 1;
        formGbc.gridy = 1;
        formBackgroundPanel.add(passwordField, formGbc);

        // Giriş butonu
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        formGbc.gridx = 0;
        formGbc.gridy = 2;
        formBackgroundPanel.add(loginButton, formGbc);

        // İptal butonu
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 18));
        formGbc.gridx = 1;
        formGbc.gridy = 2;
        formBackgroundPanel.add(cancelButton, formGbc);

        // Alt bilgilendirici mesaj
        JLabel footerLabel = new JLabel("Welcome to the SGS system. Please login to continue.");
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        footerLabel.setForeground(Color.WHITE);
        formGbc.gridx = 0;
        formGbc.gridy = 3;
        formGbc.gridwidth = 2;
        formBackgroundPanel.add(footerLabel, formGbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 1;
        backgroundPanel.add(formBackgroundPanel, gbc);


        frame.add(backgroundPanel);
        frame.setVisible(true);
    }
}
