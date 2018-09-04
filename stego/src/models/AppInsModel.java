package models;

import java.awt.image.BufferedImage;

public class AppInsModel {
	
	private BufferedImage mCover, mStego;
	private String mName;
	
	public AppInsModel() {
		mCover = null;
		mStego = null;
		
	}

	public BufferedImage getCover() {
		return mCover;
	}

	public void setCover(BufferedImage mCover) {
		this.mCover = mCover;
	}

	public BufferedImage getStego() {
		return mStego;
	}

	public void setStego(BufferedImage mStego) {
		this.mStego = mStego;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}
	
	public void reset() {
		mName = "";
		mCover = null;
		mStego = null;
	}

}
