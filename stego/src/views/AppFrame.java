package views;

import java.awt.CardLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import stego.AppController;
import stego.Strings;

public class AppFrame extends JFrame {
	
	public static final int WIDTH = 900;
	public static final int HEIGHT = 700;
	private JPanel mContentPane;
	
	public AppFrame(AppController controller) {
		setTitle(Strings.TITLE);
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initUI(controller);
		setVisible(true);
	}
	
	private void initUI(AppController controller) {
		mContentPane = new JPanel();
		mContentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		mContentPane.setLayout(new CardLayout());
		
		AppMainPanel mainPanel = new AppMainPanel(controller);
		AppInsPanel insPanel = new AppInsPanel(controller);
		AppExtPanel extPanel = new AppExtPanel(controller);
		mContentPane.add(mainPanel, Strings.MAIN);
		mContentPane.add(insPanel, Strings.INSERTION);
		mContentPane.add(extPanel, Strings.EXTRACTION);
		setContentPane(mContentPane);
		
	}

	public JPanel getContentPane() {
		return mContentPane;
	}
	
	

}
