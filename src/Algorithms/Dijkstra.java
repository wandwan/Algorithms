package Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.TreeSet;

public class Dijkstra {
	int n;
	ArrayList<Edge>[] adj;
	int[] weights;
	/**
	 * arr contains the shortest path length to all nodes from previous query
	 * Use to get shortest path lengths after getting shortest path tree.
	 */
	Vert[] arr;
	/**
	 * Creates Data Structure to run Dijsktra algorithm on
	 * Adjacency list of graph with all edges having weight 1
	 * @param adj graph with no negative edges
	 */
	public Dijkstra(ArrayList<Edge>[] adj) {
		this(adj, new int[adj.length]);
	}
	/**
	 * Creates Data Structure to run Dijsktra algorithm on
	 * @param adj - adjaceny list representation of graph
	 * @param weights - weights of graph.
	 */
	public Dijkstra(ArrayList<Edge>[] adj, int[] weights) {
		this.adj = adj;
		this.weights = weights;
		n = adj.length;
	}
	/**
	 * Implements Dijkstras to return Single Source Shortest Path lengths to all nodes in the graph
	 * returns Integer.MAX_VALUE for nodes that are unreachable from the source.
	 * @param source The starting Node for single source shortest path.
	 * @return the shortest path distances to all nodes 
	 */
	public long[] getShortest(int source) {
		getShortestTree(source);
		long[] ans = new long[n];
		for(int i = 0; i < n; i++)
			ans[i] = arr[i].val;
		return ans;
	}
	/**
	 * Implements Dijkstras to return the Single Source Shortest Path tree.
	 * @param source The starting Node for single source shortest path.
	 * @return A parent array that represents the shortest path tree
	 */
	public Vert[] getShortestTree(int source) {
		arr = new Vert[n];
		Vert[] par = new Vert[n];
		boolean[] finished = new boolean[n];
		TreeSet<Vert> q = new TreeSet<Vert>();
		for(int i = 0; i < n; i++)
			arr[i] = new Vert(Long.MAX_VALUE, i);
		arr[source].val = 0;
		for(Vert e: arr) q.add(e);
		par[source] = arr[source];
		while(!q.isEmpty()) {
			Vert curr = q.pollFirst();
			finished[curr.id] = true;
			for(Edge e: adj[curr.id]) {
				Vert child = arr[e.end];
				long sum = weights[curr.id] + curr.val + e.val;
				if(sum < child.val && !finished[child.id]) {
					q.remove(child);
					par[child.id] = curr;
					child.val = sum;
					q.add(child);
				}
			}
		}
		return par;
	}
}
class Vert implements Comparable<Vert>{
	long val;
	int id;
	public Vert(long val, int id) {
		this.val = val;
		this.id = id;
	}
	@Override
	public int compareTo(Vert o) {
		if(val != o.val)
			return Long.compare(val, o.val);
		return id - o.id;
	}
	@Override
	public String toString() {
		return "val: " + val;
	}
}


//public static void main(String[] args) {
//	Random r = new Random(System.currentTimeMillis());
//	int size = 20;
//	Graph g = Graph.getRandomWeightedGraph(20, 30);
//	Dijkstra d = new Dijkstra(g.adj);
//	int source = r.nextInt(size);
//	System.out.println(Arrays.toString(d.getShortest(source)));
//	System.out.println(source);
//	System.out.println(g);
//}