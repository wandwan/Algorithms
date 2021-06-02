package Algorithms;
import java.util.*;
import java.io.*;

public class HLD {
	private int n, root;
	private RecursiveSegmentTree[] arr;
	private int[] up, depth, idx;
	private ArrayList<Integer>[] adj;
	/**
	 * Initialize HLD, requires RecursiveSegmentTree to operate. Do not mess with the Arrays pls.
	 * @param adj downwards directed adjacency list (it can't be undirected or a par array)
	 * @param root root of the tree
	 */
	public HLD(ArrayList<Integer>[] adj, int root) {
		this.adj = adj;
		n = adj.length;
		this.root = root;
		up = new int[n];
		depth = new int[n];
		idx = new int[n];
		arr = new RecursiveSegmentTree[n];
		int[] subTreeSize = new int[n];
		up[root] = -1;
		DFS(root, 0, subTreeSize);
		DFS2(root, subTreeSize);
	}
	private int DFS(int curr, int depth, int[] subTreeSize) {
		this.depth[curr] = depth;
		subTreeSize[curr] = 1;
		for(int i = 0; i < adj[curr].size(); i++)
			subTreeSize[curr] += DFS(adj[curr].get(i), depth + 1, subTreeSize);
		return subTreeSize[curr];
	}
	private void DFS2(int curr, int[] subTreeSize) {
		if(adj[curr].size() == 0) {
			arr[curr] = new RecursiveSegmentTree(new int[idx[curr] + 1]);
			return;
		}
		int max = -1;
		for(int i = 0; i < adj[curr].size(); i++)
			if(max == -1 || subTreeSize[adj[curr].get(i)] > subTreeSize[max])
				max = adj[curr].get(i);
		for(int i = 0; i < adj[curr].size(); i++) {
			int child = adj[curr].get(i);
			if(child != max) {
				up[child] = curr;
				idx[child] = 0;
			}
		}
		up[max] = up[curr];
		idx[max] = idx[curr] + 1;
		for(int i = 0; i < adj[curr].size(); i++)
			DFS2(adj[curr].get(i), subTreeSize);
		arr[curr] = arr[max];
	}
	/**
	 *  doesn't care about depths or ordering of the two nodes
	 *  increment's every node on path a to b inclusive by val
	 *  @param a first node (0 based)
	 *  @param b second node (0 based)
	 */
	public void inc(int a, int b, int val) {
		while(arr[a] != arr[b]) {
			if(up[a] == -1 || (up[b] != -1 && depth[up[a]] < depth[up[b]])) {
				a ^= b;
				b ^= a;
				a ^= b;
			}
			arr[a].inc(0, idx[a], val);
			a = up[a];
		}
		arr[a].inc(Math.min(idx[a], idx[b]), Math.max(idx[a], idx[b]), val);
	}
	/**
	 * 	doesn't care about depths or ordering of the two nodes
	 *  @return sum query of all values on path a to b inclusive
	 *  @param a first node (0 based)
	 *  @param b second node (0 based)
	 */
	public long query(int a, int b) {
		long sum = 0;
		while(arr[a] != arr[b]) {
			if(up[a] == -1 || (up[b] != -1 && depth[up[a]] < depth[up[b]])) {
				a ^= b;
				b ^= a;
				a ^= b;
			}
			sum += arr[a].getSum(0, idx[a]);
			a = up[a];
		}
		sum += arr[a].getSum(Math.min(idx[a], idx[b]), Math.max(idx[a], idx[b]));
		return sum;
	}
}
//public static void main(String[] args) {
//	Random r = new Random(System.currentTimeMillis());
//	int size = r.nextInt(1000) + 1;
//	Tree t = Tree.getTestTree(size, -1);
//	HLD test = new HLD(t.adj, t.root);
//	long[] val = new long[size];
//	for(int i = 0; i < 10000; i++) {
//		int a = r.nextInt(size);
//		int b = r.nextInt(size);
//		if(r.nextBoolean()) {
//			int v1 = r.nextInt(1000000);
//			test.inc(a, b, v1);
////			System.out.println("u: " + a + " " + b + " " + v1);
//			while(a != b) {
//				if(test.depth[a] < test.depth[b]) {
//					a ^= b;
//					b ^= a;
//					a ^= b;
//				}
//				val[a] += v1;
//				a = t.par[a];
//			}
//			val[a] += v1;
//		}
//		else {
//			long sum = 0;
//			long v1 = test.query(a, b);
////			System.out.println("queried: " + a + " " + b);
//			while(a != b) {
//				if(test.depth[a] < test.depth[b]) {
//					a ^= b;
//					b ^= a;
//					a ^= b;
//				}
//				sum += val[a];
//				a = t.par[a];
//			}
//			sum += val[a];
//			if(sum != v1) {
//				System.out.print("false: ");
//				System.out.println(sum + " " + v1);
//			}
////			else System.out.println("true");
//		}
//	}
//}
