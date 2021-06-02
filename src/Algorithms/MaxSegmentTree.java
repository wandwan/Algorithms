package Algorithms;

import java.util.ArrayList;

public class MaxSegmentTree {
//	lazy range increment range maximum / minimum query
//	conditions for code:
//	1. we don't need to know how large the interval is
//	2. operation is both commutative and associative
	int n;
	int[] segTree;
	int[] lazy;
	boolean[] checker;
	int height;

	public MaxSegmentTree(int[] arr) {
		init(arr);
	}

	public MaxSegmentTree(ArrayList<Integer> arr) {
		init(arr);	
	}

	public void init(int[] arr) {
		n = arr.length;
		segTree = new int[n * 2];
		height = 32 - Integer.numberOfLeadingZeros(n);
		lazy = new int[n];
		checker = new boolean[n];
		for (int i = 0; i < n; i++)
			segTree[n + i] = arr[i];
		for (int i = n - 1; i >= 0; i--)
			segTree[i] = Math.max(segTree[i << 1], segTree[i << 1 | 1]);
	}

	public void init(ArrayList<Integer> arr) {
		n = arr.size();
		segTree = new int[n * 2];
		height = 32 - Integer.numberOfLeadingZeros(n);
		lazy = new int[n];
		checker = new boolean[n];
		for (int i = 0; i < n; i++)
			segTree[n + i] = arr.get(i);
		for (int i = n - 1; i >= 0; i--)
			// change to combine(a,b)
			segTree[i] = Math.max(segTree[i << 1], segTree[i << 1 | 1]);
	}

	// helper method to lazily apply a value to a node
	void apply(int idx, int value) {
		segTree[idx] += value;
		if (idx < n)
			lazy[idx] += value;
	}

	// helper method to apply an update to all parents
	void build(int idx) {
		while (idx > 1) {
			idx >>= 1;
			segTree[idx] = lazy[idx] + Math.max(segTree[idx << 1], segTree[idx << 1 | 1]);
		}
	}

	void push(int idx) {
		// suspicious code, to test replace int j = height with int j = max:
//			int max = 31 - Integer.numberOfLeadingZeros(idx);
		for (int j = height; j > 0; --j) {
			int i = idx >> j;
			if (lazy[i] != 0) {
				apply(i << 1, lazy[i]);
				apply(i << 1 | 1, lazy[i]);
				lazy[i] = 0;
			}
		}
	}

	// increment a point
	public void Inc(int idx, int val) {
		rInc(idx, idx + 1, val);
	}

	// range Increment interval[l,r) by value
	public void rInc(int l, int r, int value) {
		l += n;
		r += n;
		int origL = l;
		int origR = r;
		for (; l < r; l >>= 1, r >>= 1) {
			if ((l & 1) > 0)
				apply(l++, value);
			if ((r & 1) > 0)
				apply(--r, value);
		}
		build(origL);
		build(origR - 1);
	}

	public void modify(int idx, int val) {
		idx += n;
		for (; idx > 1; idx >>= 1)
			segTree[idx >> 1] = Math.max(segTree[idx], segTree[idx ^ 1]);
	}
	//query function [l, r)
	public int query(int l, int r) {
		l += n;
		r += n;
		push(l);
		push(r - 1);
		int res = Integer.MIN_VALUE;
		for (; l < r; l >>= 1, r >>= 1) {
			if ((l & 1) > 0)
				res = Math.max(res, segTree[l++]);
			if ((r & 1) > 0)
				res = Math.max(res, segTree[--r]);
		}
		return res;
	}
}
