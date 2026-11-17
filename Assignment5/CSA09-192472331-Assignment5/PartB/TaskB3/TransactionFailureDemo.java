import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionFailureDemo {
    private static String env(String k,String d){return System.getenv().getOrDefault(k,d);}
    public static void main(String[] args) throws SQLException {
        try(Connection c=DriverManager.getConnection(env("DB_URL","jdbc:mysql://localhost:3306/co5_assignment"),
                env("DB_USER","root"),env("DB_PASSWORD",""))) {
            try(Statement s=c.createStatement()){s.executeUpdate("DELETE FROM bus_pass");}
            c.setAutoCommit(false);
            try(PreparedStatement p=c.prepareStatement("INSERT INTO bus_pass(pass_id,reg_no,name,route_no,amount) VALUES (?,?,?,?,?)")) {
                for(int i=1;i<=10;i++) {
                    p.setString(1,"TX"+i); p.setString(2,"R"+i); p.setString(3,"Test"+i);
                    p.setInt(4,1);
                    p.setDouble(5,i==6 ? -10.0 : 100.0);
                    if(i==6) throw new SQLException("Forced failure at row 6");
                    p.executeUpdate();
                }
                c.commit();
            } catch(SQLException e) {
                c.rollback();
                System.out.println("Failure observed; transaction rolled back: "+e.getMessage());
            } finally { c.setAutoCommit(true); }
            try(Statement s=c.createStatement(); var rs=s.executeQuery("SELECT COUNT(*) FROM bus_pass")) {
                rs.next(); System.out.println("Rows remaining after rollback: "+rs.getInt(1));
            }
        }
    }
}
