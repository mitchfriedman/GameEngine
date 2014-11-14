package com.engine.game.screens;

import com.engine.components.TileMapComponent;
import com.engine.game.BaseScreen;
import com.engine.game.Entity;
import com.engine.game.GameEngine;
import com.engine.utilities.TileMap;

public class Level extends BaseScreen {
	
	private Camera camera;

	public Level(GameEngine game) {
		super(game);
		camera = new Camera(game.getWorld());
		
	}
	
	@Override
	public void initialize() {
		createTiles();
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

	private void createTiles() {
		Entity entity = game.getWorld().getEntityManager().createEntity();
		entity.addComponent(new TileMapComponent(game, TileMap.tileMaps.get(0).getTiles()));
	}
}
