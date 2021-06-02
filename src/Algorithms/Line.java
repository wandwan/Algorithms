package Algorithms;

public class Line {
	Vector dir, point;
	/*
	 * Creates a new Line which includes a point and extends infinitely in direction dir.
	 *
	 */
	public Line(Vector dir, Vector point) {
		super();
		this.dir = dir;
		this.point = point;
	}
	/*
	 * Creates a new Line which runs between two points (p and p1, represented as vectors from (0,0,0)).
	 * simplify boolean used to specify whether or not a unit vector is required for direction.
	 */
	public Line(Vector p, Vector p1, boolean simplify) {
		dir = Vector.subtract(p1, p);
		point = p;
		if(simplify) dir = dir.getUnit();
	}
	/*
	 * Returns a Point (represented as a vector from (0,0,0)) that is located at the intersection between lines l and l1
	 * If l and l1 do not intersect for any reason (are skew, parallel, etc) returns point closest on l closest to l1
	 * @param l - First line
	 * @param l1 - Second Line
	 */
	public static Vector getIntersect(Line l, Line l1) {
		return getConnecting(l,l1).l.point;
	}
	/*
	 * @
	 */
	public static LineSegment getConnecting(Line l, Line l1) {
		if(isParallel(l, l1)) {
			Vector connect = Vector.subtract(l.point, l1.point);
			Vector dir = Vector.subtract(connect, Vector.project(connect, l.dir));
			return new LineSegment(new Line(dir, l1.point), 1);
		}
		Vector n = Vector.crossP(l.dir, l1.dir);
		Vector n1 = Vector.crossP(l.dir, n);
		Vector n2 = Vector.crossP(l1.dir, n);
		Vector c1 = Vector.add(l.point, Vector.scale(l.dir, Vector.dotP(Vector.subtract(l1.point, l.point), n2) / (Vector.dotP(l.dir, n2))));
		Vector c2 = Vector.add(l1.point, Vector.scale(l1.dir, Vector.dotP(Vector.subtract(l.point, l1.point), n1) / (Vector.dotP(l1.dir, n1))));
		return new LineSegment(c1, c2);
	}
	public static boolean isParallel(Line l, Line l1) {
		double a = l1.dir.x - l.dir.x;
		double b = l1.dir.y - l.dir.y;
		double c = l1.dir.z - l.dir.z;
		return Math.abs(a - b) < .0001 && Math.abs(b - c) < .0001;
	}
}