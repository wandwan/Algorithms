package Algorithms;

import java.util.ArrayList;

public class PrimeSieve {
	//prime factorizes all numbers < n
	//O(number of prime factors) time query,  
	int[] arr;
	ArrayList<Integer> primes;
	int limit;
	public PrimeSieve(int n) {
		limit = n;
		arr = new int[n + 1];
		primes = new ArrayList<Integer>();
		init();
	}
	private void init() {
		if(arr.length >= 2)
			arr[1] = 1;
		arr[0] = 0;
		for(int i = 2; i < arr.length; i++) {
			if(arr[i] != 0)
				continue;
			primes.add(arr[i]);
			for(int k = i; k < arr.length; k += i) {
				arr[k] = i;
			}
		}
	}
	public boolean isPrime(int n) {
		return arr[n] == n && n != 1 && n != 0;
	}
	public ArrayList<Integer> getFactors(int n) {
		ArrayList<Integer> factors = new ArrayList<Integer>();
		while(arr[n] != n) {
			factors.add(arr[n]);
			n /= arr[n];
		}
		factors.add(n);
		return factors;
	}
	public int getPrime(int n) {
		return primes.get(n);
	}
}
