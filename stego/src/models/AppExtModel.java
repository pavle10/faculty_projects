package models;

import java.awt.image.BufferedImage;

public class AppExtModel {
	
	private BufferedImage mStego;

	public AppExtModel() {
		mStego = null;
		
	}

	public BufferedImage getStego() {
		return mStego;
	}

	public void setStego(BufferedImage mStego) {
		this.mStego = mStego;
	}
	
	public void reset() {
		mStego = null;
	}

}
