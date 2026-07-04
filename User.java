import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    //buat konek database cik
    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/db_nextfly";
        return DriverManager.getConnection(url, "root", "");
    }

    // buat cek login para member
    public static boolean login(String username, String password) {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true kalau ada 1 baris kode yang ok menurut database;

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}