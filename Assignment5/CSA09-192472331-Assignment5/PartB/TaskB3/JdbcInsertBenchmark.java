import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcInsertBenchmark {
    private static String env(String k, String d) { return System.getenv().getOrDefault(k, d); }
    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(env("DB_URL","jdbc:mysql://localhost:3306/co5_assignment"),
            env("DB_USER","root"), env("DB_PASSWORD",""));
    }
    private static List<String[]> data(int n, String prefix) {
        List<String[]> list = new ArrayList<>();
        for (int i=1;i<=n;i++) list.add(new String[]{prefix+i,"R"+i,"Student"+i,Integer.toString(i%20),Double.toString(1000+i%500)});
        return list;
    }
    private static void clear(Connection c) throws SQLException {
        try (Statement s=c.createStatement()) { s.executeUpdate("DELETE FROM bus_pass"); }
    }
    private static long statement(Connection c, List<String[]> rows) throws SQLException {
        long start=System.nanoTime();
        try (Statement s=c.createStatement()) {
            for (String[] p:rows) s.executeUpdate("INSERT INTO bus_pass(pass_id,reg_no,name,route_no,amount) VALUES ('"+
                p[0]+"','"+p[1]+"','"+p[2]+"',"+p[3]+","+p[4]+")");
        }
        return (System.nanoTime()-start)/1_000_000;
    }
    private static long prepared(Connection c, List<String[]> rows) throws SQLException {
        long start=System.nanoTime();
        try (PreparedStatement p=c.prepareStatement("INSERT INTO bus_pass(pass_id,reg_no,name,route_no,amount) VALUES (?,?,?,?,?)")) {
            for(String[] x:rows) {
                p.setString(1,x[0]); p.setString(2,x[1]); p.setString(3,x[2]);
                p.setInt(4,Integer.parseInt(x[3])); p.setDouble(5,Double.parseDouble(x[4])); p.executeUpdate();
            }
        }
        return (System.nanoTime()-start)/1_000_000;
    }
    private static long batch(Connection c, List<String[]> rows) throws SQLException {
        long start=System.nanoTime(); boolean old=c.getAutoCommit(); c.setAutoCommit(false);
        try(PreparedStatement p=c.prepareStatement("INSERT INTO bus_pass(pass_id,reg_no,name,route_no,amount) VALUES (?,?,?,?,?)")) {
            int count=0;
            for(String[] x:rows) {
                p.setString(1,x[0]); p.setString(2,x[1]); p.setString(3,x[2]);
                p.setInt(4,Integer.parseInt(x[3])); p.setDouble(5,Double.parseDouble(x[4])); p.addBatch();
                if(++count%1000==0) p.executeBatch();
            }
            if(rows.size()%1000!=0) p.executeBatch();
            c.commit();
        } catch(SQLException e) { c.rollback(); throw e; }
        finally { c.setAutoCommit(old); }
        return (System.nanoTime()-start)/1_000_000;
    }
    public static void main(String[] args) throws SQLException {
        int[] sizes={10,1000,10000,100000};
        try(Connection c=connect()) {
            for(int n:sizes) {
                List<String[]> rows=data(n,"S"+n+"_");
                clear(c); long t1=statement(c,rows);
                clear(c); long t2=prepared(c,rows);
                clear(c); long t3=batch(c,rows);
                System.out.println(n+" rows: Statement="+t1+" ms, PreparedStatement="+t2+" ms, Batch+transaction="+t3+" ms");
            }
            try(Statement s=c.createStatement(); ResultSet rs=s.executeQuery("SELECT * FROM bus_pass LIMIT 1")) {
                ResultSetMetaData m=rs.getMetaData();
                for(int i=1;i<=m.getColumnCount();i++)
                    System.out.println(m.getColumnName(i)+" : "+m.getColumnTypeName(i));
            }
        }
    }
}
