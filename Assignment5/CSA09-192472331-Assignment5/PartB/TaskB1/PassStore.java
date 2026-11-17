import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class PassStore {
    private final Path file;
    public PassStore(Path file) { this.file = file; }

    // Format: passId,regNo,name,route,amount. Fields must not contain commas in this simple demo.
    public void add(String passId, String regNo, String name, int route, double amount) throws IOException {
        if (passId == null || passId.isBlank() || regNo == null || regNo.isBlank()
                || name == null || name.isBlank() || route < 0 || amount < 0) {
            throw new IllegalArgumentException("Invalid pass fields");
        }
        Path parent = file.toAbsolutePath().getParent();
        if (parent != null) Files.createDirectories(parent);
        try (BufferedWriter w = Files.newBufferedWriter(file, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            w.write(String.join(",", passId, regNo, name, Integer.toString(route), Double.toString(amount)));
            w.newLine();
        }
    }

    /** Returns five fields, or null if not found. Malformed rows are skipped. */
    public String[] find(String passId) throws IOException {
        if (!Files.exists(file)) return null;
        try (BufferedReader r = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] fields = line.split(",", -1);
                if (fields.length != 5) {
                    System.err.println("Skipping damaged line: " + line);
                    continue;
                }
                try {
                    Integer.parseInt(fields[3]);
                    Double.parseDouble(fields[4]);
                } catch (NumberFormatException ex) {
                    System.err.println("Skipping line with invalid number: " + line);
                    continue;
                }
                if (fields[0].equals(passId)) return fields;
            }
        }
        return null;
    }

    public void listByRoute(int route) throws IOException {
        if (!Files.exists(file)) {
            System.out.println("Pass file does not exist.");
            return;
        }
        try (BufferedReader r = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] fields = line.split(",", -1);
                if (fields.length != 5) {
                    System.err.println("Skipping damaged line: " + line);
                    continue;
                }
                try {
                    if (Integer.parseInt(fields[3]) == route) System.out.println(Arrays.toString(fields));
                    Double.parseDouble(fields[4]);
                } catch (NumberFormatException ex) {
                    System.err.println("Skipping line with invalid number: " + line);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Path f = Files.createTempFile("passes", ".csv");
        PassStore store = new PassStore(f);
        store.add("P1001", "192311001", "Arun", 12, 4500);
        Files.writeString(f, "P1002,DAMAGED LINE\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        store.add("P1003", "192311003", "Divya", 7, 3800);
        System.out.println(Arrays.toString(store.find("P1003")));
        System.out.println(Arrays.toString(store.find("P1002")));
        store.listByRoute(12);
        Files.deleteIfExists(f);
    }
}
