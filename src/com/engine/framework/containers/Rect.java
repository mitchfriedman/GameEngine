package com.engine.framework.containers;

import android.util.Log;


public class Rect {
	public double x, y;
	public double width, height;
	
	public Rect(double x, double y, double width, double height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Rect(Vector2d position, Vector2d size)
	{
		this.x = position.x;
		this.y = position.y;
		this.width = size.x;
		this.height = size.y;
	}
	
	public Rect(Rect rect)
	{
		this.x = rect.x;
		this.y = rect.y;
		this.width  = rect.width;
		this.height = rect.height;
	}
	
	public Vector2d getRealPosition()
	{
		return new Vector2d(x,y);
	}
	
	public Vector2i getIntPosition()
	{
		return new Vector2i(x,y);
	}
	
	public void setPosition(Vector2d position)
	{
		this.x = position.x;
		this.y = position.y;
	}
	
	public Rect scalePosition(float scale)
	{
		Rect rect = new Rect(this);
		rect.x *= scale;
		rect.y *= scale;
		return rect;
	}
	
	public Rect scaleSize(float scale)
	{
		Rect rect = new Rect(this);
		rect.width  *= scale;
		rect.height *= scale;
		return rect;
	}
	
	public Rect scaleValues(float scale)
	{
		return scaleSize(scale).scalePosition(scale);
	}
	
	public double getHeight() 
	{
		return height;
	}
	
	public double getWidth() 
	{
		return width;
	}
	
	public Vector2d getRealSize()
	{
		return new Vector2d(width,height);
	}
	
	public Vector2i getIntSize()
	{
		return new Vector2i(width,height);
	}
	
	public void setSize(Vector2d size)
	{
		this.width = size.x;
		this.height = size.y;
	}
	
	public android.graphics.RectF getAndroidRectF()
	{
		return new android.graphics.RectF( (float)x, (float)y, (float)width, (float)height);
	}
	
	public android.graphics.Rect getAndroidRect()
	{
		return new android.graphics.Rect( (int)x, (int)y, (int)width, (int)height);
	}
	
	public android.graphics.Rect getAndroidRectOffseted()
	{
		return new android.graphics.Rect( (int)x, (int)y, (int)(width+x), (int)(height+y));
	}
	
	public boolean isOutsideRect(Rect rect)
	{
		boolean isOutsideX = x < rect.x || x > rect.width-width;
		boolean isOutsideY = y < rect.y || y > rect.height-height;
		Log.d("Outside", isOutsideX + " " +  isOutsideY);
		return isOutsideX || isOutsideY;
	}
	
	public static boolean rectCollides(Rect a, Rect b)
	{
		return !(a.y+a.height < b.y ||
				 a.y > b.y+b.height ||
				 a.x > b.x+b.width  ||
				 a.x+a.width < b.x);
	}
	
	@Override
	public String toString() {
		return "{ " + x + ", " + y + " | " + width + ", " + height + " }";
	}
	
	public static boolean vectorWithinRect(Vector2d vec, Rect rect)
	{
		boolean withinX = vec.x <= rect.x + rect.width  && vec.x >= rect.x;
		boolean withinY = vec.y <= rect.y + rect.height && vec.y >= rect.y;
		
		return withinX && withinY;
	}
	
}
