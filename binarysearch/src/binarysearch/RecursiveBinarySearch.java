package binarysearch;

public class RecursiveBinarySearch {
	public int search(int number, int[] search) {
		if (search.length == 0) return -1;
		return search(number, search, 0, search.length - 1);
	}
	
	public int search(int number, int[] search, int start, int end) {
		if (start >= end) {
			return (search[start] == number) ? start : -1;
		} else {
			int midpoint = start + ((end - start) / 2);
			if (search[midpoint] > number) {
				return search(number, search, start, midpoint - 1);
			} else if (search[midpoint] < number) {
				return search(number, search, midpoint + 1, end);
			} else {
				return midpoint;
			}
				
		}
	}
}
