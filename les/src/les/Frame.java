package les;

import javax.swing.JFrame;

public class Frame extends JFrame {
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	private MainPanel mPanel;
	
	public Frame(Controller controller) {
		setTitle("Largest empty square");
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		mPanel = new MainPanel(controller);
		add(mPanel);
		setVisible(true);
	}

	public MainPanel getPanel() {
		return mPanel;
	}

	public void setPanel(MainPanel mPanel) {
		this.mPanel = mPanel;
	}

}
