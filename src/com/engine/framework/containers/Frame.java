package com.engine.framework.containers;

import com.engine.framework.Image;

public class Frame {
	
	public Image image;
	public boolean reverseX = false;
	public boolean reverseY = false;
	public Vector2d offset = new Vector2d(0,0);
	
	public Frame(Image image) 
	{
		this.image = image;
	}
	
	public Frame(Image image, boolean reverseX, boolean reverseY)
	{
		this(image);
		this.reverseX = reverseX;
		this.reverseY = reverseY;
	}
	
	public Frame(Image image, boolean reverseX, boolean reverseY, Vector2d offset)
	{
		this(image, reverseX, reverseY);
		this.offset = offset;
	}
	
}
