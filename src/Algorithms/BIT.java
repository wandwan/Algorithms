package Algorithms;

class BIT {
	int[] BIT;
	int[] arrCopy;
	int n;
	/**
	 * creates blazing fast fenwick tree for range additions and updates
	 * @param n - size of fenwick tree
	 */
	public BIT(int n) {
		super();
		this.n = n + 1;
		BIT = new int[n + 1];
	}
	public BIT(int[] arr) {
		this(arr.length);
		arrCopy = new int[arr.length];
		for(int i = 0; i < arr.length; i++) {
			arrCopy[i] = arr[i];
			add(i, arr[i]);
		}
	}
	/**
	 * add val to index
	 * @param idx - what index to update
	 * @param val - value to add to index
	 */
	public void add(int idx, int val) {
		arrCopy[idx] += val;
		idx++;
		for(; idx < n; idx += (idx & (-idx)))
			BIT[idx] += val;
	}
	public void set(int idx, int val) {
		add(idx, val - arrCopy[idx]);
	}
	/**
	 * gets sum of values from [0,l) in array
	 * @param idx - l, left edge exclusive to sum to from index 0
	 * @return sum of values [0,l) in array, if l is 0, returns 0
	 */
	public int get(int idx) {
		int ret = 0;
		for(; idx > 0; idx -= (idx & (-idx)))
			ret += BIT[idx];
		return ret;
	}
	/**
	 * gets range sum of value[l,r)
	 * @param l - left edge inclusive to sum to
	 * @param r - right edge exclusive to sum to
	 * @return sum of values in [l,r) in array.
	 */
	public int get(int l, int r) {
		return (get(r) - get(l));
	}
	/**
	 * gets arr value at idx
	 * @param idx - idx to get arr value at
	 * @return value of arr[idx]
	 */
	public int getAt(int idx) {
		return arrCopy[idx];
	}
}