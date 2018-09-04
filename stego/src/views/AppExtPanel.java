package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import stego.AppController;
import stego.Strings;

public class AppExtPanel extends JPanel {
	

	private AppController mController;
	
	public AppExtPanel(AppController controller) {
		mController = controller;
		setBackground(Color.DARK_GRAY);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPanels();
		
	}
	
	private void setPanels() {
		JPanel first = new JPanel();
		first.setPreferredSize(new Dimension(400, 550));
		first.setBackground(Color.DARK_GRAY);
		first.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 100));
		JLabel stegoImage = new JLabel(Strings.STEGOPLACE);
		stegoImage.setHorizontalAlignment(JLabel.CENTER);
		stegoImage.setPreferredSize(new Dimension(400, 300));
		stegoImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		first.add(stegoImage);
		add(first);
		
		JPanel second = new JPanel();
		second.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel msgLabel = new JLabel(Strings.MSG);
		msgLabel.setPreferredSize(new Dimension(100, 30));
		JTextField textField = new JTextField();
		textField.setPreferredSize(new Dimension(500, 30));
		textField.setEditable(false);
		second.add(msgLabel);
		second.add(textField);
		add(second);
		
		JPanel third = new JPanel();
		third.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton importImg = new JButton(Strings.IMPORT);
		importImg.setPreferredSize(new Dimension(100, 30));
		importImg.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				File file = mController.chooseImage(false);
				if (file == null) return ;
				BufferedImage image = null;
				try {
					image = ImageIO.read(file);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				stegoImage.setIcon(new ImageIcon(image));
				revalidate();
				repaint();
			}
		});
		JButton extract = new JButton(Strings.EXTRACT);
		extract.setPreferredSize(new Dimension(100, 30));
		extract.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = mController.extraction();
				if (msg == null) return ;
				textField.setText(msg);
				
			}
		});

		JButton reset = new JButton(Strings.RESET);
		reset.setPreferredSize(new Dimension(100, 30));
		reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mController.reset(false);
				textField.setText("");
				stegoImage.setIcon(null);
				revalidate();
				repaint();
				
			}
		});
		JButton back = new JButton(Strings.BACK);
		back.setPreferredSize(new Dimension(100, 30));
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mController.reset(false);
				textField.setText("");
				stegoImage.setIcon(null);
				revalidate();
				repaint();
				mController.changePanel(Strings.MAIN);
				
			}
		});
		third.add(importImg);
		third.add(extract);
		third.add(reset);
		third.add(back);
		add(third);
	}

}
