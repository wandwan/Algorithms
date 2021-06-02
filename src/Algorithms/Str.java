package Algorithms;
//string class and algorithms
public class Str {
	
	String s;
	int[] pows;
	int[] subHash;
	int[] inv;
	int hash;
	@Override
	public int hashCode() {
		return hash;
	}
	public Str(String s) {
		this.s = s;
		subHash = computeHash(s);
		hash = subHash[s.length() - 1];
		pows = new int[s.length() + 1];
		inv = new int[s.length() + 1];
		pows[0] = 1;
		for(int i = 1; i < pows.length; i++)
			pows[i] = (pows[i - 1] * 53) % 1000000009;
		inv[0] = 1;
		for(int i = 1; i < s.length(); i++)
			inv[i] = modInv(pows[i], 1000000009);
	}
	//returns hash of [l,r)
	public int subHash(int l, int r) {
		if(l == 0)
			return subHash[r - 1];
		int t1 = subHash[r - 1];
		int t2 = subHash[l - 1];
		return (int) (((long) (t1 - t2) * inv[l]) % 1000000009);
	}
	/**
	 * hashes and subhashes string s
	 * @param s - string to hash
	 * @return all prefix hashes of s
	 */
	public int[] computeHash(String s) {
		if(subHash != null)
			return subHash;
		int[] allHash = new int[s.length()];
		int hash = 0;
		int pow = 1;
		for(int i = 0; i < s.length(); i++) {
			hash = (int) (( hash + (long) (s.charAt(i) - 'a' + 1) * pow) % 1000000009);
			pow = (int) (((long) pow * 53) % 1000000009);
			allHash[i] = hash;
		}
		return allHash;
	}
	private static long pow(long base, int exp, int mod) {
		long result = 1;
		while(true) {
			if((exp & 1) == 1)
				result = (result * base) % mod;
			exp >>= 1;
			if(exp == 0)
				break;
			base = (base * base) % mod;
		}
		return result;
	}
	private static int modInv(int a, int m) {
		return (int) pow(a, m - 2, m);
	}
}