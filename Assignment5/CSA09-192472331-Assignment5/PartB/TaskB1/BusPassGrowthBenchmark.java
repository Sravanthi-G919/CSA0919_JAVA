import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BusPassGrowthBenchmark {
    public static void main(String[] args) throws IOException {
        int[] sizes = {1000, 10000, 100000};
        Path file = Path.of("PartB", "test-data", "growth-passes.csv");
        Files.createDirectories(file.getParent());
        for (int n : sizes) {
            StringBuilder data = new StringBuilder();
            for (int i = 1; i <= n; i++)
                data.append("P").append(i).append(",R").append(i).append(",Student").append(i)
                    .append(",").append(i % 20).append(",1000.0\n");
            Files.writeString(file, data.toString());
            PassStore store = new PassStore(file);
            long start = System.nanoTime();
            String[] result = store.find("P" + n);
            long ms = (System.nanoTime() - start) / 1_000_000;
            System.out.println(n + " records: found=" + (result != null) + ", elapsed=" + ms + " ms");
        }
    }
}
