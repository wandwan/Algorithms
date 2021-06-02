package Algorithms;

import java.util.Arrays;

public class SuffArray {
	String s;
	int n;
	//p[i] -> position of (i - 1)th smallest substring
	//c[i] -> rank of substring at pos i, 1 based since (char) 0
	int[] p, c;
	//size of similarity between i and i + 1 character in string ex. aac --> [1, 0]
	int[] lcp, rp;
	SparseTable rmq;
	//this is the number of different substrings, computation takes O(n) time
	//you need to call getNumSubString to initialize this
	int diffSubString;
	//remember that (char) 0 is appended to the end
	//remember that p[0] = will always be n - 1 since (char) 0 is always first
	//reminder on LCP array to ignore 0
	//reminder that c[n - 1] == 0 since thats where (char) 0 willl be
	//tldr, s, n , p , lcp, and rmq are 1 based
	//tldr, ignore c[n - 1] but c is 0 based
	public SuffArray(String s) {
		super();
		s = s + (char) 0;
		this.s = s;
		n = s.length();
		p = new int[n];
		lcp = new int[n];
		initSort();
		initLCP();
		rmq = new SparseTable(lcp);
	}
	private void initSort() {
		c = new int[n];
		int[] cnt = new int[Math.max(128, n)];
		for(int i = 0; i < n; i++)
			cnt[s.charAt(i)]++;
		for(int i = 1; i < 128; i++)
			cnt[i] += cnt[i - 1];
		for(int i = 0; i < n; i++)
			p[--cnt[s.charAt(i)]] = i;
		c[p[0]] = 0;
		int classes = 1;
		for(int i = 1; i < n; i++) {
			if(s.charAt(p[i]) != s.charAt(p[i - 1]))
				classes++;
			c[p[i]] = classes - 1;
		}
		int[] pn = new int[n], cn = new int[n];
		for(int h = 0; (1 << h) < n; h++) {
			for(int i = 0; i < n; i++) {
				pn[i] = p[i] - (1 << h);
				if(pn[i] < 0)
					pn[i] += n;
			}
			cnt = new int[Math.max(128, n)];
			for(int i = 0; i < n; i++)
				cnt[c[pn[i]]]++;
			for(int i = 1; i < classes; i++)
				cnt[i] += cnt[i - 1];
			for(int i = n - 1; i >= 0; i--)
				p[--cnt[c[pn[i]]]] = pn[i];
			cn[p[0]] = 0;
			classes = 1;
			for(int i = 1; i < n; i++) {
				if(c[p[i]] != c[p[i - 1]] || c[(p[i] + (1 << h)) % n] != c[(p[i-1] + (1 << h)) % n])
					++classes;
				cn[p[i]] = classes - 1;
			}
			int[] t = c;
			c = cn;
			cn = t;
		}
	}
	private void initLCP() {
		int[] rank = new int[n];
		for(int i = 0; i < n; i++)
			rank[p[i]] = i;
		int k = 0;
		lcp = new int[n - 1];
		for(int i = 0; i < n; i++) {
			if(rank[i] == n - 1) {
				k = 0;
				continue;
			}
			int j = p[rank[i] + 1];
			while (i + k < n && j + k < n && s.charAt(i + k) == s.charAt(j + k))
	            k++;
	        lcp[rank[i]] = k;
	        if (k != 0)
	            k--;
		}
	}
	public int getNumSubString() {
		diffSubString = ((n - 1) * (n - 1) - (n - 1)) / 2;
		for(int i = 1; i < n - 1; i++)
			diffSubString -= lcp[i];
		return diffSubString;
	}
	//returns longest common prefix of the suffixes of i and j in the original string(0 based)
	public int getLCP(int i, int j) {
		return rmq.query(Math.min(rp[i], rp[j]), Math.max(rp[i], rp[j]) + 1);
	}
	//get index of all suffixes in your string in your sorted suffix array
	public int[] getPos() {
		rp = new int[n];
		for(int i = 0; i < n; i++)
			rp[p[i]] = i;
		return rp;
	}
	private class SparseTable {
		int[][] table;
		int[] log;
		int n;
		public SparseTable(int[] arr) {
			log = new int[arr.length + 1];
			n = arr.length;
			for(int i = 2; i <= n; i++)
				log[i] = log[i >> 1] + 1;
			table = new int[log[n] + 1][n];
			for(int i = 0; i < n; i++)
				table[0][i] = arr[i];
			for(int k = 1; 1 << k < n; k++)
				//remember table[i][k] = [i, i + 2 ^ k), k is zero based
				for(int i = 0; i + (1 << k) <= n; i++)
					table[k][i] = Math.min(table[k - 1][i], table[k - 1][i + (1 << k - 1)]);
		}
		//RMQ for [l, r)
		public int query(int l, int r) {
			return Math.min(table[log[r - l]][l], table[log[r - l]][r - (1 << log[r - l]) + 1]);
		}
	}
}
