package dao;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

        boolean stokBerhasilDikurangi =
                tiketDAO.kurangiStok(tiketId, jumlah);

        if (!stokBerhasilDikurangi) {
            return Status.STOK_TIDAK_CUKUP;
        }

        double harga = tiketDAO.getHarga(tiketId);

        double totalHarga = harga * jumlah;

        String sql =
                "INSERT INTO transaksi " +
                "(tiket_id, jumlah, total_harga) " +
                "VALUES (?, ?, ?)";

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

            System.out.println(
                    "Error transaksi : "
                    + e.getMessage()
            );

            return Status.GAGAL;
        }
    }
}
