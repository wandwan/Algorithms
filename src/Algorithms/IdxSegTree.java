package Algorithms;
//segment tree update O(log n) range query O(log n)
class IdxSegTree {
	private Node root;
	private int n;
	private int[] arr;
	private class Node {
		int min;
		int l, r;
		Node lc, rc, par;
		public Node(int l, int r) {
			this.l = l;
			this.r = r;
		}
		Node getNode(Node curr) {
			if(curr.l == curr.r) {
				curr.min = curr.l;
				return curr;
			}
			Node lc = getNode(new Node(curr.l, (curr.l + curr.r) / 2));
			Node rc = getNode(new Node((curr.l + curr.r) / 2 + 1, curr.r));
			if(arr[lc.min] < arr[rc.min])
				min = lc.min;
			else
				min = rc.min;
			curr.lc = lc;
			curr.rc = rc;
			lc.par = curr;
			rc.par = curr;
			return curr;
		}
		public String toString() {
			return l + " " + r + ", ";
		}
	}
	/**
	 * builds new Minimum segment tree of size n 
	 * @param n - size of segment tree,
	 */
	public IdxSegTree(int n) {
		this(new int[n]);
	}
	/**
	 * builds new Minimum segment tree of size n
	 * @param arr - array of elements to base segment tree on
	 */
	public IdxSegTree(int[] arr) {
		this.n = arr.length;
		this.arr = arr;
		root = new Node(0, n - 1);
		root.getNode(root);
	}
	/**
	 * sets idx to val
	 * @param idx - index in original array
	 * @param val - value to set segtree index that correlates to idx to
	 */
	public void set(int idx, int val) {
		val = arr[idx] - val;
		inc(idx, val);
	}
	/**
	 * increments idx by val
	 * @param idx - index in original array
	 * @param val - value to increment segtree index that correlates to idx to
	 */
	public void inc(int idx, int val) {
		arr[idx] += val;
		inc(idx, val, root);
	}
	private void inc(int idx, int val, Node curr) {
		if(curr.l > idx || curr.r < idx || curr.l == curr.r)
			return;
		inc(idx, val, curr.lc);
		inc(idx, val, curr.rc);
		if(arr[curr.lc.min] < arr[curr.rc.min])
			curr.min = curr.lc.min;
		else
			curr.min = curr.rc.min;
	}
	/**
	 * gets minimum in range [l, r)
	 * @param l - left border to find range minimum from
	 * @param r - right border to find range minimum from (exclusive)
	 * @return range minimum query between l and r exclusive
	 */
	public int getMin(int l, int r) {
		return getMin(l,r,root);
	}
	private int getMin(int l, int r, Node curr) {
		if(curr.l > r || curr.r < l)
			return Integer.MAX_VALUE;
		if(curr.l >= l && curr.r <= r) return curr.min;
		int t1 = getMin(l,r,curr.lc);
		int t2 = getMin(l,r,curr.rc);
		if(arr[t1] < arr[t2])
			return t1;
		return t2;
	}
}