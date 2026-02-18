import java.util.Arrays;

public class Sort2 {
    public static <T extends Comparable<? super T>> T[] sort(T[] table, Stats stats) {
        @SuppressWarnings("unchecked")
        T[] a = (T[]) Arrays.copyOf(table, table.length);
        stats.reset();
        long start = System.nanoTime();

        quickSort(a, 0, a.length - 1, stats);

        stats.runtime = System.nanoTime() - start;
        return a;
    }

    private static <T extends Comparable<? super T>>
    void quickSort(T[] a, int low, int high, Stats stats) {
        if (low < high) {
            int p = partition(a, low, high, stats);
            quickSort(a, low, p - 1, stats);
            quickSort(a, p + 1, high, stats);
        }
    }

    private static <T extends Comparable<? super T>>
    int partition(T[] a, int low, int high, Stats stats) {
        T pivot = a[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            stats.comparisons++;
            if (a[j].compareTo(pivot) <= 0) {
                i++;
                swap(a, i, j, stats);
            }
        }
        swap(a, i + 1, high, stats);
        return i + 1;
    }

    private static <T> void swap(T[] a, int i, int j, Stats stats) {
        T tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
        stats.exchanges++;
    }
}
