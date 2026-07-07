package gui;

import dao.TiketDAO;
import dao.TransaksiDAO;
import model.Tiket;
import model.Transaksi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TransaksiPanel extends JPanel {

    private final TiketDAO tiketDAO = new TiketDAO();
    private final TransaksiDAO transaksiDAO = new TransaksiDAO();
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    private JComboBox<Tiket> comboTiket;
    private JSpinner spinnerJumlah;
    private JLabel lblHargaSatuan;
    private JLabel lblStokTersedia;
    private JTable table;
    private DefaultTableModel tableModel;

    public TransaksiPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);

        refreshData();
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Form Transaksi Penjualan Tiket"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Pilih Tiket:"), gbc);

        comboTiket = new JComboBox<>();
        comboTiket.addActionListener(e -> updateInfoTiket());
        gbc.gridx = 1; gbc.gridwidth = 2;
        panel.add(comboTiket, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Harga Satuan:"), gbc);
        lblHargaSatuan = new JLabel("-");
        gbc.gridx = 1;
        panel.add(lblHargaSatuan, gbc);

        gbc.gridx = 2; gbc.gridy = 1;
        panel.add(new JLabel("Stok Tersedia:"), gbc);
        lblStokTersedia = new JLabel("-");
        gbc.gridx = 3;
        panel.add(lblStokTersedia, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Jumlah Beli:"), gbc);
        spinnerJumlah = new JSpinner(new SpinnerNumberModel(1, 1, 9999, 1));
        gbc.gridx = 1;
        panel.add(spinnerJumlah, gbc);

        JButton btnBeli = new JButton("Proses Transaksi");
        btnBeli.addActionListener(e -> prosesTransaksi());
        gbc.gridx = 3; gbc.gridy = 2;
        panel.add(btnBeli, gbc);

        return panel;
    }

    private JScrollPane buildTablePanel() {
        String[] columns = {"ID", "Nama Tiket", "Jumlah", "Total Harga", "Tanggal"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        return new JScrollPane(table);
    }

    public void refreshData() {
        loadComboTiket();
        loadTableData();
    }

    private void loadComboTiket() {
        comboTiket.removeAllItems();
        List<Tiket> daftarTiket = tiketDAO.getAllTiket();
        for (Tiket t : daftarTiket) {
            comboTiket.addItem(t);
        }
        updateInfoTiket();
    }

    private void updateInfoTiket() {
        Tiket selected = (Tiket) comboTiket.getSelectedItem();
        if (selected == null) {
            lblHargaSatuan.setText("-");
            lblStokTersedia.setText("-");
            return;
        }
        lblHargaSatuan.setText(currencyFormat.format(selected.getHarga()));
        lblStokTersedia.setText(String.valueOf(selected.getStokTiket()));
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        for (Transaksi trx : transaksiDAO.getAllTransaksi()) {
            tableModel.addRow(new Object[]{
                    trx.getId(), trx.getNamaTiket(), trx.getJumlah(),
                    currencyFormat.format(trx.getTotalHarga()), trx.getTanggal()
            });
        }
    }

    private void prosesTransaksi() {
        Tiket selected = (Tiket) comboTiket.getSelectedItem();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Tidak ada tiket yang tersedia.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int jumlahBeli = (Integer) spinnerJumlah.getValue();

        // Validasi awal di sisi UI; TransaksiDAO tetap ngecek ulang di level database
        if (jumlahBeli > selected.getStokTiket()) {
            JOptionPane.showMessageDialog(this,
                    "Jumlah pembelian (" + jumlahBeli + ") melebihi stok tersedia (" + selected.getStokTiket() + ").\n"
                            + "Silakan kurangi jumlah pembelian.",
                    "Stok Tidak Mencukupi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        TransaksiDAO.Status status = transaksiDAO.beli(selected.getId(), jumlahBeli);

        switch (status) {
            case SUKSES:
                double total = selected.getHarga() * jumlahBeli;
                JOptionPane.showMessageDialog(this,
                        "Transaksi berhasil!\nTotal: " + currencyFormat.format(total),
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
                spinnerJumlah.setValue(1);
                refreshData();
                break;
            case STOK_TIDAK_CUKUP:
                JOptionPane.showMessageDialog(this,
                        "Stok tiket tidak mencukupi untuk memproses transaksi ini.",
                        "Stok Tidak Mencukupi", JOptionPane.WARNING_MESSAGE);
                loadComboTiket();
                break;
            case GAGAL:
            default:
                JOptionPane.showMessageDialog(this,
                        "Transaksi gagal diproses. Silakan coba lagi.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }
}
