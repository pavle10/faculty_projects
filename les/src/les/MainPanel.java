package les;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class MainPanel extends JPanel {
	
	private Controller mController;
	private JPanel mCenter, mLower;
	private JTextField mField;
	
	public MainPanel(Controller controller) {
		mController = controller;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder(5,5,5,5));
		addPanels();
		
	}
	
	private void addPanels() {
		mCenter = new DotsPanel(mController);
		add(mCenter);
		
		mLower = new JPanel();
		mLower.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel label = new JLabel("a: ");
		label.setPreferredSize(new Dimension(20, 30));
		mLower.add(label);
		mField = new JTextField();
		mField.setPreferredSize(new Dimension(100, 30));
		mField.setEditable(false);
		mLower.add(mField);
		add(mLower);
		
	}

	public JTextField getField() {
		return mField;
	}

	public void setField(String a) {
		mField.setText(a);
		
	}
	
	

}
