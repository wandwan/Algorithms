package Algorithms;

import java.util.*;
import java.io.*;

public class Hamiltonian {
	static long[][] dp;
	static int n;
	static int cur;
	/**
	 * finds length of shortest hamiltonian path in dense graph (NP complete problem)
	 * @param distance adjacency matrix,
	 * @return length of shortest hamiltonian path its, O(2^n) but theoretically optimal!
	 */
	public static long shortestPath(int[][] dist) {
		n = dist.length;
		dp = new long[1 << n][n];
		for (long[] d : dp)
			Arrays.fill(d, Long.MAX_VALUE / 2);
		for (int i = 0; i < n; i++)
			dp[1 << i][i] = 0;
		cur = 1 << n;
		for (int mask = 1; mask < cur; mask++)
			for (int i = 0; i < n; i++)
				if ((mask & 1 << i) != 0)
					for (int j = 0; j < n; j++)
						if ((mask & 1 << j) != 0)
							dp[mask][i] = Math.min(dp[mask][i], dp[mask ^ (1 << i)][j] + dist[j][i]);
		long res = Long.MAX_VALUE;
		for (int i = 0; i < n; i++)
			res = Math.min(res, dp[cur - 1][i]);
		return res;
	}
	/**
	 * returns shortest hamiltonian path in dense graph (NP complete problem)
	 * @param dist - distance adjaceny matrix
	 * @return shortest hamiltonian path
	 */
	public static int[] getPath(int[][] dist) {
		if (dp == null)
			shortestPath(dist);
		cur--;
		int[] order = new int[n];
		int last = -1;
		int bj = -1;
		for (int j = 0; j < n; j++)
			if ((cur & 1 << j) != 0 && (bj == -1 || dp[cur][bj] + 0 > dp[cur][j] + 0))
				bj = j;
		order[n - 1] = bj;
		cur ^= 1 << bj;
		last = bj;
		for (int i = n - 2; i >= 0; i--) {
			bj = -1;
			for (int j = 0; j < n; j++)
				if ((cur & 1 << j) != 0 && (bj == -1 || dp[cur][bj] + dist[bj][last] > dp[cur][j] + dist[j][last]))
					bj = j;
			order[i] = bj;
			cur ^= 1 << bj;
			last = bj;
		}
		return order;
	}
}
