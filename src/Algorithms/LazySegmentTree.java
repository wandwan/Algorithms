package Algorithms;

import java.util.ArrayList;

public class LazySegmentTree {
	//from https://codeforces.com/blog/entry/18051
	//for non ints change int[] to (Class[]) for both
		int n;
		int[] segTree;
		int[] lazy;
		boolean[] checker;
		int height;
		//for non ints change int[] to (Class[]) and new int[n * 2] to new (Class[n * 2]) 
		public LazySegmentTree(int[] arr) {
			init(arr);
		}
		public LazySegmentTree(ArrayList<Integer> arr) {
			init(arr);	
		}
		//for non commutative, just change + to combine(a,b)
		public void init(int[] arr) {
			n = arr.length;
			segTree = new int[n * 2];
			height = 32 - Integer.numberOfLeadingZeros(n);
			lazy = new int[n];
			checker = new boolean[n];
			for(int i = 0; i < n ;i++)
				segTree[n + i] = arr[i];
			for(int i = n - 1; i >= 0; i--)
				segTree[i] = segTree[i<<1] + segTree[i<<1 | 1];
		}
		public void init(ArrayList<Integer> arr) {
			n = arr.size();
			segTree = new int[n * 2];
			height = 32 - Integer.numberOfLeadingZeros(n);
			lazy = new int[n];
			checker = new boolean[n];
			for(int i = 0; i < n ;i++)
				segTree[n + i] = arr.get(i);
			for(int i = n - 1; i >= 0; i--)
				//change to combine(a,b)
				segTree[i] = segTree[i<<1] + segTree[i<<1 | 1];
		}
	//lazy assignment update, range sum query
	//conditions for code: associativity, replacement instead of increment(works by rebuilding tree)
	//k is length of interval represented by that segtree node
	void calc(int idx, int k) {
		//if not using numbers, change lazy to Class[] and check if(lazy[idx] = null)
		if(!checker[idx]) segTree[idx] = segTree[idx << 1] + segTree[idx << 1 | 1];
		else segTree[idx] += lazy[idx] * k;
	}
	void apply(int idx, int value, int k) {
		segTree[idx] += value * k;
		if(idx < n) { 
			lazy[idx] = value;
			checker[idx] = true;
		}
	}
	void build(int l, int r) {
		int k = 2;
		for(l += n, r += n - 1; l > 1; k <<= 1) {
			l >>= 1; r >>= 1;
			for(int i = r; i >= l; --i) calc(i,k);
		}
	}
	void push(int l, int r) {
		int t = height;
		int k = 1 << (height - 1);
		for(l += n, r += n - 1; t > 0; --t, k >>= 1) {
			for(int i = l >> t; i <= r >> t; ++i) if(lazy[i] != 0) {
				apply(i << 1, lazy[i], k);
				apply(i << 1 | 1, lazy[i], k);
				lazy[i] = 0;
				checker[i] = false;
			}
		}
	}
	//sets interval [l, r) to val
	public void modify(int l, int r, int val) {
		push(l, l + 1);
		push(r - 1, r);
		boolean checkL = false, checkR = false;
		int k = 1;
		for(l += n, r += n; l < r; l >>= 1, r >>= 1, k <<= 1) {
			if(checkL) calc(l - 1, k);
			if(checkR) calc(r, k);
			if((l & 1) > 0) {
				apply(l++, val, k);
				checkL = true;
			}
			if((r & 1) > 0) {
				apply(--r, val, k);
				checkR = true;
			}
		}
		for(--l; r > 0; l >>= 1, r >>= 1, k <<= 1) {
			if(checkL) calc(l,k);
			if(checkR && (!checkL || l != r)) calc(r,k);
		}
	}
	//query is in interval [l, r)
	public int query(int l, int r) {
		push(l, l + 1);
		push(r - 1, r);
		int res = 0;
		for(l += n, r += n; l < r; l >>= 1, r >>= 1) {
			if((l & 1) > 0) res += segTree[l++];
			if((r & 1) > 0) res += segTree[--r];
		}
		return res;
	}

}
