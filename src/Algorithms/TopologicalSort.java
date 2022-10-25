package Algorithms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TopologicalSort {
	public static ArrayList<Integer> topoSort(ArrayList<Integer>[] adj) {
		ArrayList<Integer> queue = new ArrayList<Integer>();
        int[] outgoing = new int[adj.length];
        for(int i = 0 ; i < adj.length; i++) {
            outgoing[i] = adj[i].size();
            if(outgoing[i] == 0)
                queue.add(i);
        }
        adj = reverse(adj);
        for(int i = 0; i < queue.size(); i++) {
            int curr = queue.get(i);
            for(int next: adj[curr])
                if(--outgoing[next] == 0)
                    queue.add(next);
        }
        if(queue.size() < adj.length)
            return new ArrayList<Integer>();
        Collections.reverse(queue);
        return queue;
	}
    public static ArrayList<Integer>[] reverse(ArrayList<Integer>[] adj) {
        ArrayList<Integer>[] reversed = new ArrayList[adj.length];
        for(int i = 0; i < adj.length; i++)
            reversed[i] = new ArrayList<Integer>();
        for(int i = 0; i < adj.length; i++)
            for(int end : adj[i])
                reversed[end].add(i);
        return reversed;
    }
}
