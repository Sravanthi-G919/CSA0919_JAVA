import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DatabaseMetadataReport {
    private static String env(String k,String d){return System.getenv().getOrDefault(k,d);}
    public static void main(String[] args) throws Exception {
        try(Connection c=DriverManager.getConnection(env("DB_URL","jdbc:mysql://localhost:3306/co5_assignment"),
                env("DB_USER","root"),env("DB_PASSWORD",""))) {
            DatabaseMetaData md=c.getMetaData();
            System.out.println("Database product: "+md.getDatabaseProductName()+" "+md.getDatabaseProductVersion());
            System.out.println("Driver: "+md.getDriverName()+" "+md.getDriverVersion());
            try(ResultSet rs=md.getTables(c.getCatalog(),null,"%",new String[]{"TABLE"})) {
                System.out.println("Tables:");
                while(rs.next()) System.out.println(" - "+rs.getString("TABLE_NAME"));
            }
        }
    }
}
