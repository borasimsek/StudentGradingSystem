package View;

import javax.swing.*;
import java.awt.*;

public class BackGroundPanel extends JPanel {
    private Image backgroundImage;

    public BackGroundPanel(String imagePath) {
        backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("resources/arkaplan.jpg")).getImage();
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Resmi panel boyutuna göre ölçekleyerek çiz
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
