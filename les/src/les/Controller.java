package les;

import java.util.Random;

public class Controller {
	
	private RangeTree mModel;
	private Frame mView;
	
	public Controller() {
		Point[] points = initPoints();
		mModel = new RangeTree(points);
		
	}
	
	public void run() {
		mView = new Frame(this);
		
	}
	
	public double query(int x, int y) {
		Point qp = new Point(x, y);
		System.out.println("QP x: " + x + ", y: " + y);
		double min = 0, max = 3000;
		double mid;
		double a = 0;
		
		while (min <= max) {
			mid = (min+max)/2;
			if (!mModel.query(qp.getX()-mid/2, qp.getX()+mid/2, qp.getY()-mid/2, qp.getY()+mid/2)){
				a = mid;
				min = mid +1;
			}
			else {
				max = mid-1;
			}
		}
		
		mView.getPanel().setField(String.valueOf(a));
		return a;
	}
	
	
	private Point[] initPoints() {
		Random rnd = new Random();
		Point[] temp = new Point[30];

		for(int i = 0; i < 30; i++) {
			Point p = new Point(rnd.nextInt(750), rnd.nextInt(400));
			temp[i] = p;
		}
		
		return temp;
	}
	
	public Point[] getPoints() {
		return mModel.getPoints();
	}

}
