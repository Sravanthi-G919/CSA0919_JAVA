import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.FileInputStream;

public class BufferedLineCounter {
    public static long countUnbuffered(Path path) throws IOException {
        long lines = 0;
        try (FileInputStream in = new FileInputStream(path.toFile())) {
            int b;
            while ((b = in.read()) != -1) if (b == '\n') lines++;
        }
        return lines;
    }
    public static long countBuffered(Path path) throws IOException {
        long lines = 0;
        try (BufferedInputStream in = new BufferedInputStream(Files.newInputStream(path), 8192)) {
            int b;
            while ((b = in.read()) != -1) if (b == '\n') lines++;
        }
        return lines;
    }
    private static long timed(boolean buffered, Path path) throws IOException {
        long start = System.nanoTime();
        long count = buffered ? countBuffered(path) : countUnbuffered(path);
        long ms = (System.nanoTime() - start) / 1_000_000;
        System.out.println((buffered ? "Buffered" : "Unbuffered") + ": " + count + " newline bytes, " + ms + " ms");
        return ms;
    }
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Usage: java BufferedLineCounter <file-path>");
            return;
        }
        Path path = Path.of(args[0]);
        timed(false, path);
        timed(true, path);
    }
}
