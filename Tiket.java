import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Tiket {
    // method konek database cik
    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/db_nextfly";
        return DriverManager.getConnection(url, "root", "");
    }

    // tambah tiketnya mas
    public static boolean tambah(String namaTiket, double harga, int stok) {
        String sql = "INSERT INTO tiket (nama_tiket, harga, stok_tiket) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, namaTiket);
            stmt.setDouble(2, harga);
            stmt.setInt(3, stok);

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error tambah: " + e.getMessage());
            return false;
        }
    }

    // showcase tiket loh ya
    public static void tampilkanSemua() {
        String sql = "SELECT * FROM tiket";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nama = rs.getString("nama_tiket");
                double harga = rs.getDouble("harga");
                int stok = rs.getInt("stok_tiket");

                System.out.println(id + " | " + nama + " | Rp" + harga + " | Stok: " + stok);
            }

        } catch (SQLException e) {
            System.out.println("Error tampil: " + e.getMessage());
        }
    }

    // update dulu mas
    public static boolean update(int id, String namaTiket, double harga, int stok) {
        String sql = "UPDATE tiket SET nama_tiket = ?, harga = ?, stok_tiket = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, namaTiket);
            stmt.setDouble(2, harga);
            stmt.setInt(3, stok);
            stmt.setInt(4, id);

            int baris = stmt.executeUpdate();
            return baris > 0; // true kalau ada baris yang diupdate

        } catch (SQLException e) {
            System.out.println("Error update: " + e.getMessage());
            return false;
        }
    }
    
    // hapus doksli
    public static boolean hapus(int id) {
        String sql = "DELETE FROM tiket WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int baris = stmt.executeUpdate();
            return baris > 0;

        } catch (SQLException e) {
            System.out.println("Error hapus: " + e.getMessage());
            return false;
        }
    }
    // cek dulu harganya mas
    public static double getHarga(int id) {
        String sql = "SELECT harga FROM tiket WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("harga");
            }

        } catch (SQLException e) {
            System.out.println("Error getHarga: " + e.getMessage());
        }
        return 0;
    }

    //cek stoknya 
    public static int getStok(int id) {
        String sql = "SELECT stok_tiket FROM tiket WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("stok_tiket");
            }

        } catch (SQLException e) {
            System.out.println("Error getStok: " + e.getMessage());
        }
        return 0;
    }

    // kurangi dulu stoknya jangan kasih scalper
    public static boolean kurangiStok(int id, int jumlah) {
        String sql = "UPDATE tiket SET stok_tiket = stok_tiket - ? WHERE id = ? AND stok_tiket >= ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, jumlah);
            stmt.setInt(2, id);
            stmt.setInt(3, jumlah);

            int baris = stmt.executeUpdate();
            return baris > 0; // kalau 0, berarti stok gak cukup yang artinya error

        } catch (SQLException e) {
            System.out.println("Error kurangiStok: " + e.getMessage());
            return false;
        }
    }
}



