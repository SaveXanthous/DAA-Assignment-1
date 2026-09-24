package Sorts;

import Benchmarks.Metrics;
import Utils.Utils;

public class QuickSort {
    public static void quick_sort(int[] arr, Metrics metrics) {
        if (arr == null || arr.length == 0) return;
        sort(arr, 0, arr.length - 1, metrics);
    }

    private static void sort(int[] arr, int left, int right, Metrics metrics) {
        while (left < right) {
            metrics.enterRecursion();

            int randomIndex = left + (int) (Math.random() * (right - left + 1));
            int pivot = arr[randomIndex];
            int[] equalRange = partition(arr, left, right, pivot, metrics);
            int lessEnd = equalRange[0] - 1;
            int greaterStart = equalRange[1] + 1;

            int leftSize = lessEnd - left + 1;
            int rightSize = right - greaterStart + 1;

            if (leftSize < rightSize) {
                sort(arr, left, lessEnd, metrics);
                left = greaterStart;
            } else {
                sort(arr, greaterStart, right, metrics);
                right = lessEnd;
            }

            metrics.exitRecursion();
        }
    }

    public static int[] partition(int[] arr, int left, int right, int pivot, Metrics metrics) {
        int less = left;
        int current = left;
        int greater = right;

        while (current <= greater) {
            metrics.comparisons++;
            if (arr[current] < pivot) {
                Utils.swap(arr, less++, current++);
            } else {
                metrics.comparisons++;
                if (arr[current] > pivot) {
                    Utils.swap(arr, current, greater--);
                } else {
                    current++;
                }
            }
        }

        return new int[] {less, greater};
    }
}
