public class Main{
    public static void main(String[]  args){
        int[] arr = new int[] {54,3,6,18,34,23};
        
        // InsertSort.insert_sort(arr);
        MergeSort.merge_sort(arr);

        for(int element: arr){
            System.out.print(element + " ");
        }
    }
}