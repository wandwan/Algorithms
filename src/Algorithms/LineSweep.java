package Algorithms;
import java.util.*;
public class LineSweep {
	public static Point[] closestPair(ArrayList<Point> arr) {
		Point[] closest = new Point[2];
		if(arr.size() < 2)
			return null;
		arr.sort(new xPointComparator());
		TreeSet<Point> ySorted = new TreeSet<Point>(new yPointComparator());
		int s = 0;
		Double h = Double.MAX_VALUE;
		ySorted.add(arr.get(0));
		for(int i = 1; i < arr.size(); i++) {
			Point curr = arr.get(i);
			ySorted.add(curr);
			Point t = ySorted.lower(curr);
			while(t != null && curr.y - t.y <= h) {
				double d = curr.getDistance(t);
				if(d < h) {
					h = d;
					closest[0] = t;
					closest[1] = curr;
				}
				
				t = ySorted.lower(t);
			}
			t = ySorted.higher(curr);
			while(t != null && t.y - curr.y <= h) {
				double d = curr.getDistance(t);
				if(d < h) {
					h = d;
					closest[0] = t;
					closest[1] = curr;
				}
				t = ySorted.higher(t);
			}
			while(curr.x - arr.get(s).x >= h)
				ySorted.remove(arr.get(s++));
		}
		return closest;
	}
	public static Point[] test(ArrayList<Point> arr) {
		if(arr.size() < 2)
			return null;
		Point[] ans = new Point[2];
		Double d = Double.MAX_VALUE;
		for(int i = 0; i < arr.size() - 1; i++) {
			for(int j = i + 1; j < arr.size(); j++) {
				Double t = arr.get(i).getDistance(arr.get(j));
				if(t < d && !arr.get(i).equals(arr.get(j))) {
					d = t;
					ans[0] = arr.get(i);
					ans[1] = arr.get(j);
				}
			}
		}
		return ans;
	}
	public static void main(String[] args) {
		Long time = 0l;
		Long time1 = 0l;
		Random r = new Random(58423152);
		for(int i = 0; i < 1000; i++) {
//			System.out.println(i);
			ArrayList<Point> arr = new ArrayList<Point>();
			int d = r.nextInt(4000) + 3;
			for(int j = 0; j < d; j++) {
				arr.add(new Point(r.nextInt(10000), r.nextInt(10000)));
			}
			time -= System.currentTimeMillis();
			Point[] t1 = closestPair(arr);
			time += System.currentTimeMillis();
			time1 -= System.currentTimeMillis();
			Point[] t2 = test(arr);
			time1 += System.currentTimeMillis();
			if(t1[0] != t2[0] || t1[1] != t2[1]) {
				System.out.println("false");
				System.out.println(t1[0] +" " +  t1[1]);
				System.out.println(t2[0] + " " + t2[1]);
			}
		}
		System.out.println("done");
		System.out.println(time + " " + time1);
	}
}
