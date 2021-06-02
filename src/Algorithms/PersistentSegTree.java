package Algorithms;

import java.util.ArrayList;

public class PersistentSegTree {
	ArrayList<Node> versions;
	Node root;
	int[] arr;
	class Node {
		int val, lazy;
		int l, r;
		Node lc, rc, par;
		public Node(int l, int r, int[] arr) {
			super();
			this.l = l;
			this.r = r;
		}
		public Node(Node prev) {
			super();
			this.val = prev.val;
			this.lazy = prev.lazy;
			this.l = prev.l;
			this.r = prev.r;
			this.lc = prev.lc;
			this.rc = prev.rc;
		}
	}
	public PersistentSegTree(int[] arr) {
		root = new Node(0, arr.length - 1, arr);
		this.arr = arr;
		versions = new ArrayList<Node>();
		init(root, arr);
	}
	private void init(Node curr, int[] arr) {
		if(curr.l == curr.r) {
			curr.val = arr[curr.l];
			return;
		}
		Node lc = new Node(curr.l, curr.l + (curr.r - curr.l) / 2, arr);
		Node rc = new Node(curr.l + (curr.r - curr.l) / 2 + 1, curr.r, arr);
		curr.lc = lc;
		curr.rc = rc;
		curr.lc.par = curr;
		curr.rc.par = curr;
		init(lc, arr);
		init(rc, arr);
		curr.val = lc.val + rc.val;
	}
	//inclusive both sides
	public int query(int l, int r, int ver) {
		return query(l, r, versions.get(ver));
	}
	private int query(int l, int r, Node curr) {
		if(l == curr.l && r == curr.r)
			return curr.val;
		if(curr.l > r || curr.r < l)
			return 0;
		return query(l , Math.min(curr.lc.r, r), curr.lc) + query(Math.max(curr.rc.l, l), r, curr.rc);
	}
	public void add(int idx, int val) {
		update(idx, val, root, root, false);
	}
	public void set(int idx, int val) {
		update(idx, val - arr[idx], root, root, false);
	}
	private void update(int idx, int val, Node curr, Node par, boolean left) {
		if(curr.l > idx || curr.r < idx)
			return;
		if(par != curr) {
			curr = new Node(curr);
			curr.par = par;
			if(left) par.lc = curr;
			else par.rc = curr;
		} else {
			versions.add(new Node(curr));
			root = curr;
		}
		curr.val += val;
		if(curr.l == curr.r)
			return;
		update(idx, val, curr.lc, curr, true);
		update(idx, val, curr.rc, curr, false);
	}
}
