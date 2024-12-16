package View;
import javax.swing.*;
public class AdminDashboard {
    public static void show() {
        JFrame frame = new JFrame("Admin Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        JLabel label = new JLabel("Admin Dashboard - Manage Users and Courses", SwingConstants.CENTER);
        frame.add(label);
        frame.setVisible(true);
}

}
