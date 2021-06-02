package Algorithms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class DSU {
	int[] par;
	int[] depth;
	int[] setSize;
	int sets;
	/**
	 * creates Disjoint Set Union (Union find) data structure
	 * @param n - number of elements
	 */
	public DSU(int n) {
		this(new int[n]);
		for(int i = 0; i < n; i++)
			par[i] = i;
	}
	/**
	 * Creates Disjoint set Union (union find) data structure with specified parent array
	 * used for disk storage and retrieval of union find structure
	 * @param par - parent array
	 */
	public DSU(int[] par) {
		this.par = par;
		depth = new int[par.length];
		setSize = new int[par.length];
		sets = par.length;
		for(int i = 0; i < par.length; i++)
			setSize[find(i)]++;
	}
	/**
	 * merges set a and b in O(1) effectively
	 * @param a - first group to merge
	 * @param b - second group to merge
	 */
	public void merge(int a, int b) {
		a = find(a);
		b = find(b);
		if(a == b)
			return;
		if(depth[b] > depth[a]) {
			merge(b,a);
			return;
		}
		par[b] = a;
		if(depth[b] == depth[a])
			depth[a]++;
		setSize[a] += setSize[b];
		sets--;
	}
	/**
	 * finds parent of n
	 * @param n - node to find parent of
	 * @return parent of n in O(1) effectively
	 */
	public int find(int n) {
		if(par[n] != n)
			par[n] = find(par[n]);
		return par[n];
	}
}