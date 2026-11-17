import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginComparison {
    private static String env(String key, String fallback) {
        return System.getenv().getOrDefault(key, fallback);
    }
    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(
            env("DB_URL", "jdbc:mysql://localhost:3306/co5_assignment"),
            env("DB_USER", "root"), env("DB_PASSWORD", ""));
    }
    // Intentionally unsafe. Use ONLY with a disposable local test database.
    static boolean unsafeLogin(Connection con, String id, String pwd) throws SQLException {
        String sql = "SELECT * FROM users WHERE id='" + id + "' AND pwd='" + pwd + "'";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            return rs.next();
        }
    }
    static boolean safeLogin(Connection con, String id, String pwd) throws SQLException {
        String sql = "SELECT * FROM users WHERE id=? AND pwd=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, pwd);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }
    public static void main(String[] args) throws SQLException {
        try (Connection con = connect()) {
            System.out.println("Unsafe accepts crafted input: " + unsafeLogin(con, "nobody' OR '1'='1' -- ", "wrong"));
            System.out.println("PreparedStatement accepts same input: " + safeLogin(con, "nobody' OR '1'='1' -- ", "wrong"));
        }
    }
}
