package com.engine.framework.containers;

public class Vector2i {
	
	public static final Vector2i ZERO = new Vector2i(0,0);
	
	public int x, y;
	
	public Vector2i(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2i(double x, double y)
	{
		this.x = (int)x;
		this.y = (int)y;
	}
	
	public Vector2d asDouble()
	{
		return new Vector2d(x,y);
	}
}
