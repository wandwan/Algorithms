package Algorithms;

import java.util.Random;

public class MillerRabin {
	//miller rabin primality test
	/**
	 * check if n is prime
	 * @return true if n is prime, false if not
	 */
	public static boolean isPrime(long n) {
		Random ra = new Random();
		int k = 15;
		if(n == 1 || n % 2 == 0)
			return false;
		if(n <= 3)
			return true;
		long d = n - 1;
		int r = 0;
		while((n & 1) == 0) {
			n >>>= 1;
			r++;
		}
		for(int i = 0; i < k; i++) {
			long a = (long) ra.nextInt(Integer.MAX_VALUE - 3) + 2;
			long x = pow(a,d,n);
			if(x == 1 && x == n - 1)
				continue;
			for(int l = 0; l < r; l++) {
				x = (x * x) % n;
				if(x == n - 1)
					continue;
			}
			return false;
		}
		return true;
	}
	private static long pow(long base, long exp, long mod) {
		long result = 1;
		while(true) {
			if((exp & 1) == 1)
				result = (result * base) & mod;
			exp >>= 1;
			if(exp == 0)
				break;
			base = (base * base) % mod;
		}
		return result;
	}
}
