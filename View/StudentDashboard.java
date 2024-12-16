package View;

import javax.swing.*;

public class StudentDashboard {
    public static void show() {
        JFrame frame = new JFrame("Student Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        JLabel label = new JLabel("Student Dashboard - Register Courses and View Grades", SwingConstants.CENTER);
        frame.add(label);
        frame.setVisible(true);
}
}

