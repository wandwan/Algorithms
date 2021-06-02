
package Algorithms;
import java.util.*;
import java.io.*;

public class RunDownSort {
//	public static void main(String[] args) {
//		Scanner sc = new Scanner(System.in);
//		int n = sc.nextInt();
//		int[] arr = new int[n];
//		for(int i = 0; i < n; i++)
//			arr[i] = sc.nextInt();
//		new RunDownSort().runItDownSort(arr);
//	}
	/**
	 * weird sorting algorithm for array
	 * @param arr
	 */
	public void runItDownSort(int[] arr) {
		ArrayList<Stack<Integer>> runs = new ArrayList<Stack<Integer>>();
		runs.add(new Stack<Integer>());
		Stack<Integer> prev = runs.get(0);
		prev.add(arr[0]);
		for(int i = 1; i < arr.length; i++) {
			if(arr[i] > prev.peek()) {
				prev = new Stack<Integer>();
				runs.add(prev);
			}
			prev.add(arr[i]);
		}
//		System.out.println(runs);
	}
}
