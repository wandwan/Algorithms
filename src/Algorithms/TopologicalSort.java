package Algorithms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TopologicalSort {
	public static int[] TopoSort(ArrayList<Integer>[] adj) {
		int[] ans = new int[adj.length];
		int[] incoming = new int[adj.length];
		int tot = 0;
		for(int i = 0; i < adj.length; i++)
			for(int j = 0; j < adj[i].size(); j++) {
				incoming[adj[i].get(j)]++;
				tot++;
			}
		ArrayDeque<Integer> q = new ArrayDeque<Integer>();
		for(int i = 0; i < adj.length; i++) {
			if(incoming[i] != 0)
				continue;
		q.add(i);
		ans[i] = 0;
			while(!q.isEmpty()) {
				int curr = q.poll();
				for(int j = 0; j < adj[curr].size(); j++) {
					int n = adj[curr].get(j);
					incoming[n]--;
					tot--;
					if(incoming[n] != 0)
						continue;
					ans[n] = ans[curr] + 1;
					q.add(n);
				}
			}
		}
		if(tot <= 0)
			return null;
		return ans;
	}
}
