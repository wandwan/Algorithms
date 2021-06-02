package Algorithms;

public class LCS {
	/**
	 * finds longest common substring between string s1 and s2
	 * @return length of longest common substring
	 */
	public int solve(String s1, String s2) {
		int[] prev = new int[s2.length() + 1];
		int[] dp = new int[s2.length() + 1];
		for(int i = 1; i <= s1.length(); i++) {
			dp = new int[s2.length() + 1];
			for(int j = 1; j <= s2.length(); j++) {
				if(s1.charAt(i) == s2.charAt(j))
					dp[j] = prev[j - 1] + 1;
				else dp[j] = Math.max(prev[j], dp[j - 1]);
				prev = dp;
			}
		}
		return dp[s2.length()];
	}
}
