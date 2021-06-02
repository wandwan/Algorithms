package Algorithms;

import java.util.Arrays;

public class SparseTable {
	int[][] table;
	int[] arr;
	int[] log;
	int n;
	/**
	 * range minimum query with no updates in O(1) and nlogn preprocess
	 */
	public SparseTable(int[] arr) {
		log = new int[arr.length + 1];
		this.arr = arr;
		n = arr.length;
		for(int i = 2; i <= n; i++)
			log[i] = log[i >> 1] + 1;
		table = new int[log[n] + 1][n];
		for(int i = 0; i < n; i++)
			table[0][i] = arr[i];
		for(int k = 1; 1 << k < n; k++)
			//remember table[i][k] = [i, i + 2 ^ k), k is zero based
			for(int i = 0; i + (1 << k) <= n; i++)
				table[k][i] = Math.min(table[k - 1][i], table[k - 1][i + (1 << k - 1)]);
	}
	//RMQ for [l, r)
	public int query(int l, int r) {
		return Math.min(table[log[r - l]][l], table[log[r - l]][r - (1 << log[r - l]) + 1]);
	}
}
