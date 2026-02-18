import java.util.Arrays;

public class Sort1 {
    public static <T extends Comparable<? super T>> T[] sort(T[] table, Stats stats) {
        @SuppressWarnings("unchecked")
        T[] a = (T[]) Arrays.copyOf(table, table.length);
        stats.reset();
        long start = System.nanoTime();

        for (int nextPos = 1; nextPos < a.length; nextPos++) {
            T nextVal = a[nextPos];
            int j = nextPos - 1;
            stats.comparisons++;
            while (j >= 0 && nextVal.compareTo(a[j]) < 0) {
                stats.comparisons++;
                a[j + 1] = a[j];
                stats.exchanges++;
                j--;
            }
            a[j + 1] = nextVal;
            if (j + 1 != nextPos) stats.exchanges++;
        }

        stats.runtime = System.nanoTime() - start;
        return a;
    }
}
