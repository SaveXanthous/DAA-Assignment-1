package Benchmarks;
public class Metrics {
    public long comparisons = 0;
    public int maxDepth = 0;

    private int currentDepth = 0;

    public long startTime;
    public long endTime;

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        endTime = System.nanoTime();
    }

    public double getTimeMs() {
        return (endTime - startTime) / 1_000_000.0;
    }

    public void enterRecursion() {
        currentDepth++;

        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
    }

    public void exitRecursion() {
        currentDepth--;
    }
}