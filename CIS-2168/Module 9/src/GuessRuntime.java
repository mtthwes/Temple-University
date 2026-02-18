import java.util.Random;

public class GuessRuntime {
    public static void main(String[] args) {
        int[] sizes = {64, 128, 256, 512, 1024};
        Stats stats = new Stats();

        System.out.println("First Sort");
        for (int n : sizes) {
            Integer[] arr = randomArray(n);
            Sort1.sort(arr, stats);
            report(n, stats);
        }

        System.out.println("\nSecond Sort");
        for (int n : sizes) {
            Integer[] arr = randomArray(n);
            Sort2.sort(arr, stats);
            report(n, stats);
        }

        System.out.println("\nThird Sort");
        for (int n : sizes) {
            Integer[] arr = randomArray(n);
            Sort3.sort(arr, stats);
            report(n, stats);
        }
    }

    private static Integer[] randomArray(int n) {
        Random r = new Random();
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = r.nextInt(n * 10);
        }
        return a;
    }

    private static void report(int n, Stats s) {
        System.out.printf("  %4d elements: comps=%6d, exchs=%6d, time=%8d ns%n",
                n, s.comparisons, s.exchanges, s.runtime);
    }
}
