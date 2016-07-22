package com.centerm.model.menu;

public class Sreenshot {
	private String mchntCd;
	
	private String path;
	
	private Double x;
	
	private Double y;
	
	private Double width;
	
	private Double height;
	
	public Double getX() {
		return x;
	}

	public Double getY() {
		return y;
	}

	public Double getWidth() {
		return width;
	}

	public Double getHeight() {
		return height;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMchntCd() {
		return mchntCd;
	}

	public void setMchntCd(String mchntCd) {
		this.mchntCd = mchntCd;
	}
}
