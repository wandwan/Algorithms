package Algorithms;
/*
 * edge helper class
 */
public class Edge implements Comparable<Edge> {
	int val, end, id;
	static int assigner = 0;
	public Edge(int end, int val) {
		this.val = val;
		this.end = end;
		id = assigner++;
	}
	public Edge(int end) {
		this(1,end);
	}
	@Override
	public int compareTo(Edge o) {
		if(val != o.val)
			return val - o.val;
		return id - o.id;
	}
	@Override
	public String toString() {
		return "val: " + val + " end: " + end;
	}
}
