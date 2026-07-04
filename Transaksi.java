import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Transaksi {
    // koneksi database wak
    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/db_nextfly";
        return DriverManager.getConnection(url, "root", "");
    }

    // hasil proses transaksi, intinya warning atau pesan
    public enum Status {
        SUKSES,
        STOK_TIDAK_CUKUP,
        GAGAL
    }
    // status ini
    public static Status beli(int tiketId, int jumlah) {
        // cek status 
        int stokSekarang = Tiket.getStok(tiketId);

        if (jumlah > stokSekarang) {
            return Status.STOK_TIDAK_CUKUP;
        }

        // stok dikurangkan + cek stok
        boolean stokBerhasilDikurangi = Tiket.kurangiStok(tiketId, jumlah);

        if (!stokBerhasilDikurangi) {
            return Status.STOK_TIDAK_CUKUP;
        }

        // pencatatan doksli
        double harga = Tiket.getHarga(tiketId);
        double total = harga * jumlah;

        String sql = "INSERT INTO transaksi (tiket_id, jumlah, total_harga) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, tiketId);
            stmt.setInt(2, jumlah);
            stmt.setDouble(3, total);

            stmt.executeUpdate();
            return Status.SUKSES;

        } catch (SQLException e) {
            System.out.println("Error catat transaksi: " + e.getMessage());
            return Status.GAGAL;
        }
    }
}