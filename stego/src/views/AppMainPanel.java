package views;


import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import stego.AppController;
import stego.Strings;

public class AppMainPanel extends JPanel {
	
	private JButton mInsPanel, mExtPanel;
	private AppController mController;
	
	public AppMainPanel(AppController controller) {
		mController = controller;
		setBackground(Color.DARK_GRAY);
		
		setLayout(new GridLayout(0,1,10,10));
        setBorder(new EmptyBorder(230,300,230,300));
        addButtons();
        add(mInsPanel);
        add(mExtPanel);
	}
	
	private void addButtons() {
		mInsPanel = new JButton(Strings.INSERTION);
		mInsPanel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mController.changePanel(Strings.INSERTION);
				
			}
		});
		mExtPanel = new JButton(Strings.EXTRACTION);
		mExtPanel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mController.changePanel(Strings.EXTRACTION);
				
			}
		});
		
		add(mInsPanel);
		add(mExtPanel);
		
		
	}

}
