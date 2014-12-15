package com.engine.game.components;

import com.engine.framework.Input;
import com.engine.framework.containers.Rect;
import com.engine.framework.containers.Vector2d;
import com.engine.game.Entity;
import com.engine.game.GameEngine;

public abstract class Component {
	
	protected Entity entity;
	protected GameEngine game;
	
	public Component(GameEngine game) {
		this.game = game;
	}
	
	public Component(GameEngine game, Entity entity) {
		this(game);
		this.entity = entity;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
    public void onComponentAdded() {}
    public void onEntityCreationCompletion() {}
    public void onPaint(double deltaTime) {}
    public void onUpdate(double deltaTime) {}
    
    public boolean isTouched(Rect bounds) {
		Input input = game.getInput();
		
		if (input.isTouchDown(0)) {
			Vector2d touchPos = new Vector2d(input.getTouchX(0), input.getTouchY(0));
			Rect touchBox = new Rect(bounds);
			return Rect.vectorWithinRect(touchPos, touchBox);
		} else {
			return false;
		}
	}	
}
