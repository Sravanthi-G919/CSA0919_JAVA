
import java.util.*;

public class ListComparison {
    private static volatile int sink;

    static void test(int n) {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }

        long start = System.nanoTime();
        int sum = 0;

        for (int i = 0; i < 20000; i++)
            sum += arrayList.get((i * 31) % n);

        sink = sum;
        long arrayGet = System.nanoTime() - start;

        start = System.nanoTime();
        sum = 0;

        for (int i = 0; i < 20000; i++)
            sum += linkedList.get((i * 31) % n);

        sink = sum;
        long linkedGet = System.nanoTime() - start;

        start = System.nanoTime();
        sum = 0;
        for (int value : arrayList)
            sum += value;
        sink = sum;
        long arrayIteration = System.nanoTime() - start;

        start = System.nanoTime();
        sum = 0;
        for (int value : linkedList)
            sum += value;
        sink = sum;
        long linkedIteration = System.nanoTime() - start;

        System.out.println("\nList size: " + n);
        System.out.println("ArrayList indexed access: "
                + arrayGet / 1_000_000.0 + " ms");
        System.out.println("LinkedList indexed access: "
                + linkedGet / 1_000_000.0 + " ms");
        System.out.println("ArrayList iteration: "
                + arrayIteration / 1_000_000.0 + " ms");
        System.out.println("LinkedList iteration: "
                + linkedIteration / 1_000_000.0 + " ms");
    }

    public static void main(String[] args) {
        test(1000);
        test(10000);
        test(50000);

        Deque<String> upNext = new ArrayDeque<>();

        upNext.addLast("Song A");
        upNext.addLast("Song B");
        upNext.addFirst("Urgent Song");

        System.out.println("\nUp Next: " + upNext);
        System.out.println("Playing: " + upNext.removeFirst());
        System.out.println("Remaining: " + upNext);
    }
}

