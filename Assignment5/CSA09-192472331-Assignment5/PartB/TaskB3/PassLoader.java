import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PassLoader {
    /** Loads passes in batches of 1,000 within one transaction. */
    public static int loadAll(Connection con, List<String[]> passes) throws SQLException {
        String sql = "INSERT INTO bus_pass(pass_id, reg_no, name, route_no, amount) VALUES (?, ?, ?, ?, ?)";
        boolean oldAutoCommit = con.getAutoCommit();
        con.setAutoCommit(false);
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            int n = 0;
            int batchCount = 0;
            for (String[] p : passes) {
                if (p == null || p.length != 5) throw new SQLException("Invalid pass: wrong field count");
                ps.setString(1, p[0]);
                ps.setString(2, p[1]);
                ps.setString(3, p[2]);
                ps.setInt(4, Integer.parseInt(p[3]));
                ps.setDouble(5, Double.parseDouble(p[4]));
                ps.addBatch();
                n++;
                batchCount++;
                if (batchCount == 1000) {
                    ps.executeBatch();
                    batchCount = 0;
                }
            }
            if (batchCount > 0) ps.executeBatch();
            con.commit();
            return n;
        } catch (SQLException | RuntimeException e) {
            try { con.rollback(); } catch (SQLException rollbackError) { e.addSuppressed(rollbackError); }
            throw e;
        } finally {
            con.setAutoCommit(oldAutoCommit);
        }
    }
}
