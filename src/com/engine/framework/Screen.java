package com.engine.framework;

import com.engine.game.GameEngine;

public abstract class Screen {
    protected final GameEngine game;

    public Screen(GameEngine game) {
        this.game = game;
    }

    public abstract void update(float deltaTime);
    
	public abstract void paint(float deltaTime);
	
    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();
    
	public abstract void backButton();
	
	public GameEngine getMeteorDefence()
	{
		return game;
	}


}
