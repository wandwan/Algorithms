package Algorithms;

import java.util.*;

public class LCA {
	ArrayList<Integer>[] adj;
	ArrayList<Integer> tour;
	ArrayList<Integer> lvl;
	SparseTable st;
	int[] first;
	int root;
	//requires direct graph
	public LCA(ArrayList<Integer>[] adj, int root) {
		this.adj = adj;
		this.root = root;
		first = new int[adj.length];
		tour = new ArrayList<Integer>();
		lvl = new ArrayList<Integer>();
		DFS(root, 0);
		int[] arr = new int[lvl.size()];
		for(int i = 0; i < arr.length; i++)
			arr[i]  = lvl.get(i);
		st = new SparseTable(arr);
	}
	private void DFS(int curr, int lvl) {
		first[curr] = tour.size();
		tour.add(curr);
		this.lvl.add(lvl);
		for(int i = 0; i < adj[curr].size(); i++) {
			DFS(adj[curr].get(i), lvl + 1);
			tour.add(curr);
			this.lvl.add(lvl);
		}
	}
	public int query(int a, int b) {
		return tour.get(st.query(Math.min(first[a], first[b]), Math.max(first[a], first[b]) + 1));
	}
}
