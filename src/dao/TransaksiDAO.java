package dao;

import database.DatabaseConnection;
import model.Transaksi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class TransaksiDAO {

    public enum Status {
        SUKSES,
        STOK_TIDAK_CUKUP,
        GAGAL
    }

    private final TiketDAO tiketDAO = new TiketDAO();

    public Status beli(int tiketId, int jumlah) {

        int stokSekarang = tiketDAO.getStok(tiketId);

        if (jumlah > stokSekarang) {
            return Status.STOK_TIDAK_CUKUP;
        }

        boolean stokBerhasilDikurangi = tiketDAO.kurangiStok(tiketId, jumlah);

        if (!stokBerhasilDikurangi) {
            return Status.STOK_TIDAK_CUKUP;
        }

        double harga = tiketDAO.getHarga(tiketId);
        double totalHarga = harga * jumlah;

        String sql = "INSERT INTO transaksi (tiket_id, jumlah, total_harga) VALUES (?, ?, ?)";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, tiketId);
            stmt.setInt(2, jumlah);
            stmt.setDouble(3, totalHarga);

            stmt.executeUpdate();
            return Status.SUKSES;

        } catch (SQLException e) {
            System.out.println("Error transaksi : " + e.getMessage());
            return Status.GAGAL;
        }
    }

    public List<Transaksi> getAllTransaksi() {

        List<Transaksi> daftar = new ArrayList<>();

        String sql = "SELECT t.id, t.tiket_id, tk.nama_tiket, t.jumlah, t.total_harga, t.tanggal "
                + "FROM transaksi t JOIN tiket tk ON t.tiket_id = tk.id "
                + "ORDER BY t.id DESC";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {
                Transaksi trx = new Transaksi();
                trx.setId(rs.getInt("id"));
                trx.setTiketId(rs.getInt("tiket_id"));
                trx.setNamaTiket(rs.getString("nama_tiket"));
                trx.setJumlah(rs.getInt("jumlah"));
                trx.setTotalHarga(rs.getDouble("total_harga"));
                trx.setTanggal(rs.getString("tanggal"));
                daftar.add(trx);
            }

        } catch (SQLException e) {
            System.out.println("Error getAllTransaksi : " + e.getMessage());
        }

        return daftar;
    }
}
