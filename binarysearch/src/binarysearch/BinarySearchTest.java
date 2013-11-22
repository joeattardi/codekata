package binarysearch;

import static org.junit.Assert.*;

import org.junit.Test;

public class BinarySearchTest {

	@Test
	public void testIterative() {
		IterativeBinarySearch search = new IterativeBinarySearch();		
		
		assertEquals(-1, search.search(3, new int[] {}));
	    assertEquals(-1, search.search(3, new int[] {1}));
	    assertEquals(0, search.search(1, new int[] {1}));	    
		
	    int[] numbers = {1, 3, 5, 7};
		assertEquals(0, search.search(1, numbers));
		assertEquals(1, search.search(3, numbers));
		assertEquals(2, search.search(5, numbers));
		assertEquals(3, search.search(7, numbers));
		assertEquals(-1, search.search(0, numbers));
		assertEquals(-1, search.search(2, numbers));
		assertEquals(-1, search.search(4, numbers));
		assertEquals(-1, search.search(6, numbers));
		assertEquals(-1, search.search(8, numbers));
	}

	@Test
	public void testRecursive() {
		RecursiveBinarySearch search = new RecursiveBinarySearch();
		
		assertEquals(-1, search.search(3, new int[] {}));
	    assertEquals(-1, search.search(3, new int[] {1}));
	    assertEquals(0, search.search(1, new int[] {1}));	 		
		
		int[] numbers = {1, 3, 5, 7};
		
		assertEquals(0, search.search(1, numbers));
		assertEquals(1, search.search(3, numbers));
		assertEquals(2, search.search(5, numbers));
		assertEquals(3, search.search(7, numbers));
		assertEquals(-1, search.search(0, numbers));
		assertEquals(-1, search.search(2, numbers));
		assertEquals(-1, search.search(4, numbers));
		assertEquals(-1, search.search(6, numbers));
		assertEquals(-1, search.search(8, numbers));
	}
	
}
