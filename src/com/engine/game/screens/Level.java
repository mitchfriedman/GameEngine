package com.engine.game.screens;

import com.engine.entity.EntityFactory;
import com.engine.game.GameEngine;

public class Level extends BaseScreen {
	
	private Camera camera;

	public Level(GameEngine game) {
		super(game);
		camera = new Camera(game.getWorld());
	}
	
	@Override
	public void initialize() {
		EntityFactory.createTileMap(world);
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		camera.update();
	}
	
	@Override
	public void paint(float deltaTime) {
		super.paint(deltaTime);
	}
}
