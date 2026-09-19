public class InsertSort {
    public static void insert_sort(int[] arr){
        for (int i = 1; i < arr.length; i++){
            int j = i;        
            while(j != 0 && arr[j-1] > arr[j]){
                Utils.swap(arr,j-1,j);
                j--;
            }
        }
    }
}