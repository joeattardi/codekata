package binarysearch;

public class IterativeBinarySearch {
	public int search(int number, int[] search) {
        int start = 0;
        int end = search.length - 1;        
        
        while (start <= end) {
            int midpoint = start + ((end - start) / 2);
        
            if (search[midpoint] > number) {
                end = midpoint - 1;
            } else if (search[midpoint] < number) {
                start = midpoint + 1;
            } else {
                return midpoint;
            }        
        }

        return -1;
    }
}
