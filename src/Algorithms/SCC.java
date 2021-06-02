package Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class SCC {
	static Stack<Integer> s;
	static int[] disc, low, group;
	static int comp;
	static boolean[] onStack;
	static ArrayList<Integer>[] adj;
	static int t;
	/**
	 * strongly connected components finder
	 * @param adj - adjacency list
	 * @return array representing which strongly connected component each index belongs to
	 */
	public static int[] findSCC(ArrayList<Integer>[] adj) {
		s = new Stack<Integer>();
		disc = new int[adj.length];
		Arrays.fill(disc, -1);
		low = new int[adj.length];
		group = new int[adj.length];
		comp = 1;
		t = 0;
		onStack = new boolean[adj.length];
		SCC.adj = adj;
		for(int i = 0; i < adj.length; i++)
			if(disc[i] == -1)
				assign(i, -2);
		return group;
	}
	/**
	 * find biconnected component.
	 * @param adj - adjacency list
	 * @return biconnected component each idx belongs to
	 */
	public static int[] findBCC(ArrayList<Integer>[] adj) {
		s = new Stack<Integer>();
		disc = new int[adj.length];
		Arrays.fill(disc, -1);
		low = new int[adj.length];
		group = new int[adj.length];
		comp = 0;
		onStack = new boolean[adj.length];
		SCC.adj = adj;
		for(int i = 0; i < adj.length; i++)
			if(disc[i] == -1)
				assign(i, -1);
		return group;
	}
	public static ArrayList<Integer>[] findEdgeBCC(ArrayList<Integer>[] adj) {
		findBCC(adj);
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] edge = new ArrayList[adj.length];
		for(int i = 0; i < adj.length; i++)
			for(int j = 0; j < adj[i].size(); j++)
				if(group[i] != group[adj[i].get(j)]) {
					edge[i].add(adj[i].get(j));
					edge[j].add(adj[j].get(i));
				}
		return edge;
	}
	public static void assign(int idx, int par) {
		disc[idx] = t;
		low[idx] = t;
		t++;
		s.push(idx);
		onStack[idx] = true;
		for(int i = 0; i < adj[idx].size(); i++) {
			int next = adj[idx].get(i);
			if(next == par)
				continue;
			if(disc[next] == -1) {
				if(par == -2)
					assign(next, -2);
				else
					assign(next, idx);
				low[idx] = Math.min(low[idx], low[next]);
			}
			else if(onStack[next])
				low[idx] = Math.min(low[idx], disc[next]);
		}
		if(low[idx] == disc[idx]) {
			while(true) {
				int n = s.pop();
				onStack[n] = false;
				group[n] = comp;
				if(n == idx)
					break;
			}
			comp++;
		}
	}
	public static ArrayList<Integer>[] getCondenseGraph(ArrayList<Integer>[] adj) {
		findSCC(adj);
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] cond = new ArrayList[comp];
		for(int i = 0; i < cond.length; i++)
			cond[i] = new ArrayList<Integer>();
		for(int i = 0; i < adj.length; i++)
			for(int j = 0; j < adj[i].size(); j++)
				if(group[i] != group[adj[i].get(j)])
					cond[group[i] - 1].add(group[adj[i].get(j)] - 1);
		return cond;
	}
}