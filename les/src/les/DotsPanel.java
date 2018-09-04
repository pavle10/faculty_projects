package les;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class DotsPanel extends JPanel {
	
	private Controller mController;
	private Point queryPoint;
	private double a = -1;
	
	public DotsPanel(Controller controller) {
		setPreferredSize(new Dimension(800, 450));
		setBackground(Color.LIGHT_GRAY);
		mController = controller;
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				queryPoint = new Point(e.getX(), e.getY());
				a = mController.query(e.getX(), e.getY());
				System.out.println(a);
				repaint();
			}
			
			
		});
	}

	@Override
	protected void paintComponent(Graphics ge) {
		super.paintComponent(ge);
		Graphics2D g = (Graphics2D)ge;
		Point[] points = mController.getPoints();
		g.setColor(Color.BLUE);
		
		for(int i = 0; i < points.length; i++) {
			int x = (int)points[i].getX();
			int y = (int)points[i].getY();
			g.fillOval(x-5, y-5, 10, 10);
		}
		
		if (queryPoint != null) {
			g.setColor(Color.RED);
			int x = (int) queryPoint.getX();
			int y = (int) queryPoint.getY();
			
			int s = (int) (a/2);
			System.out.println(s);
			g.fillOval(x-5, y-5, 10, 10);
			g.drawRect(x-s, y-s, 2*s, 2*s);
		}
		
	}
	

}
