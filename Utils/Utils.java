package Utils;
public class Utils {
    public static void swap(int[] arr, int i, int j){
        int hold_int = arr[i];
        arr[i] = arr[j];
        arr[j] = hold_int;
    }
}
