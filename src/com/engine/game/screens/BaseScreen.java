package com.engine.game.screens;

import com.engine.framework.Screen;
import com.engine.game.GameEngine;

public abstract class BaseScreen extends Screen {

	protected GameEngine game;
	protected World world;
	
	public BaseScreen(GameEngine game) {
		super(game);
		this.game = game;
		this.world = game.getWorld();
	}

	@Override
	public void update(float deltaTime) {
		world.update(deltaTime);
	}
	
	public abstract void initialize();
	
	@Override
	public void paint(float deltaTime) {
		world.paint(deltaTime);
	}

	@Override
	public void pause() {
		world.pause();
	}

	@Override
	public void resume() {
		world.resume();
	}

	@Override
	public void dispose() {
		world.dispose();
	}

	@Override
	public void backButton() {
		world.backButton();
	}
}
