package dao;

import database.DatabaseConnection;
import model.Tiket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class TiketDAO {

    public boolean tambah(Tiket tiket) {

        String sql = "INSERT INTO tiket (nama_tiket, harga, stok_tiket) VALUES (?, ?, ?)";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, tiket.getNamaTiket());
            stmt.setDouble(2, tiket.getHarga());
            stmt.setInt(3, tiket.getStokTiket());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error tambah tiket : " + e.getMessage());
            return false;
        }
    }

    public List<Tiket> getAllTiket() {

        List<Tiket> daftarTiket = new ArrayList<>();
        String sql = "SELECT * FROM tiket ORDER BY id";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {
                Tiket tiket = new Tiket();
                tiket.setId(rs.getInt("id"));
                tiket.setNamaTiket(rs.getString("nama_tiket"));
                tiket.setHarga(rs.getDouble("harga"));
                tiket.setStokTiket(rs.getInt("stok_tiket"));
                daftarTiket.add(tiket);
            }

        } catch (SQLException e) {
            System.out.println("Error getAllTiket : " + e.getMessage());
        }

        return daftarTiket;
    }

    public Tiket getById(int id) {

        String sql = "SELECT * FROM tiket WHERE id = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Tiket tiket = new Tiket();
                tiket.setId(rs.getInt("id"));
                tiket.setNamaTiket(rs.getString("nama_tiket"));
                tiket.setHarga(rs.getDouble("harga"));
                tiket.setStokTiket(rs.getInt("stok_tiket"));
                return tiket;
            }

        } catch (SQLException e) {
            System.out.println("Error getById : " + e.getMessage());
        }

        return null;
    }

    public boolean update(Tiket tiket) {

        String sql = "UPDATE tiket SET nama_tiket = ?, harga = ?, stok_tiket = ? WHERE id = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, tiket.getNamaTiket());
            stmt.setDouble(2, tiket.getHarga());
            stmt.setInt(3, tiket.getStokTiket());
            stmt.setInt(4, tiket.getId());

            int baris = stmt.executeUpdate();
            return baris > 0;

        } catch (SQLException e) {
            System.out.println("Error update tiket : " + e.getMessage());
            return false;
        }
    }

    public boolean hapus(int id) {

        String sql = "DELETE FROM tiket WHERE id = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);
            int baris = stmt.executeUpdate();
            return baris > 0;

        } catch (SQLException e) {
            System.out.println("Error hapus tiket : " + e.getMessage());
            return false;
        }
    }

    public double getHarga(int id) {

        String sql = "SELECT harga FROM tiket WHERE id = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("harga");
            }

        } catch (SQLException e) {
            System.out.println("Error getHarga : " + e.getMessage());
        }

        return 0;
    }

    public int getStok(int id) {

        String sql = "SELECT stok_tiket FROM tiket WHERE id = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("stok_tiket");
            }

        } catch (SQLException e) {
            System.out.println("Error getStok : " + e.getMessage());
        }

        return 0;
    }

    public boolean kurangiStok(int id, int jumlah) {

        String sql = "UPDATE tiket SET stok_tiket = stok_tiket - ? "
                + "WHERE id = ? AND stok_tiket >= ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, jumlah);
            stmt.setInt(2, id);
            stmt.setInt(3, jumlah);

            int baris = stmt.executeUpdate();
            return baris > 0;

        } catch (SQLException e) {
            System.out.println("Error kurangiStok : " + e.getMessage());
            return false;
        }
    }
}
