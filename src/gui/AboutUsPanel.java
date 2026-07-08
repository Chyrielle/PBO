package gui;

import javax.swing.*;
import java.awt.*;

public class AboutUsPanel extends JPanel {

    public AboutUsPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("NEXTFLY DEVELOPMENT TEAM", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        JPanel anggotaPanel = new JPanel(new GridLayout(2, 2, 20, 20));

        anggotaPanel.add(createMemberPanel(
                "images/arya.jpeg",
                "Made Arya Dharma Putra Mahothama",
                "250030020"
        ));

        anggotaPanel.add(createMemberPanel(
                "images/Bram.png",
                "I Gusti Agung Bramantha Prana Citra J.",
                "250030061"
        ));

        anggotaPanel.add(createMemberPanel(
                "images/Yoga.png",
                "I Putu Yoga Pratama",
                "250030062"
        ));

        anggotaPanel.add(createMemberPanel(
                "images/Ajuz.png",
                "Gede Agung Bagus Aryadinatha",
                "250030094"
        ));

        add(anggotaPanel, BorderLayout.CENTER);
    }

    private JPanel createMemberPanel(String imagePath, String nama, String nim) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel foto = new JLabel(loadFoto(imagePath));
        foto.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel namaLabel = new JLabel(nama);
        namaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nimLabel = new JLabel("NIM: " + nim);
        nimLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(10));
        panel.add(foto);
        panel.add(Box.createVerticalStrut(10));
        panel.add(namaLabel);
        panel.add(nimLabel);

        return panel;
    }

    
    private ImageIcon loadFoto(String path) {
        java.io.File file = new java.io.File(path);

        if (file.exists()) {
            ImageIcon icon = new ImageIcon(path);
            Image scaled = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        }

        return createPlaceholderIcon();
    }

    private ImageIcon createPlaceholderIcon() {
        int size = 120;
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(
                size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(new Color(220, 220, 220));
        g2.fillRect(0, 0, size, size);
        g2.setColor(Color.GRAY);
        g2.drawRect(0, 0, size - 1, size - 1);
        g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
        FontMetrics fm = g2.getFontMetrics();
        String text = "Foto tidak ada";
        int textWidth = fm.stringWidth(text);
        g2.drawString(text, (size - textWidth) / 2, size / 2);
        g2.dispose();
        return new ImageIcon(img);
    }
}
