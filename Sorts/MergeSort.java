package Sorts;

import Benchmarks.Metrics;
import Sorts.InsertSort;

public class MergeSort {

    public static void merge_sort(int[] arr, Metrics metrics) {

        int length = arr.length;

        if (length <= 15) {
            InsertSort.insert_sort(arr, metrics);
            return;
        }

        split(arr, arr.clone(), 0, length - 1, metrics);
    }

    private static void split(
            int[] arr,
            int[] buffer,
            int left,
            int right,
            Metrics metrics
    ) {

        if (left >= right) {
            return;
        }

        metrics.enterRecursion();

        int mid = left + (right - left) / 2;

        split(arr, buffer, left, mid, metrics);

        split(arr, buffer, mid + 1, right, metrics);

        merge(arr, buffer, left, right, mid, metrics);

        metrics.exitRecursion();
    }

    private static void merge(
            int[] arr,
            int[] buffer,
            int left,
            int right,
            int mid,
            Metrics metrics
    ) {

        int i = left;
        int j = mid + 1;
        int z = left;

        while (i <= mid && j <= right) {

            metrics.comparisons++;

            if (arr[i] <= arr[j]) {
                buffer[z++] = arr[i++];
            } else {
                buffer[z++] = arr[j++];
            }
        }

        while (i <= mid) {
            buffer[z++] = arr[i++];
        }

        while (j <= right) {
            buffer[z++] = arr[j++];
        }

        for (i = left; i <= right; i++) {
            arr[i] = buffer[i];
        }
    }
}
