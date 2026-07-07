package gui;

import dao.TiketDAO;
import model.Tiket;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MasterTiketPanel extends JPanel {

    private final TiketDAO tiketDAO = new TiketDAO();

    private JTextField txtNama, txtHarga, txtStok;
    private JTable table;
    private DefaultTableModel tableModel;
    private int selectedId = -1;

    public MasterTiketPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildButtonPanel(), BorderLayout.SOUTH);

        refreshData();
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Data Tiket"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nama Tiket:"), gbc);
        txtNama = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtNama, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(new JLabel("Harga:"), gbc);
        txtHarga = new JTextField(12);
        gbc.gridx = 3;
        panel.add(txtHarga, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Stok Tiket:"), gbc);
        txtStok = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtStok, gbc);

        return panel;
    }

    private JScrollPane buildTablePanel() {
        String[] columns = {"ID", "Nama Tiket", "Harga", "Stok"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                populateFormFromSelectedRow();
            }
        });
        return new JScrollPane(table);
    }

    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel();

        JButton btnTambah = new JButton("Tambah");
        btnTambah.addActionListener(e -> tambahTiket());

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(e -> updateTiket());

        JButton btnHapus = new JButton("Hapus");
        btnHapus.addActionListener(e -> hapusTiket());

        JButton btnBersihkan = new JButton("Bersihkan Form");
        btnBersihkan.addActionListener(e -> clearForm());

        panel.add(btnTambah);
        panel.add(btnUpdate);
        panel.add(btnHapus);
        panel.add(btnBersihkan);
        return panel;
    }

    public void refreshData() {
        tableModel.setRowCount(0);
        for (Tiket t : tiketDAO.getAllTiket()) {
            tableModel.addRow(new Object[]{
                    t.getId(), t.getNamaTiket(), t.getHarga(), t.getStokTiket()
            });
        }
    }

    private void populateFormFromSelectedRow() {
        int row = table.getSelectedRow();
        selectedId = (int) tableModel.getValueAt(row, 0);
        txtNama.setText(String.valueOf(tableModel.getValueAt(row, 1)));
        txtHarga.setText(String.valueOf(tableModel.getValueAt(row, 2)));
        txtStok.setText(String.valueOf(tableModel.getValueAt(row, 3)));
    }

    private void clearForm() {
        selectedId = -1;
        txtNama.setText("");
        txtHarga.setText("");
        txtStok.setText("");
        table.clearSelection();
    }

    private boolean validasiForm() {
        if (txtNama.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama tiket wajib diisi.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            Double.parseDouble(txtHarga.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(txtStok.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Stok tiket harus berupa angka bulat.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private Tiket buildTiketFromForm() {
        Tiket t = new Tiket();
        t.setId(selectedId);
        t.setNamaTiket(txtNama.getText().trim());
        t.setHarga(Double.parseDouble(txtHarga.getText().trim()));
        t.setStokTiket(Integer.parseInt(txtStok.getText().trim()));
        return t;
    }

    private void tambahTiket() {
        if (!validasiForm()) return;

        boolean sukses = tiketDAO.tambah(buildTiketFromForm());
        if (sukses) {
            JOptionPane.showMessageDialog(this, "Tiket berhasil ditambahkan.");
            clearForm();
            refreshData();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menambah tiket.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTiket() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data tiket pada tabel terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validasiForm()) return;

        boolean sukses = tiketDAO.update(buildTiketFromForm());
        if (sukses) {
            JOptionPane.showMessageDialog(this, "Tiket berhasil diperbarui.");
            clearForm();
            refreshData();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal memperbarui tiket.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusTiket() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data tiket pada tabel terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin menghapus tiket ini?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean sukses = tiketDAO.hapus(selectedId);
            if (sukses) {
                JOptionPane.showMessageDialog(this, "Tiket berhasil dihapus.");
                clearForm();
                refreshData();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Gagal menghapus tiket (mungkin masih terpakai di data transaksi).",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
