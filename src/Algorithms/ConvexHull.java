package Algorithms;
import java.util.*;
import java.io.*;
public class ConvexHull {
	/*
	 * returns a clockwise sorted convex hull of points in arr (2d convex hull)
	 */
	public static Point[] ConvexHull(Point[] arr) {
		if(arr.length == 1)
			return arr;
		Arrays.sort(arr, new xPointComparator());
		int n = arr.length;
		Point[] Hull = new Point[2 * arr.length];
		int k = 0;
		for (int i = 0; i < n; ++i) {
			while (k >= 2 && cross(Hull[k - 2], Hull[k - 1], arr[i]) <= 0)
				k--;
			Hull[k++] = arr[i];
		}
		for (int i = n - 2, t = k + 1; i >= 0; i--) {
			while (k >= t && cross(Hull[k - 2], Hull[k - 1], arr[i]) <= 0)
				k--;
			Hull[k++] = arr[i];
		}
		if (k > 1)
			Hull = Arrays.copyOfRange(Hull, 0, k - 1);
		return Hull;
	}
	public static long cross(Point O, Point A, Point B) {
		return (A.x - O.x) * (long) (B.y - O.y) - (A.y - O.y) * (long) (B.x - O.x);
	}
}