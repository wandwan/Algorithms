package Algorithms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;;

public class Tree {
	int root;
	ArrayList<Integer>[] adj;
	int[] par;
	public Tree(ArrayList<Integer>[] adj) {
		this.adj = adj;
	}
	public Tree(ArrayList<Integer>[] adj, int root) {
		this(adj);
		this.root = root;
	}
	public Tree(int[] par) {
		this.par = par;
	}
	// get a random tree of with size number of nodes
	// root can be specified or -1 if random root is needed
	public static Tree getTestTree(int size, int root) {
		Random r = new Random(System.currentTimeMillis());
		ArrayList<Integer>[] adj = new ArrayList[size];
		int[] par = new int[size];
		for(int i = 0; i < size; i++)
			adj[i] = new ArrayList<Integer>();
		if(root == -1)
			root = r.nextInt(size);
		ArrayDeque<Integer> q = new ArrayDeque<Integer>();
		ArrayList<Integer> remain = new ArrayList<Integer>();
		for(int i = 0; i < size; i++)
			if(i != root)
				remain.add(i);
		q.add(root);
		while(!q.isEmpty()) {
			int curr = q.remove();
			if(remain.size() == 0)
				break;
			// max amount of children you can have
			int children = Math.max(1, r.nextInt(Math.min(remain.size(), 17)));
			for(int i = 0; i < children; i++) {
				adj[curr].add(remain.remove(r.nextInt(remain.size())));
				q.add(adj[curr].get(adj[curr].size() - 1));
				par[adj[curr].get(adj[curr].size() - 1)] = curr;
			}
		}
		Tree t = new Tree(adj, root);
		t.par = par;
		return t;
	}
	//converts undirected adj tree to one directed downwards from root
	public static ArrayList<Integer>[] convertToDirected(ArrayList<Integer>[] adj, int root) {
		ArrayList<Integer>[] adj1 = new ArrayList[adj.length];
		for(int i = 0; i < adj.length; i++)
			adj1[i] = new ArrayList<Integer>();
		ArrayDeque<Integer> q = new ArrayDeque<Integer>();
		q.add(root);
		boolean[] visited = new boolean[adj.length];
		while(!q.isEmpty()) {
			int curr = q.remove();
			visited[curr] = true;
			for(int i = 0; i < adj[curr].size(); i++) {
				if(!visited[adj[curr].get(i)]) {
					adj1[curr].add(adj[curr].get(i));
					q.add(adj[curr].get(i));
				}
			}
		}
		return adj1;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		ArrayDeque<Integer> q = new ArrayDeque<Integer>();
		q.add(root);
		int num = 1;
		while(!q.isEmpty()) {
			int curr = q.remove();
			sb.append(curr + " ");
			for(int i = 0; i < adj[curr].size(); i++)
				q.add(adj[curr].get(i));
			num--;
			if(num == 0) {
				num = q.size();
				sb.append("\n");
			}
		}
		return sb.toString();
	}
}
