package com.engine.game.screens;

import android.graphics.Color;

import com.engine.framework.Graphics;
import com.engine.framework.Input;
import com.engine.framework.containers.Rect;
import com.engine.framework.containers.Vector2d;
import com.engine.game.components.TileMapComponent;
import com.engine.framework.containers.MathHelper;
import com.engine.game.screens.World;

public class Camera {
	
	private static final float DEFAULT_SCALE = 1.0f;
	
	private static final double SCALE_FACTOR = 0.0005f;
	
	private int mapWidth = TileMapComponent.MAP_WIDTH;
	private int mapHeight = TileMapComponent.MAP_HEIGHT;
	
	private int viewWidth;
	private int viewHeight;
	
	private float scale;
	
	private Rect bounds;
	
	int deltaX = 1, deltaY = 1;
	
	//general (single touch & multi-touch)
	private boolean lastTouched;
	private boolean lastScaled;
	
	private boolean hasChanged;
	
	//single touch vector
	private Vector2d lastTouchPos;
	
	//multi touch handling vectors
	private Vector2d lastTouchMulti1;
	private Vector2d lastTouchMulti2;
	
	private Input input;
	private Graphics graphics;
	
	public Camera(World world) {
		world.setCamera(this);
		input = world.getGame().getInput();
		graphics = world.getGame().getGraphics();
		
		scale = DEFAULT_SCALE;
		viewWidth = world.getGame().getGraphics().getWidth();
		viewHeight = world.getGame().getGraphics().getHeight();
		
		hasChanged = true;
		
		initializeOffset();
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
		} else if(touched && !secondTouch) {
			//handle single touch
			Vector2d touchPos = new Vector2d(input.getTouchX(0), input.getTouchY(0));
			if(lastTouched) {
				calculateOffset(touchPos.subtract(lastTouchPos));
			}
			
			lastTouchPos = touchPos;
			lastTouched = true;
			hasChanged = true;
		} else if(touched && secondTouch) {
			//handle double-touch
			Vector2d touchPos1 = new Vector2d(input.getTouchX(0), input.getTouchY(0));
			Vector2d touchPos2 = new Vector2d(input.getTouchX(1), input.getTouchY(1));
			
			if(lastScaled) {
				calculateScale(touchPos1, touchPos2);
			}
			
			lastTouchMulti1 = touchPos1;
			lastTouchMulti2 = touchPos2;
			lastScaled = true;
			hasChanged = true;
			
			graphics.drawRect(new Rect(0,0, bounds.width, bounds.height), Color.BLACK);
		}
	}
	
	public boolean isTouched() {
		return lastTouched;
	}
	
	public Vector2d getTouchPosWorldCordinates() {
		return lastTouchPos.add(new Vector2d(bounds.x, bounds.y));
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
	
	private void calculateScale(Vector2d touch1, Vector2d touch2) {
		double distance1 = MathHelper.distanceBetweenVectors(touch1, touch2);
		double distance2 = MathHelper.distanceBetweenVectors(lastTouchMulti1, lastTouchMulti2);
		
		double delta = distance1 - distance2;
		
		if(distance1 < distance2 && scale < 1.5) {
			scale += delta * SCALE_FACTOR;
		} else if(distance1 > distance2 && scale > 0.5) {
			scale -= -delta * SCALE_FACTOR;
		}
	}
	
	private void calculateOffset(Vector2d delta) {
		
		bounds.x -= delta.x;
		bounds.y -= delta.y;
		
		if(bounds.x < 0) bounds.x = 0;
		else if(bounds.x + bounds.width > mapWidth) bounds.x = mapWidth - bounds.width;
		if(bounds.y < 0) bounds.y = 0;
		else if(bounds.y + bounds.height > mapHeight) bounds.y = mapHeight - bounds.height;
		
		//TODO: remove?
		graphics.drawRect(new Rect(0,0, bounds.width, bounds.height), Color.BLACK);
		
	}
}