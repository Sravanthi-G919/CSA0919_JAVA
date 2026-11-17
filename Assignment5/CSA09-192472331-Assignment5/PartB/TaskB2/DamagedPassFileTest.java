import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DamagedPassFileTest {
    public static void main(String[] args) throws IOException {
        Path file = args.length > 0 ? Path.of(args[0]) : Files.createTempFile("damaged-passes", ".csv");
        if (args.length == 0) {
            Files.writeString(file, "P1,R1,Anu,4,1200.0\nP2,wrong-fields\nP3,R3,Meena,5,not-a-number\n\nP4,R4,Ravi,6,900.0\n");
        }
        PassStore store = new PassStore(file);
        System.out.println("P1: " + java.util.Arrays.toString(store.find("P1")));
        System.out.println("P3: " + java.util.Arrays.toString(store.find("P3")));
        System.out.println("P4: " + java.util.Arrays.toString(store.find("P4")));
        if (args.length == 0) Files.deleteIfExists(file);
    }
}
