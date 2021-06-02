package Algorithms;
import java.util.*;
import java.io.*;

public class MST {
	/**
	 * builds Minimum spanning tree.
	 */
	private int assigner = 0;
	private ArrayList[] adj;
	ArrayList[] MST;
	private int nodes;
	/**
	 * builds minimum spanning tree based on adjacency list
	 * @param adj - adjacency list
	 */
	public MST(ArrayList[] adj) {
		this.adj = adj;
		nodes = adj.length;
		init();
	}
	private void init() {
		MST = getAdj(nodes);
		assert adj.length > 0;
		boolean[] finished = new boolean[nodes];
		int filled = 1;
		int currNode = 0;
		TreeSet<Edge> min = new TreeSet<Edge>();
		for(Object e: adj[0]) {
			assert e instanceof Edge;
			min.add((Edge) e);
		}
		finished[0] = true;
		while(filled != nodes && !min.isEmpty()) {
			Edge t = min.pollFirst();
			if(!finished[t.end]) {
				for(Object e: adj[t.end]) {
					assert e instanceof Edge;
					min.add((Edge) e);
				}
			}
			MST[currNode].add(t);
			currNode = t.end;
		}
	}
	private ArrayList[] getAdj(int nodes) {
		ArrayList[] t = new ArrayList[nodes];
		for(int i = 0; i < nodes; i++)
			t[i] = new ArrayList<Edge>();
		return t;
	}
	private Edge getEdge(int end, int val) {
		return new Edge(end - 1, val);
	}
	private class Edge implements Comparable<Edge> {
		int end;
		int ID;
		int val;
		public Edge(int end, int val) {
			super();
			this.end = end;
			this.val = val;
			ID = assigner++;
		}
		public int compareTo(Edge e) {
			if(val != e.val)
				return val - e.val;
			return ID - e.ID;
		}
	}
}
