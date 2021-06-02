package Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ArticulationEdge {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		ArrayList<Integer>[] adj = new ArrayList[n];
		for(int i = 0; i < n; i++)
			adj[i] = new ArrayList<Integer>();
		int m = sc.nextInt();
		for(int i = 0; i < m; i++) {
			sc.nextLine();
			adj[sc.nextInt() - 1].add(sc.nextInt() - 1);
		}
		System.out.println(Arrays.toString(getArticEdge(adj)));
	}
	/**
	 * method to get Articulation Edges from adjacency List representation of graph.
	 * "Articulation edges" are edges that disconnect the graph once removed.
	 * @param adj - adjacency list representation of graph
	 * @return all edge incides based of the arraylist in an int array.
	 */
	public static int[] getArticEdge(ArrayList<Integer>[] adj) {
		int[] disc = new int[adj.length];
		int[] low = new int[adj.length];
		Arrays.fill(low, Integer.MAX_VALUE);
		Integer curr = 1;
		for(int i = 0; i < adj.length; i++) {
			if(disc[i] == 0)
				DFS(adj, disc, low, curr, i);
		}
		int num = 0;
		for(int i = 0; i < adj.length; i++)
			if(disc[i] < low[i])
				num++;
		int[] ans = new int[num];
		int k = 0;
		for(int i = 0; i < adj.length; i++)
			if(disc[i] < low[i])
				ans[k++] = i;
		return ans;
	}
	private static int DFS(ArrayList<Integer>[] adj, int[] disc, int[] low, Integer curr, int node) {
		disc[node] = curr;
		curr++;
		for(int i = 0; i < adj[node].size(); i++) {
			if(disc[adj[node].get(i)] < disc[node] && disc[adj[node].get(i)] != 0)
				low[node] = Math.min(low[node], disc[adj[node].get(i)]);
			else if(disc[adj[node].get(i)] == 0) {
				low[node] = Math.min(low[node], DFS(adj, disc, low, curr, adj[node].get(i)));
			}
		}
		return low[node];
	}
}
