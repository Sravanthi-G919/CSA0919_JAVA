
public class StringBenchmark {
    private static volatile int sink;

    static String usingString(String[] fields) {
        String result = "";
        for (String field : fields) {
            result = result + field + "|";
        }
        return result;
    }

    static String usingBuilder(String[] fields) {
        StringBuilder result = new StringBuilder();
        for (String field : fields) {
            result.append(field).append("|");
        }
        return result.toString();
    }

    static String usingBuffer(String[] fields) {
        StringBuffer result = new StringBuffer();
        for (String field : fields) {
            result.append(field).append("|");
        }
        return result.toString();
    }

    static long benchmark(int method, String[] fields, int tickets) {
        long start = System.nanoTime();
        int length = 0;

        for (int i = 0; i < tickets; i++) {
            if (method == 1)
                length += usingString(fields).length();
            else if (method == 2)
                length += usingBuilder(fields).length();
            else
                length += usingBuffer(fields).length();
        }

        sink = length;
        return System.nanoTime() - start;
    }

    static void test(int tickets, int fieldCount) {
        String[] fields = new String[fieldCount];

        for (int i = 0; i < fieldCount; i++)
            fields[i] = "Field" + i;

        // Warm-up
        benchmark(1, fields, 2);
        benchmark(2, fields, 2);
        benchmark(3, fields, 2);

        System.out.println("\nTickets: " + tickets
                + ", Fields per ticket: " + fieldCount);

        for (int method = 1; method <= 3; method++) {
            long total = 0;

            for (int repeat = 0; repeat < 5; repeat++)
                total += benchmark(method, fields, tickets);

            System.out.printf("%s: %.3f ms average%n",
                    method == 1 ? "String +" :
                    method == 2 ? "StringBuilder" : "StringBuffer",
                    total / 5_000_000.0);
        }
    }

    public static void main(String[] args) {
        test(1, 5);
        test(100, 50);
        test(250, 100);

        System.out.println("\nConclusion:");
        System.out.println("String is immutable and repeated concatenation "
                + "can create many intermediate strings.");
        System.out.println("StringBuilder is suitable for building a "
                + "ticket within one thread.");
        System.out.println("StringBuffer synchronizes individual operations "
                + "but does not automatically make a whole multi-step "
                + "shared-log operation atomic.");
    }
}

