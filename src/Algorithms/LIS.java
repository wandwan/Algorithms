package Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class LIS {
	//returns lexicographically minimum indexes of LIS
	public static ArrayList<Integer> getLIS(int[] arr) {
		ArrayList<Integer> max = new ArrayList<Integer>();
		ArrayList<Integer> pred = new ArrayList<Integer>();
		pred.add(-1);
		max.add(arr[0]);
		pred.add(0);
		for(int i = 1; i < arr.length; i++) {
			int idx = Collections.binarySearch(max, arr[i]);
			if(idx < 0)
				idx = -idx - 1;
			if(idx == max.size()) {
				max.add(arr[i]);
				pred.add(i);
			}
			else {
				max.set(idx, arr[i]);
				pred.set(idx + 1, i);
			}
		}
		pred.remove(0);
		return pred;
	}
}
