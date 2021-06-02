package Algorithms;

import java.util.ArrayList;
import java.util.HashMap;
public class MinSegmentTree {
	/**
	 * same as max segment tree.
	 */
	int l;
	HashMap<Long, Integer> loc;
	ArrayList<Integer> idx;
	int n;
	long[] segTree;
	public MinSegmentTree(int n) {
		l = 0;
		this.n = n;
		loc = new HashMap<Long, Integer>();
		idx = new ArrayList<Integer>();
		segTree = new long[n * 2];
		for(int i = 0; i < n ;i++)
			segTree[n + i] = Long.MAX_VALUE;
		for(int i = n - 1; i >= 0; i--)
			segTree[i] = Math.min(segTree[i<<1], segTree[i<<1 | 1]);
	}
	public MinSegmentTree(long[] arr) {
		init(arr);
	}
	public void init(long[] arr) {
		n = arr.length;
		loc = new HashMap<Long, Integer>();
		idx = new ArrayList<Integer>();
		segTree = new long[n * 2];
		for(int i = 0; i < n ;i++)
			segTree[n + i] = arr[i];
		for(int i = n - 1; i >= 0; i--)
			segTree[i] = Math.min(segTree[i<<1], segTree[i<<1 | 1]);
	}
	//interval [l, r)
	public long query(int l, int r) {
		if(r <= l)
			return query(r,l);
		long res = Long.MAX_VALUE;
		for(l += n, r += n; l < r; l >>= 1, r >>= 1) {
			if((l & 1) > 0) res = Math.min(segTree[l++], res);
			if((r & 1) > 0) res = Math.min(segTree[--r], res);
		}
		return res;
	}
	public void modify(int p, long value) {
		  for (segTree[p += n] = value; p > 1; p >>= 1) 
			  segTree[p>>1] = Math.min(segTree[p], segTree[p^1]);
	}
	public void insert(long val) {
		if(loc.containsKey(val))
			return;
		if(idx.size() == 0) {
			loc.put(val, l);
			modify(l++, val);
		} else {
			int id = idx.remove(idx.size() - 1);
			loc.put(val, id);
			modify(id, val);
		}
	}
	public void delete(long val) {
		if(loc.containsKey(val)) {
			int id = loc.remove(val);
			idx.add(id);
		}
	}
	public int size() {
		return l - idx.size();
	}
}
