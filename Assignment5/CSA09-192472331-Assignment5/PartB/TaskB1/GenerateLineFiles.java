import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class GenerateLineFiles {
    public static void main(String[] args) throws IOException {
        long[] sizesMB = {1, 10, 50};
        Path dir = Path.of("PartB", "test-data");
        Files.createDirectories(dir);
        for (long mb : sizesMB) {
            Path file = dir.resolve("attendance-" + mb + "mb.txt");
            long target = mb * 1024L * 1024L;
            long written = 0;
            try (BufferedWriter w = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
                String line = "192472331,ATTENDANCE,Present\n";
                while (written + line.getBytes(StandardCharsets.UTF_8).length <= target) {
                    w.write(line);
                    written += line.getBytes(StandardCharsets.UTF_8).length;
                }
                while (written < target) { w.write('x'); written++; }
            }
            System.out.println(file + " " + Files.size(file) + " bytes");
        }
    }
}
