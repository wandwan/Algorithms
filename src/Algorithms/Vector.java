package Algorithms;
class Vector implements Comparable<Vector> {
	double x, y, z;
	double r, theta, phi;
	/**
	 * Constructor of vector <x,y,0>
	 */
	public Vector(double x, double y) {
		this(x,y,0);
	}
	/*
	 * Constructor of Vector <x,y,z>
	 */
	public Vector(double x, double y, double z) {
		x = this.x;
		y = this.y;
		z = this.z;
		init();
	}
	/*
	 * Returns unit vector of self
	 */
	public Vector getUnit() {
		return scale(this, 1 / r);
	}
	/**
	 * Initializes Spherical and Cylindrical forms of the vector. 
	 * Possible optimization: remove from vector constructor for constant factor speedup
	 */
	public void init() {
		theta = Math.atan(y / x);
		phi = Math.atan(Math.sqrt(x * x + y * y) / z);
		r = Math.sqrt(getSquaredMag(this));
	}
	/**
	 * @param v - Target vector
	 * @return Squared magnitude of current vector
	 */
	public static double getSquaredMag(Vector v) {
		return v.x * v.x + v.y * v.y + v.z * v.z;
	}
	/**
	 * Negates current vector
	 */
	public void negate() {
		x = -x;
		y = -y;
		z = -z;
	}
	/**
	 * Adds vectors a and b
	 * @param a - First vector
	 * @param b - Second vector
	 * @return a + b
	 */
	public static Vector add(Vector a, Vector b) {
		return new Vector(a.x + b.x, a.y + b.y, a.z + b.z);
	}
	/**
	 * Subtracts vector b from a
	 * @param a - First vector
	 * @param b - Second vector
	 * @return a - b
	 */
	public static Vector subtract(Vector a, Vector b) {
		return new Vector(a.x - b.x, a.y - b.y, a.z - b.z);
	}
	/**
	 * Scales vector a by double s;
	 * @param a - First vector
	 * @param s - Double to be scaled by
	 * @return a * s
	 */
	public static Vector scale(Vector a, double s) {
		return new Vector(a.x * s, a.y * s, a.z * s);
	}
	/**
	 * Calculates dot product between vectors a and b or a * b
	 * @param a - First vector
	 * @param b - Second vector
	 * @return a * b
	 */
	public static double dotP(Vector a, Vector b) {
		return a.x * b.x + a.y * b.y + a.z * b.z;
	}
	/**
	 * Calculates cross product between vectors a and b or a x b
	 * @param a - First vector
	 * @param b - Second vector
	 * @return a x b
	 */
	public static Vector crossP(Vector a, Vector b) {
		return new Vector(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
	}
	/**
	 * Uses cross product to calculate direction of turning between vectors ab and bc.
	 * O(1), used for convex hull creation and Geometric algorithms where concave convex distinction matters.
	 * @param a - First point
	 * @param b - Second point
	 * @param c - Third point
	 * @return 1 if a -> b -> c turns right, -1 if a -> b -> c turns left and 0 if the 3 points are collinear
	 */
	public static int ccw(Vector a, Vector b, Vector c) {
		Vector t = subtract(c,a);
		Vector t1 = subtract(b,a);
		Vector t2 = crossP(t,t1);
		if(Math.abs(t2.z) <= .00001)
			return 0;
		return t2.z > 0 ? 1 : -1;
	}
	/**
	 * Calculates vector projection of a onto b
	 * O(1)
	 * @param a - First vector
	 * @param b - Second vector
	 * @return Vector projection of a onto b
	 */
	public static Vector project(Vector a, Vector b) {
		return scale(b, dotP(a,b)/ (getSquaredMag(a) * getSquaredMag(b)));
	}
	/**
	 *  Calculates euclidean distance between 2 points
	 *  O(1)
	 * @param p - First point as defined as a vector from (0,0,0)
	 * @param p1 - Second point as defined as a vector from (0,0,0)
	 * @return Euclidean distance between the two points
	 */
	public static double getDist(Vector p, Vector p1) {
		Vector t = subtract(p1,p);
		t.init();
		return Math.abs(t.r);
	}
	/**
	 * Calculates angle between 2 vectors
	 * O(1)
	 * @param p - First vector
	 * @param p1 - Second vector
	 * @return angle in radians between 2 vectors, can be paired with static method convertToDegrees.
	 */
	public static double getAngle(Vector p, Vector p1) {
		p.init(); p1.init();
		return Math.acos(dotP(p, p1) / (p.r * p1.r));
	}
	/**
	 * Converts Radians to Degrees.
	 * O(1)
	 * @param rad - radian measure of an angle
	 * @return angle in degrees, converted from radians
	 */
	public static double convertToDegrees(double rad) {
		return rad * 180 / Math.PI;
	}
	/**
	 * Finds the orthogonal distance between vector p and it's projection on Line l.
	 * Formally, finds Vector a such that a is equal to p minus the projection of p onto Line l
	 * O(1)
	 * @param p - the target vector
	 * @param l - the target line
	 * @return Vector that is orthogonal to l and has magnitude |p - (projection of p onto l)|
	 */
	public Vector getFoot(Vector p, Line l) {
		double t = ((p.x - l.point.x) * l.dir.x) + ((p.y - l.point.y) * l.dir.y) + ((p.z - l.point.z) * l.dir.z) / getSquaredMag(l.dir);
		return new Vector(l.dir.x * t + l.point.x, l.dir.y * t + l.point.y, l.dir.z * t + l.point.z);
	}
	@Override
	public String toString() {
		return "Vector [A=" + x + ", B=" + y + ", C=" + z + "]";
	}
	@Override
	public int compareTo(Vector o) {
		if(Math.abs(x - o.x) > .000001)
			return Double.compare(x, o.x);
		else if(Math.abs(y - o.y) > .000001)
			return Double.compare(y, o.y);
		else if(Math.abs(z - o.z) > .000001)
			return Double.compare(z, o.z);
		return 0;
	}
}

class LineSegment {
	Line l;
	double endTime;
	public LineSegment(Vector start, Vector end) {
		l = new Line(Vector.subtract(end, start), start);
		endTime = 1;
	}
	public LineSegment(Line l, double time) {
		this.l = l;
		endTime = time;
	}
	public void setEndTime(Vector end) {
		end = Vector.subtract(end, l.point);
		endTime = end.x / l.dir.x;
		if(Math.abs(endTime - end.y / l.dir.y) > .0001 || Math.abs(endTime - end.z / l.dir.z) > .0001)
			throw new RuntimeException("point not on line");
	}
}
