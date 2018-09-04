package stego;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import models.AppExtModel;
import models.AppInsModel;
import views.AppFrame;

public class AppController {
	
	private AppFrame mFrame;
	private AppInsModel mInsModel;
	private AppExtModel mExtModel;
	
	public AppController() {
		mInsModel = new AppInsModel();
		mExtModel = new AppExtModel();
		
	}
	
	public void start() {
		mFrame = new AppFrame(this);
		
	}
	
	public void changePanel(String panelName) {
		CardLayout cl = (CardLayout)mFrame.getContentPane().getLayout();
		cl.show(mFrame.getContentPane(), panelName);
		
	}
	
	public File chooseImage(boolean isIns) {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(Strings.FILEPATH));
		int temp = fc.showOpenDialog(null);
		if (temp == JFileChooser.APPROVE_OPTION) {
			File f = fc.getSelectedFile();
			String name = f.getName();
			
			try {
				BufferedImage image = ImageIO.read(f);
				if (isIns) {
					mInsModel.setCover(image);
					mInsModel.setName(name.substring(0, name.length()-4));
				}
				else {
					mExtModel.setStego(image);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return f;
		}
		return null;
	}
	
	public BufferedImage insertion(String msg) {
		if (msg.length() == 0) {
			JOptionPane.showMessageDialog(mFrame, Strings.TYPEMSG);
			return null;
		}
		if (mInsModel.getCover() == null) {
			JOptionPane.showMessageDialog(mFrame, Strings.NOTFOUNDCOVER);
			return null;
		}
		ArrayList<String> msgInBits = textToBits(msg);
		String res = "";
		int count = 0, zeros = 0;
		int state = 1;
		
		for (String s : msgInBits) {
			res += s;
		}
		
		BufferedImage image = mInsModel.getCover();
		int height = image.getHeight();
		int width = image.getWidth();

		for(int i =0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				Color c = new Color(image.getRGB(j, i));
				Integer red = c.getRed();
				Integer green = c.getGreen();
				Integer blue = c.getBlue();
				
				switch(state) {
					case 1:
						String temp1 = getBits(red.byteValue());
						if (temp1.charAt(0) != res.charAt(count)) {
							temp1 = replace(temp1, res.charAt(count));
							red = byteToInt(temp1);
						}
						count++;
						if (count == res.length()) state = 2;
						break;
					case 2:
						String temp2 = getBits(red.byteValue());
						temp2 = replace(temp2, '0');
						red = byteToInt(temp2);
						zeros++;
						if (zeros == 8) state = 3;
				}
				
				switch(state) {
				case 1:
					String temp1 = getBits(green.byteValue());
					if (temp1.charAt(0) != res.charAt(count)) {
						temp1 = replace(temp1, res.charAt(count));
						green = byteToInt(temp1);
					}
					count++;
					if (count == res.length()) state = 2;
					break;
				case 2:
					String temp2 = getBits(green.byteValue());
					temp2 = replace(temp2, '0');
					green = byteToInt(temp2);
					zeros++;
					if (zeros == 8) state = 3;
				}
				
				switch(state) {
				case 1:
					String temp1 = getBits(blue.byteValue());
					if (temp1.charAt(0) != res.charAt(count)) {
						temp1 = replace(temp1, res.charAt(count));
						blue = byteToInt(temp1);
					}
					count++;
					if (count == res.length()) state = 2;
					break;
				case 2:
					String temp2 = getBits(blue.byteValue());
					temp2 = replace(temp2, '0');
					blue = byteToInt(temp2);
					zeros++;
					if (zeros == 8) state = 3;
				}
				
				Color newColor = new Color(red, green, blue);
				image.setRGB(j,i,newColor.getRGB());
				
			}
			
		}
		
		mInsModel.setStego(image);
		JOptionPane.showMessageDialog(mFrame, Strings.SCSIMPORT);
		return image;
	}
	
	public String extraction() {
		if (mExtModel.getStego() == null) {
			JOptionPane.showMessageDialog(mFrame, Strings.NOTFOUNDSTEGO);
			return null;
		}
		BufferedImage image = mExtModel.getStego();
		int height = image.getHeight();
		int width = image.getWidth();
		String res = "", foo = "";
		
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				Color c = new Color(image.getRGB(j, i));
				Integer red = c.getRed();
				Integer green = c.getGreen();
				Integer blue = c.getBlue();
				
				String temp = getBits(red.byteValue());
				foo += temp.charAt(0);
				temp = getBits(green.byteValue());
				foo += temp.charAt(0);
				temp = getBits(blue.byteValue());
				foo += temp.charAt(0);
				
				char ch;
				if (foo.length() > 8) {
					if (byteToInt(foo) == 0) {
						return res;
					}
					ch = charValue(foo.substring(0, 8));
					res += ch;
					foo = foo.substring(8);
				}
				else if (foo.length() == 8) {
					if (byteToInt(foo) == 0) {
						return res;
					}
					ch = charValue(foo.substring(0, 8));
					res += ch;
					foo = "";
				}
				
				
			}
		}
		JOptionPane.showMessageDialog(mFrame, Strings.MISTAKE);
		return null;
		

	}
	
	private ArrayList<String> textToBits(String text) {
		byte[] temp = text.getBytes();
		ArrayList<String> bits = new ArrayList<>();
		
		for(byte b: temp)
			bits.add(getBits(b));
		
		return bits;
		
	}
	
	private String getBits(byte b){
		String result = "";
	    for(int i = 0; i < 8; i++)
	    	result += (b & (1 << i)) == 0 ? "0" : "1";
	    return result;
	}
	
	private String replace(String str, char c) {
		String res = "";
		res += c;
		return res.concat(str.substring(1));
		
	}
	
	private int byteToInt(String str) {
		int value = 0;
		for(int i =0; i < 8; i++) {
			if (str.charAt(i) == '1') value += Math.pow(2, i);
		}
		
		return value;
	}
	
	private char charValue(String str){
		int intValue = byteToInt(str);
		return (char) intValue;
		
	}

	
	public void saveStego() {
		BufferedImage img = mInsModel.getStego();
		if (img == null) {
			JOptionPane.showMessageDialog(mFrame, Strings.NOTFOUNDSTEGO);
			return ;
		}
		String name = mInsModel.getName();
		File outputFile = new File(Strings.FILEPATH+'/'+ name +"_stego.bmp");
		try {
			ImageIO.write(img, "bmp", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(mFrame, Strings.SCSSAVED);
	}
	
	public void reset(boolean isIns) {
		if (isIns) mInsModel.reset();
		else mExtModel.reset();
	}

}
