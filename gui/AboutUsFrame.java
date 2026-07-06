package gui;

import javax.swing.*;
import java.awt.*;

public class AboutUsFrame extends JFrame {

    public AboutUsFrame() {

        setTitle("About Us - NextFly");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        panel.add(new JLabel("NEXTFLY", SwingConstants.CENTER));

        panel.add(new JLabel("Arya", SwingConstants.CENTER));
        panel.add(new JLabel("Yoga", SwingConstants.CENTER));
        panel.add(new JLabel("Ajuz", SwingConstants.CENTER));
        panel.add(new JLabel("Nama Anda", SwingConstants.CENTER));

        add(panel);

        setVisible(true);
    }
}
