public class MergeSort {
    public static void merge_sort(int[] arr){
        int length = arr.length;
        split(arr, arr.clone(), 0, length - 1);
    }

    private static void split(int[] arr, int[] buffer,int left, int right){
        if(left >= right){
            return;    
        }
        
        int mid = left + (right - left) / 2;

        split(arr, buffer, left, mid);

        split(arr, buffer, mid + 1, right);

        merge(arr, buffer, left, right, mid);
    }

    private static void merge(int[] arr, int[] buffer,int left, int right, int mid){
        int i = left;
        int j = mid + 1;
        int z = left;
        
        while(i != mid + 1 && j != right + 1){
            if(arr[i] <= arr[j]){
                buffer[z++] = arr[i++];
            } else {
                buffer[z++] = arr[j++];
            }
        }

        while(i != mid + 1){
            buffer[z++] = arr[i++];
        }

        while(j != right + 1){
            buffer[z++] = arr[j++];
        }
       
        for(i = left; i <= right; i++){
            arr[i] = buffer[i];
        }
    }
}
