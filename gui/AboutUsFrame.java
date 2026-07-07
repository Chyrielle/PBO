package gui;

import javax.swing.*;
import java.awt.*;

public class AboutUsFrame extends JFrame {

    public AboutUsFrame() {

        setTitle("About Us - NextFly");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel(
                "NEXTFLY DEVELOPMENT TEAM",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 22));

        mainPanel.add(title, BorderLayout.NORTH);

        JPanel anggotaPanel = new JPanel(
                new GridLayout(2, 2, 20, 20)
        );

        anggotaPanel.add(createMemberPanel(
                "images/yoga.jpg",
                "Made Arya Dharma Putra Mahothama",
                "250030020"
        ));

        anggotaPanel.add(createMemberPanel(
                "images/yoga.jpg",
                "I Gusti Agung Bramantha Prana Citra J.",
                "250030061"
        ));

        anggotaPanel.add(createMemberPanel(
                "images/yoga.jpg",
                "I Putu Yoga Pratama",
                "250030062"
        ));

        anggotaPanel.add(createMemberPanel(
                "images/ajuz.jpg",
                "Gede Agung Bagus Aryadinatha",
                "250030094"
        ));

        mainPanel.add(anggotaPanel, BorderLayout.CENTER);

        add(mainPanel);

        setVisible(true);
    }

    private JPanel createMemberPanel(
            String imagePath,
            String nama,
            String nim
    ) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(
                panel,
                BoxLayout.Y_AXIS
        ));

        ImageIcon icon = new ImageIcon(imagePath);

        Image image = icon.getImage().getScaledInstance(
                120,
                120,
                Image.SCALE_SMOOTH
        );

        JLabel foto = new JLabel(
                new ImageIcon(image)
        );

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
}
}
