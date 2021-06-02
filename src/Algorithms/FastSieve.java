package Algorithms;
import java.util.*;
import java.io.*;

public class FastSieve {
	public static ArrayList<Integer> sieveTo(int n) {
		int t =(int) Math.sqrt(n) + 1;
		ArrayList<Integer> primes = new ArrayList<Integer>();
		boolean[] arr = new boolean[n + 1];
		for(int i = 2; i <= t; i++) {
			if(arr[i])
				continue;
			primes.add(i);
			for(int j = i * i; j <= n; j += i)
				arr[j] = true;
		}
		for(int i = t + 1; i <= n; i++)
			if(!arr[i])
				primes.add(i);
		return primes;
	}
	public static void main(String[] args) {
		Long first = System.currentTimeMillis();
		FastSieve.sieveTo(100000000);
		System.out.println("First: " + (System.currentTimeMillis() - first));
		first = System.currentTimeMillis();
	}
}
