package com.engine.framework.containers;

public class Align {

	public enum Horizontal {
		LEFT,
		CENTER,
		RIGHT
	}
	public enum Vertical {
		BOTTOM,
		CENTER,
		TOP
	}
	Vertical vertical;
	Horizontal horizontal;
	
	public Align(Vertical vertical, Horizontal horizontal) {
		this.vertical = vertical;
		this.horizontal = horizontal;
	}
	
	public Vertical getVertical() {
		return vertical;
	}
	
	public void setVertical(Vertical vertical) {
		this.vertical = vertical;
	}
	
	public Horizontal getHorizontal() {
		return horizontal;
	}
	
	public void setHorizontal(Horizontal horizontal) {
		this.horizontal = horizontal;
	}
}
