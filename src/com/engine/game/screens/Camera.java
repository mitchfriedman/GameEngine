package com.engine.game.screens;

import android.graphics.Color;

import com.engine.components.TileMapComponent;
import com.engine.framework.Graphics;
import com.engine.framework.Input;
import com.engine.framework.containers.Rect;
import com.engine.framework.containers.Vector2d;

public class Camera {
	
	private int mapWidth;
	private int mapHeight;
	
	private int viewWidth;
	private int viewHeight;
	
	private Rect bounds;
	
	private boolean lastTouched;
	private Vector2d lastTouchPos;
	
	
	private boolean lastScaled;
	private Vector2d lastTouch1;
	private Vector2d lastTouch2;
	
	
	private Input input;
	private Graphics g;
	
	private float scale;
	
	private boolean hasChanged;
	
	public Camera(World world) {
		world.setCamera(this);
		input = world.getGame().getInput();
		g = world.getGame().getGraphics();
		
		viewWidth = world.getGame().getGraphics().getWidth();
		viewHeight = world.getGame().getGraphics().getHeight();
		
		mapWidth = TileMapComponent.MAP_WIDTH;
		mapHeight = TileMapComponent.MAP_HEIGHT;
		
		hasChanged = true;
		
		initializeOffset();
		scale = 1.0f;
	}
	
	private void initializeOffset() {
		bounds = new Rect(mapWidth  / 2 - viewWidth / 2, 
				mapHeight / 2 - viewWidth / 2,
				viewWidth, viewHeight);
	}
	
	public Rect getViewport() {
		return new Rect(bounds);
	}
	
	public Vector2d getOffset() {
		return new Vector2d(bounds.x, bounds.y);
	}
	
	public void update() {
		boolean touched = input.isTouchDown(0);
		boolean secondTouch = input.isTouchDown(1);
		
		if(!touched) {
			lastScaled = false;
			lastTouched = false;
			hasChanged = false;
		} else if(touched && !secondTouch){
			Vector2d touchPos = new Vector2d(input.getTouchX(0), input.getTouchY(0));
			if(lastTouched) {
				calculateOffset(touchPos.subtract(lastTouchPos));
			}
			
			lastTouchPos = touchPos;
			lastTouched = true;
			hasChanged = true;
		} else if(touched && secondTouch) {
			Vector2d touchPos1 = new Vector2d(input.getTouchX(0), input.getTouchY(0));
			Vector2d touchPos2 = new Vector2d(input.getTouchX(1), input.getTouchY(1));
			
			if(lastScaled) {
				calculateScale(touchPos1, touchPos2);
			}
			
			lastTouch1 = touchPos1;
			lastTouch2 = touchPos2;
			lastScaled = true;
			hasChanged = true;
			
			g.drawRect(new Rect(0,0, bounds.width, bounds.height), Color.BLACK);
		}
	}
	
	public boolean isTouched() {
		return lastTouched;
	}
	
	public Vector2d getTouchPosWorldCordinates() {
		return lastTouchPos.add(new Vector2d(bounds.x, bounds.y));
	}
	
	private void calculateScale(Vector2d touch1, Vector2d touch2) {
		double distance1 = Math.sqrt((touch1.x - touch2.x) * (touch1.x - touch2.x) 
 				+ (touch1.y - touch2.y) * (touch1.y - touch2.y));
		
		double distance2 = Math.sqrt((lastTouch2.x - lastTouch1.x) * (lastTouch2.x - lastTouch1.x) 
						 + (lastTouch2.y - lastTouch1.y) * (lastTouch2.y - lastTouch1.y));
		
		double delta = distance1 - distance2;
		
		if(distance1 < distance2 && scale < 1.5) {
			scale -= delta * 0.0005;
		} else if(distance1 > distance2 && scale > 0.5) {
			scale += -delta * 0.0005;
		}
	}
	
	public float getScale() {
		return scale;
	}
		
	public boolean onCamera(Vector2d screenCordinates) {
		return Rect.vectorWithinRect(screenCordinates, bounds);
	}
	
	public boolean cameraUpdated() {
		return hasChanged;
	}
	
	private void calculateOffset(Vector2d delta) {
		
		bounds.x += delta.x;
		bounds.y += delta.y;
		
		if(bounds.x < 0) bounds.x = 0;
		else if(bounds.x + bounds.width > mapWidth) bounds.x = mapWidth - bounds.width;
		if(bounds.y < 0) bounds.y = 0;
		else if(bounds.y + bounds.height > mapHeight) bounds.y = mapHeight - bounds.height;
		
		g.drawRect(new Rect(0,0, bounds.width, bounds.height), Color.BLACK);
		
	}
}
