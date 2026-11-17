import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PassFileMigration {
    private static String env(String k,String d){return System.getenv().getOrDefault(k,d);}
    public static void main(String[] args) throws Exception {
        if(args.length==0){System.out.println("Usage: java PassFileMigration <pass-file.csv>");return;}
        Path path=Path.of(args[0]);
        List<String[]> valid=new ArrayList<>();
        List<String> failures=new ArrayList<>();
        try(BufferedReader r=Files.newBufferedReader(path,StandardCharsets.UTF_8)) {
            String line; int lineNo=0;
            while((line=r.readLine())!=null) {
                lineNo++;
                String[] p=line.split(",",-1);
                try {
                    if(p.length!=5) throw new IllegalArgumentException("expected 5 fields");
                    if(p[0].isBlank()||p[1].isBlank()||p[2].isBlank()) throw new IllegalArgumentException("empty required field");
                    Integer.parseInt(p[3]); double amount=Double.parseDouble(p[4]);
                    if(amount<0) throw new IllegalArgumentException("negative amount");
                    valid.add(p);
                } catch(RuntimeException e) { failures.add("line "+lineNo+": "+e.getMessage()+" | "+line); }
            }
        }
        try(Connection c=DriverManager.getConnection(env("DB_URL","jdbc:mysql://localhost:3306/co5_assignment"),
                env("DB_USER","root"),env("DB_PASSWORD",""))) {
            int loaded=PassLoader.loadAll(c,valid);
            System.out.println("Valid records loaded: "+loaded);
        }
        Path report=Path.of("migration-failures.txt");
        Files.write(report,failures,StandardCharsets.UTF_8);
        System.out.println("Invalid records: "+failures.size()+"; report: "+report.toAbsolutePath());
        for(String failure:failures) System.out.println(failure);
    }
}
