package Algorithms;

public class IntegerUtil {
	/**
	 * @return base ^ exp
	 */
	public static long pow(long base, long exp) {
		long result = 1;
		while(true) {
			if((exp & 1) == 1)
				result *= base;
			exp >>= 1;
			if(exp == 0)
				break;
			base *= base;
		}
		return result;
	}
	/**
	 * @return base ^ exp % mod
	 */
	public static long pow(long base, long exp, int mod) {
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
	/**
	 * returns the modular inverse of a under mod m
	 */
	public static int modInv(int a, int m) {
		return (int) pow(a, m - 2, m);
	}
	/**
	 * returns the greatest common divisor between a and b
	 * @param a - first int
	 * @param b - second int
	 * @return greatest common divisor between a and b, O(log(max(a,b)))
	 */
	public static int gcd(int a, int b) {
		if(a == 0)
			return b;
		return gcd(b % a, a);
	}
	/**
	 * quick trick learned from my friend to calculate long square and ith roots.
	 * @return square root of a, floored to a long
	 */
	public static long sRoot(long a) {
		return iroot(a,2);
	}
	/**
	 * floored nth root of a
	 */
	public static long iroot(long a, long n) {
	    if (a < 2)
	        return a;
	    long c = 1;
	    long d = ((n - 1) * c + a / pow(c, n - 1)) / n;
	    long e = ((n - 1) * d + a / pow(d, n - 1)) / n;
	    while ((c != d) && (c != e)) {
	    	c = d;
	    	d = e;
	    	e = ((n - 1) * e + a / (pow(e, n - 1))) / n;
	    }
	    return Math.min(d, e);
	}
	/**
	 * returns an int that represents mask cleared to the ith bit.
	 */
	public static int clearToIthBit(int mask, int i) {
		return mask & ~((1 << i + 1) - 1);
	}
	/**
	 * removes lowest bit in mask
	 */
	public static int clearLowestBit(int mask) {
		return mask & (mask - 1);
	}
	/**
	 * clears from ith bit up
	 */
	public static int clearFromIthBit(int mask, int i) {
		return mask & ((1 << i) - 1);
	}
	public static char toLowerCase(char a) {
		return a |= ' ';
	}
	public static int countSetBits(int a) {
		int count = 0;
		while(a != 0) {
			a &= (a - 1);
			count++;
		}
		return count;
	}
	//ceiling of log2, copied from benQ
	public static int log2(int a) {
		int d = a;
		int ans = 0;
		int c = 16;
		while(c != 0) {
			if(a >> c != 0) {
				a >>= c;
				ans += c;
			}
			c >>= 1;
		}
		if(1 << ans != d)
			ans++;
		return ans;
	}
	/**
	 * get index of last bit
	 */
	public static int lastBitIdx(int mask) {
		int res = 0;
		while((mask & 1) == 0) {
			res++;
			mask >>= 1;
		}
		return res;
	}
	/**
	 * trick to isolate leftmost bit in only 6 shifts
	 */
	public static int getLeftMostBit(int x) {
		x |= x >> 1;
		x |= x >> 2;
		x |= x >> 4;
		x |= x >> 8;
		x |= x >> 16;
		x++;
		return (x >> 1);
	}
	public static boolean isPowerTwo(int x) {
		return (x & (x - 1)) == 0;
	}
	/**
	 * eulers method to get number of factors of n
	 */
	public static long euler(long n) {
		long t = sRoot(n);
		long c = n;
		long num = n;
		for(long i = 2; i <= t && c != 1; i++) {
			if(c / i * i == c) {
				num /= i;
				num *= i - 1;
			}
			while(c / i * i == c)
				c /= i;
		}
		if(c != 1) {
			num /= c;
			num *= c - 1;
		}
		return num;
	}
	// swap (a,b) -> a ^= b;
	//				 b ^= a;
	//				 a ^= b;
	//get rightmost one bit x & (-x)
	//get rightmost zero bit ~x & (x + 1)
	//flip rightmost zero bit x | (x + 1)
	//right propogate rightmost one bit x | (x - 1)
}
