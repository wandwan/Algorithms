package Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Graph {
	ArrayList<Edge>[] adj;
	int[] weights;
	public Graph(ArrayList<Edge>[] adj) {
		this(adj, new int[adj.length]);
	}
	public Graph(ArrayList<Edge>[] adj, int[] weights) {
		this.adj = adj;
		this.weights = weights;
	}
	public static Graph getRandomWeightedGraph(int nodes, int edges) {
		Random r = new Random(System.currentTimeMillis());
		int[] weights = new int[nodes];
		for(int i = 0; i < nodes; i++)
			weights[i] = r.nextInt(10000);
		ArrayList<Edge>[] adj = new ArrayList[nodes];
		for(int i = 0; i < nodes; i++)
			adj[i] = new ArrayList<Edge>();
		int tot = nodes * nodes - nodes;
		int remaining = edges;
		boolean[][] used = new boolean[nodes][nodes];
		DSU connected = new DSU(nodes);
		while(remaining > 0 || connected.sets != 1) {
			for(int i = 0; i < nodes; i++) {
				for(int j = 0; j < nodes; j++) {
					if(i == j || r.nextInt(tot) + 1 > edges || used[i][j])
						continue;
					adj[i].add(new Edge(j, r.nextInt(11)));
					adj[j].add(new Edge(i, r.nextInt(11)));
					connected.merge(i, j);
					used[i][j] = true;
					used[j][i] = true;
					remaining--;
				}
			}
		}
		return new Graph(adj, weights);
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < adj.length; i++)
			sb.append(adj[i].toString() + " " + i + "\n");
		return sb.toString();
	}
}
