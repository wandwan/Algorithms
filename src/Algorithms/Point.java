package Algorithms;

import java.util.Comparator;

public class Point {
	int x, y;

	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	public Double getDistance(Point o) {
		return Math.sqrt((x - o.x) * (x - o.x) + (y - o.y) * (y - o.y));
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	public String toString() {
		return x + " " + y;
	}
}
class yPointComparator implements Comparator<Point> {

	@Override
	public int compare(Point o1, Point o2) {
		if(o1.y != o2.y)
			return o1.y - o2.y;
		return o1.x - o2.x;
	}
	
}
class xPointComparator implements Comparator<Point> {

	@Override
	public int compare(Point o1, Point o2) {
		if(o1.x != o2.x)
			return o1.x - o2.x;
		return o1.y - o2.y;
	}
	
}