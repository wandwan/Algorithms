package Algorithms;
import java.util.*;

import javax.management.RuntimeMBeanException;

import java.io.*;
public class RecursiveSegmentTree {
	Node root;
	int n;
	int[] arr;
	class Node {
		int min;
		long sum;
		int l, r;
		int lazy;
		Node lc, rc, par;
		public Node(int l, int r) {
			this.l = l;
			this.r = r;
		}
		Node getNode(Node curr) {
			if(curr.l == curr.r) {
				curr.sum = arr[curr.l];
				curr.min = arr[curr.r];
				return curr;
			}
			Node lc = getNode(new Node(curr.l, (curr.l + curr.r) / 2));
			Node rc = getNode(new Node((curr.l + curr.r) / 2 + 1, curr.r));
			curr.min = Math.min(lc.min, rc.min);
			curr.sum = lc.sum + rc.sum;
			curr.lc = lc;
			curr.rc = rc;
			lc.par = curr;
			rc.par = curr;
			return curr;
		}
		public String toString() {
			return l + " " + r + " " + sum + ", ";
		}
	}
	public RecursiveSegmentTree(int n) {
		this(new int[n]);
	}
	public RecursiveSegmentTree(int[] arr) {
		this.n = arr.length;
		this.arr = arr;
		root = new Node(0, n - 1);
		root.getNode(root);
	}
	//[l,r]
	public void inc(int l, int r, int val) {
		inc(l,r,val, root);
	}
	private void pull(Node curr) {
		if(curr == root)
			return;
		Node t = curr.par;
		t.min = Math.min(t.lc.min, t.rc.min);
		t.sum = t.lc.sum + t.rc.sum;
	}
	private void push(Node curr) {
		if(curr.l == curr.r) {
			curr.lazy = 0;
			return;
		}
		curr.lc.min += curr.lazy;
		curr.rc.min += curr.lazy;
		curr.lc.sum += (curr.lc.r - curr.lc.l + 1) * (long) curr.lazy;
		curr.rc.sum += (curr.rc.r - curr.rc.l + 1) * (long) curr.lazy;
		curr.lc.lazy += curr.lazy;
		curr.rc.lazy += curr.lazy;
		curr.lazy = 0;
	}
	private void inc(int l, int r, int val, Node curr) {
		if(curr.l > r || curr.r < l)
			return;
		if(curr.l >= l && curr.r <= r) {
			curr.lazy += val;
			curr.min += val;
			curr.sum += val * (long) (curr.r - curr.l + 1);
		}
		else {
			push(curr);
			inc(l,r,val,curr.lc);
			inc(l,r,val,curr.rc);
		}
		pull(curr);
	}
	/**
	 * Returns min of segment [l,r]
	 * @param l	The inclusive left bound
	 * @param r The inclusive right bound
	 */
	public int getMin(int l, int r) {
		return getMin(l,r,root);
	}
	private int getMin(int l, int r, Node curr) {
		if(curr.l > r || curr.r < l)
			return Integer.MAX_VALUE;
		push(curr);
		if(curr.l >= l && curr.r <= r) return curr.min;
		else return Math.min(getMin(l,r,curr.lc), getMin(l,r,curr.rc));
	}
	/**
	 * Returns sum of segment [l,r]
	 * @param l	The inclusive left bound
	 * @param r The inclusive right bound
	 */
	public long getSum(int l, int r) {
		return getSum(l,r,root);
	}
	private long getSum(int l, int r, Node curr) {
		if(curr.l > r || curr.r < l)
			return 0;
		push(curr);
		if(curr.l >= l && curr.r <= r) return curr.sum;
		else return getSum(l,r,curr.lc) + getSum(l,r,curr.rc);
	}
//	@Override
//	public String toString() {
//		ArrayDeque<Node> q = new ArrayDeque<Node>();
//		StringBuilder sb = new StringBuilder();
//		q.add(root);
//		int max = 1;
//		int next = 0;
//		int curr = 0;
//		while(!q.isEmpty()) {
//			Node t = q.poll();
//			curr++;
//			sb.append(t + " ");
//			if(t.l != t.r) {
//				next += 2;
//				q.add(t.lc);
//				q.add(t.rc);
//			}
//			if(curr == max) {
//				sb.append("\n");
//				max = next;
//				next = 0;
//				curr = 0;
//			}
//		}
//		return sb.toString();
//	}
	@Override
	public String toString() {
		return arr.toString();
	}
//}
static BufferedReader br;
static StringTokenizer tokenizer;
public static void main(String[] args) throws Exception {
	br = new BufferedReader(new InputStreamReader(System.in));
	int n = nextInt();
	Random r = new Random(System.currentTimeMillis());
	int[] arr = new int[n];
	for(int i = 0; i < n; i++)
		arr[i] = (int) (r.nextInt(100));
	int[] t2 = new int[n];
	for(int i = 0; i < n; i++)
		t2[i] = arr[i];
	RecursiveSegmentTree st = new RecursiveSegmentTree(arr);
	System.out.println(Arrays.toString(arr));
	System.out.println(st);
	int m = nextInt();
	for(int i = 0; i < m; i++) {
		if(r.nextBoolean()) {
			int t = r.nextInt(n);
			int t1 = r.nextInt(n);
			if(t1 < t) {
				t ^= t1;
				t1 ^= t;
				t ^= t1;
			}
			int val = r.nextInt(1000);
			st.inc(t, t1, val);
			for(int j = t; j <= t1; j++)
				t2[j] += val;
			System.out.println("edit: " + i);
			System.out.println(t + " " + t1 + " " + val);
				
		} else {
			int t = r.nextInt(n);
			int t1 = r.nextInt(n);
			if(t1 < t) {
				t ^= t1;
				t1 ^= t;
				t ^= t1;
			}
			long ans = 0;
			for(int j = t; j <= t1; j++)
				ans += t2[j];
			long l = st.getSum(t, t1);
			if(l == ans) {
				System.out.print(true + " ");
				System.out.println(ans);
			}
			else {
				System.out.println(false);
				System.out.println(t + " " + t1);
				System.out.println(Arrays.toString(t2));
				System.out.println(st);
				System.out.println(l + " " + ans);
			}
		}
	}
}

public static String next() throws IOException {
	while (tokenizer == null || !tokenizer.hasMoreTokens()) {
		String line = br.readLine();
		if (line == null)
			throw new IOException();
		tokenizer = new StringTokenizer(line);
	}
	return tokenizer.nextToken();
}
public static int nextInt() throws IOException {
	return Integer.parseInt(next());
}

public static long nextLong() throws IOException {
	return Long.parseLong(next());
}

public static double nextDouble() throws IOException {
	return Double.parseDouble(next());
}
}
