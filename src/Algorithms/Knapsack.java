package Algorithms;

import java.util.Arrays;

public class Knapsack {
	public static int knapsack(int[] val, int[] weights, int W) {
		int[] dp = new int[W + 1];
		Arrays.fill(dp, -1);
		dp[0] = 0;
		for(int k = 0; k < val.length; k++)
			for(int i = W; i >= 0; i--)
				if(i - weights[k] >= 0 && dp[i - weights[k]] != -1)
					dp[i] = Math.max(dp[i - weights[k]] + val[k], dp[i]);
		return dp[W];
	}
	public static void main(String[] args) {
		int[] val = {6, 4, 5, 3, 9, 7};
		int[] weights = {4, 2, 3, 1, 6, 4};
		int max = 10;
		System.out.println(knapsack(val,weights, max));
	}
}
