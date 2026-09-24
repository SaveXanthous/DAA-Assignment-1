package Selects;

import Benchmarks.Metrics;
import Sorts.QuickSort;
public class QuickSelect {

    public static int quick_select(int[] arr, int k, Metrics metrics) {
        if (arr == null || arr.length == 0 || k < 0 || k >= arr.length) {
            throw new IllegalArgumentException("Некорректные данные или k");
        }

        int[] working = arr.clone();
        int left = 0;
        int right = working.length - 1;
        int depth = 0;

        while (left <= right) {
            depth++;
            metrics.enterRecursion();
            int randomIndex = left + (int) (Math.random() * (right - left + 1));
            int pivot = working[randomIndex];
            int[] equalRange = QuickSort.partition(working, left, right, pivot, metrics);
            metrics.exitRecursion();

            if (depth > metrics.maxDepth) {
                metrics.maxDepth = depth;
            }

            if (k < equalRange[0]) {
                right = equalRange[0] - 1;
            } else if (k > equalRange[1]) {
                left = equalRange[1] + 1;
            } else {
                return working[k];
            }
        }

        throw new IllegalStateException("QuickSelect не нашёл элемент");
    }
}
