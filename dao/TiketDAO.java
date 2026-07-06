package dao;

import database.DatabaseConnection;
import model.Tiket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TiketDAO {

    public boolean tambah(Tiket tiket) {

        String sql =
                "INSERT INTO tiket (nama_tiket, harga, stok_tiket) VALUES (?, ?, ?)";

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

            System.out.println(
                    "Error tambah tiket : "
                    + e.getMessage()
            );

            return false;
        }
    }
}
}
