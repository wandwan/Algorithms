package Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class BCC {
	int n;
	int[] low;
    int[] pre;
    int cnt;
    boolean[] articulation;
    ArrayList<Integer>[] adj;
    public BCC(ArrayList<Integer>[] adj) {
    	this.adj = adj;
    	n = adj.length;
        low = new int[adj.length];
        pre = new int[n];
        articulation = new boolean[n];
        for (int v = 0; v < n; v++)
            low[v] = -1;
        for (int v = 0; v < n; v++)
            pre[v] = -1;
        
        for (int v = 0; v < n; v++)
            if (pre[v] == -1)
                dfs(v, v);
    }

    private void dfs(int u, int v) {
        int children = 0;
        pre[v] = cnt++;
        low[v] = pre[v];
        for (int w : adj[v]) {
            if (pre[w] == -1) {
                children++;
                dfs(v, w);
                low[v] = Math.min(low[v], low[w]);
                if (low[w] >= pre[v] && u != v) 
                    articulation[v] = true;
            }
            else if (w != u)
                low[v] = Math.min(low[v], pre[w]);
        }
        if (u == v && children > 1)
            articulation[v] = true;
    }
}
