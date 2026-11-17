import java.util.*;
public class PharmacyInventory {
    private static volatile int sink;
    static void test(int n) {
        Map<String, Integer> hashMap = new HashMap<>();
        Map<String, Integer> treeMap = new TreeMap<>();
        Map<String, Integer> linkedMap = new LinkedHashMap<>();
        for (int i = n - 1; i >= 0; i--) {
            String code = String.format("MED%06d", i);
            int stock = i + 10;

            hashMap.put(code, stock);
            treeMap.put(code, stock);
            linkedMap.put(code, stock);
        }

        long start = System.nanoTime();
        int sum = 0;

        for (int i = 0; i < 100000; i++) {
            String code = String.format("MED%06d", i % n);
            sum += hashMap.get(code);
        }

        long hashTime = System.nanoTime() - start;
        sink = sum;

        start = System.nanoTime();
        sum = 0;

        for (int i = 0; i < 100000; i++) {
            String code = String.format("MED%06d", i % n);
            sum += treeMap.get(code);
        }

        long treeTime = System.nanoTime() - start;
        sink = sum;

        start = System.nanoTime();
        sum = 0;

        for (int i = 0; i < 100000; i++) {
            String code = String.format("MED%06d", i % n);
            sum += linkedMap.get(code);
        }

        long linkedTime = System.nanoTime() - start;
        sink = sum;

        start = System.nanoTime();
        sum = 0;

        for (int stock : treeMap.values())
            sum += stock;

        long reportTime = System.nanoTime() - start;
        sink = sum;

        System.out.println("\nNumber of medicines: " + n);
        System.out.printf("HashMap lookups: %.3f ms%n",
                hashTime / 1_000_000.0);
        System.out.printf("TreeMap lookups: %.3f ms%n",
                treeTime / 1_000_000.0);
        System.out.printf("LinkedHashMap lookups: %.3f ms%n",
                linkedTime / 1_000_000.0);
        System.out.printf("TreeMap report: %.3f ms%n",
                reportTime / 1_000_000.0);

        System.out.println("First code in TreeMap: "
                + treeMap.keySet().iterator().next());
        System.out.println("First inserted code in LinkedHashMap: "
                + linkedMap.keySet().iterator().next());
    }

    public static void main(String[] args) {
        test(1000);
        test(10000);
        test(50000);
    }
}

