package com.engine.framework;


import android.graphics.Canvas;
import android.graphics.Paint;

import com.engine.framework.containers.Rect;
import com.engine.framework.containers.Vector2d;

public interface Graphics {
	public static enum ImageFormat {
		ARGB8888, ARGB4444, RGB565
	}

	public Image newImage(String fileName, ImageFormat format);

	public void clearScreen(int color);
	
	public void drawARGB(int i, int j, int k, int l);
	
	public void drawString(String text, int x, int y, Paint paint);
	
	// Draw Primitives
	public void drawLine(int x, int y, int x2, int y2, int color);

	public void drawRect(int x, int y, int width, int height, int color);
	
	public void drawRect(Rect rect, int color);
	
	public void drawImage(Image image, Vector2d pos, boolean reverseX, boolean reverseY, float scale);
	
	// Draw Image (scaled to game size)
	public void drawImage(Image image, Vector2d pos, Rect src, boolean reverseX, boolean reverseY);
	
	public void drawImage(Image image, Vector2d pos, boolean reverseX, boolean reverseY);
	
	public void drawImage(Image image, Vector2d pos, Rect src);
	
	public void drawImage(Image image, int x, int y, Rect src);
	
	public void drawImage(Image image, Vector2d pos);
	
	public void drawImage(Image image, double x, double y);

	// Get screen size (scaled to game size)

	public int getWidth();
	public int getHeight();

	//public Vector2d getSize();

	public Canvas getCanvas();
	
}
