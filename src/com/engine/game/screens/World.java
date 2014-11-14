package com.engine.game.screens;

import com.engine.game.GameEngine;
import com.engine.game.managers.EntityManager;

public class World {

	private EntityManager entityManager;
	private GameEngine game;
	
	private Camera camera;
	
	public World(GameEngine game) {
		this.game = game;
		game.setWorld(this);
		
	    entityManager = new EntityManager(this);
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public void update(float deltaTime) {
		entityManager.update(deltaTime);
	}

	public void paint(float deltaTime) {
		entityManager.paint(deltaTime);
	}	
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public void pause() {
		entityManager.pause();
	}

	public void resume() {	
		entityManager.resume();
	}

	public void dispose() {}

	public void backButton() {	
		entityManager.pause();
	}

	public GameEngine getGame() {
		return game;
	}
}
