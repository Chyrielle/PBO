package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Frame utama setelah login. Navigasi 4 halaman utama (Master Tiket,
 * Transaksi, About Us) lewat JMenuBar, ditampilkan pakai CardLayout.
 * Logout ada di menu Akun.
 */
public class MainFrame extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    private static final String CARD_MASTER_TIKET = "MASTER_TIKET";
    private static final String CARD_TRANSAKSI = "TRANSAKSI";
    private static final String CARD_ABOUT_US = "ABOUT_US";

    private MasterTiketPanel masterTiketPanel;
    private TransaksiPanel transaksiPanel;

    public MainFrame() {
        setTitle("NextFly Ticketing System");
        setSize(950, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        masterTiketPanel = new MasterTiketPanel();
        transaksiPanel = new TransaksiPanel();

        cardPanel.add(masterTiketPanel, CARD_MASTER_TIKET);
        cardPanel.add(transaksiPanel, CARD_TRANSAKSI);
        cardPanel.add(new AboutUsPanel(), CARD_ABOUT_US);

        setJMenuBar(buildMenuBar());
        add(cardPanel, BorderLayout.CENTER);

        cardLayout.show(cardPanel, CARD_MASTER_TIKET);
    }

    private JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuNavigasi = new JMenu("Navigasi");

        JMenuItem itemMasterTiket = new JMenuItem("Master Tiket");
        itemMasterTiket.addActionListener(e -> {
            masterTiketPanel.refreshData();
            cardLayout.show(cardPanel, CARD_MASTER_TIKET);
        });

        JMenuItem itemTransaksi = new JMenuItem("Transaksi");
        itemTransaksi.addActionListener(e -> {
            transaksiPanel.refreshData();
            cardLayout.show(cardPanel, CARD_TRANSAKSI);
        });

        JMenuItem itemAboutUs = new JMenuItem("About Us");
        itemAboutUs.addActionListener(e -> cardLayout.show(cardPanel, CARD_ABOUT_US));

        menuNavigasi.add(itemMasterTiket);
        menuNavigasi.add(itemTransaksi);
        menuNavigasi.add(itemAboutUs);

        JMenu menuAkun = new JMenu("Akun");
        JMenuItem itemLogout = new JMenuItem("Logout");
        itemLogout.addActionListener(e -> doLogout());
        menuAkun.add(itemLogout);

        menuBar.add(menuNavigasi);
        menuBar.add(menuAkun);

        return menuBar;
    }

    private void doLogout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin logout?",
                "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            new LoginFrame().setVisible(true);
        }
    }
}
