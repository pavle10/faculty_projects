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

public class AppInsPanel extends JPanel {
	
	private AppController mController;
	
	public AppInsPanel(AppController controller) {
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
		JLabel coverImage = new JLabel(Strings.COVERPLACE);
		coverImage.setHorizontalAlignment(JLabel.CENTER);
		coverImage.setPreferredSize(new Dimension(400, 300));
		coverImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JLabel stegoImage = new JLabel(Strings.STEGOPLACE);
		stegoImage.setHorizontalAlignment(JLabel.CENTER);
		stegoImage.setPreferredSize(new Dimension(400, 300));
		stegoImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		first.add(coverImage);
		first.add(stegoImage);
		add(first);
		
		JPanel second = new JPanel();
		second.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel msgLabel = new JLabel(Strings.MSG);
		msgLabel.setPreferredSize(new Dimension(100, 30));
		JTextField textField = new JTextField();
		textField.setPreferredSize(new Dimension(500, 30));
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
				File file = mController.chooseImage(true);
				if (file == null) return ;
				BufferedImage image = null;
				try {
					image = ImageIO.read(file);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				coverImage.setIcon(new ImageIcon(image));
				revalidate();
				repaint();
			}
		});
		JButton makeStego = new JButton(Strings.MAKESTEGO);
		makeStego.setPreferredSize(new Dimension(100, 30));
		makeStego.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedImage img = mController.insertion(textField.getText());
				if (img == null) return ;
				stegoImage.setIcon(new ImageIcon(img));
				revalidate();
				repaint();
				
			}
		});
		JButton saveStego = new JButton(Strings.SAVESTEGO);
		saveStego.setPreferredSize(new Dimension(100, 30));
		saveStego.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mController.saveStego();
				
			}
		});
		JButton reset = new JButton(Strings.RESET);
		reset.setPreferredSize(new Dimension(100, 30));
		reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mController.reset(true);
				textField.setText("");
				coverImage.setIcon(null);
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
				mController.reset(true);
				textField.setText("");
				coverImage.setIcon(null);
				stegoImage.setIcon(null);
				revalidate();
				repaint();
				mController.changePanel(Strings.MAIN);
				
			}
		});
		third.add(importImg);
		third.add(makeStego);
		third.add(saveStego);
		third.add(reset);
		third.add(back);
		add(third);
	}

}
