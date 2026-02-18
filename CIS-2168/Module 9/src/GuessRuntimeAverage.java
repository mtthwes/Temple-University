import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GuessRuntimeAverage {
    private static final int RUNS = 10000;
    private static final int[] SIZES = {64, 128, 256, 512, 1024};

    public static void main(String[] args) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("averages.csv"))) {
            writer.write("Sort,N,Avg comps,Avg exchs,Avg time (ns)");
            writer.newLine();

            runAverage("First Sort",  Sort1::sort, writer);
            runAverage("Second Sort", Sort3::sort, writer);
            runAverage("Third Sort",  Sort2::sort, writer);

            System.out.println("Wrote averages.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runAverage(String sortName,
                                   SortFunction sorter,
                                   BufferedWriter writer) throws IOException {
        Stats stats = new Stats();

        for (int n : SIZES) {
            long totalComp = 0;
            long totalExch = 0;
            long totalTime = 0;

            for (int i = 0; i < RUNS; i++) {
                Integer[] arr = randomArray(n);
                stats.reset();
                sorter.sort(arr, stats);
                totalComp += stats.comparisons;
                totalExch += stats.exchanges;
                totalTime += stats.runtime;
            }

            long avgComp = totalComp / RUNS;
            long avgExch = totalExch / RUNS;
            long avgTime = totalTime / RUNS;

            writer.write(String.join(",",
                    sortName,
                    Integer.toString(n),
                    Long.toString(avgComp),
                    Long.toString(avgExch),
                    Long.toString(avgTime)
            ));
            writer.newLine();
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

    @FunctionalInterface
    interface SortFunction {
        <T extends Comparable<? super T>> T[] sort(T[] array, Stats stats);
    }
}
