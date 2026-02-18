public class Stats {
    public long comparisons = 0;
    public long exchanges   = 0;
    public long runtime     = 0; 

    public void reset() {
        comparisons = exchanges = runtime = 0;
    }
}
