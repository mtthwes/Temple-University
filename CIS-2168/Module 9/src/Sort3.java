import java.util.Arrays;

public class Sort3 {
    public static <T extends Comparable<? super T>> T[] sort(T[] table, Stats stats) {
        @SuppressWarnings("unchecked")
        T[] a = (T[]) Arrays.copyOf(table, table.length);
        stats.reset();
        long start = System.nanoTime();

        int gap = a.length / 2;
        while (gap > 0) {
            for (int nextPos = gap; nextPos < a.length; nextPos++) {
                T nextVal = a[nextPos];
                int pos = nextPos;
                stats.comparisons++;
                while (pos >= gap && nextVal.compareTo(a[pos - gap]) < 0) {
                    a[pos] = a[pos - gap];
                    stats.exchanges++;
                    pos -= gap;
                    stats.comparisons++;
                }
                a[pos] = nextVal;
                if (pos != nextPos) {
                    stats.exchanges++;
                }
            }
            if (gap == 2) {
                gap = 1;
            } else {
                gap = (int) (gap / 2.2);
            }
        }

        stats.runtime = System.nanoTime() - start;
        return a;
    }
}
