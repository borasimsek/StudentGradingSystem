package View;

import javax.swing.*;

public class InstructorDashboard {
    public static void show() {
        JFrame frame = new JFrame("Instructor Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        JLabel label = new JLabel("Instructor Dashboard - Grade Students and Manage Courses", SwingConstants.CENTER);
        frame.add(label);
        frame.setVisible(true);
    }
}
