package les;

public class Point {
	
	private double x, y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
		
	}
	
	public int compareX(Point p) {
		if (x > p.getX()) return 1;
		if (x < p.getX()) return -1;
		if (y > p.getY()) return 1;
		if (y < p.getY()) return -1;
		return 0;
	}
	
	public int compareY(Point p) {
		if (y > p.getY()) return 1;
		if (y < p.getY()) return -1;
		if (x > p.getX()) return 1;
		if (x < p.getX()) return -1;
		return 0;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return "["+x+", "+y+"]";
	}

}
