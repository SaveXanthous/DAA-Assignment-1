package Benchmarks;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import Selects.QuickSelect;
import Sorts.MergeSort;
import Sorts.QuickSort;

public class Benchmark {
    private static final int[] SIZES = {1000, 10000, 100000, 1000000};
    private static final int RUNS = 5;

    public static void main(String[] args) throws IOException {
        FileWriter file = new FileWriter("Results/results.csv");

        file.write("algorithm,input,n,time_ms,comparisons,max_depth\n");

        for (int n : SIZES) {
            testMergeSort(file, n);
            testQuickSort(file, n);
            testQuickSelect(file, n);
        }

        file.close();
    }

    private static void testMergeSort(FileWriter file, int n) throws IOException {
        test(file, "MergeSort", n, "random");
        test(file, "MergeSort", n, "sorted");
        test(file, "MergeSort", n, "duplicates");
    }

    private static void testQuickSort(FileWriter file, int n) throws IOException {
        test(file, "QuickSort", n, "random");
        test(file, "QuickSort", n, "sorted");
        test(file, "QuickSort", n, "duplicates");
    }

    private static void testQuickSelect(FileWriter file, int n) throws IOException {
        test(file, "QuickSelect", n, "random");
        test(file, "QuickSelect", n, "sorted");
        test(file, "QuickSelect", n, "duplicates");
    }

    private static void test(
            FileWriter file,
            String algorithm,
            int n,
            String input
    ) throws IOException {

        int[] original = generateArray(n, input);
        int selectedIndex = n / 2;
        double[] times = new double[RUNS];

        long comparisons = 0;
        int maxDepth = 0;

        for (int i = 0; i < RUNS; i++) {
            int[] arr = original.clone();
            Metrics metrics = new Metrics();

            long start = System.nanoTime();

            if (algorithm.equals("MergeSort")) {
                MergeSort.merge_sort(arr, metrics);
            } else if (algorithm.equals("QuickSort")) {
                QuickSort.quick_sort(arr, metrics);
            } else if (algorithm.equals("QuickSelect")) {
                QuickSelect.quick_select(arr, selectedIndex, metrics);
            }

            long end = System.nanoTime();

            times[i] = (end - start) / 1_000_000.0;

            comparisons = metrics.comparisons;
            maxDepth = metrics.maxDepth;
        }

        Arrays.sort(times);

        double median = times[RUNS / 2];

        file.write(
                algorithm + "," +
                input + "," +
                n + "," +
                median + "," +
                comparisons + "," +
                maxDepth + "\n"
        );
    }

    private static int[] generateArray(int n, String input) {
        int[] arr = new int[n];
        Random random = new Random();

        if (input.equals("random")) {
            for (int i = 0; i < n; i++) {
                arr[i] = random.nextInt();
            }
        }

        if (input.equals("sorted")) {
            for (int i = 0; i < n; i++) {
                arr[i] = i;
            }
        }

        if (input.equals("duplicates")) {
            for (int i = 0; i < n; i++) {
                arr[i] = random.nextInt(10);
            }
        }

        return arr;
    }
}
