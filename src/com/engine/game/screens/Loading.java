package com.engine.game.screens;

import com.engine.assets.Assets;
import com.engine.framework.Graphics;
import com.engine.framework.Image;
import com.engine.game.GameEngine;

public class Loading extends BaseScreen {
	
	private final static double SPLASH_FADE_OUT_TIME = 160;
	private final static double SPLASH_FADE_OUT_AT = 100;
	private double elapsedTime = 0;
	private double opacity = 0;
	private Image splash;
	
	private Graphics g;
	
	public Loading(GameEngine game) {
		super(game);
		
		g = game.getGraphics();
		Assets.loadImage("moon", "moon.png", null, g);
		splash = Assets.getImage("moon");
	}

	@Override
	public void update(float deltaTime) 
	{
		elapsedTime += deltaTime;
		
		if (elapsedTime > SPLASH_FADE_OUT_AT) {
			double percentage = (elapsedTime-SPLASH_FADE_OUT_AT) / SPLASH_FADE_OUT_TIME;
			percentage = percentage > 1 ? 1 : percentage;
			opacity = 255 * percentage;
		}
		
		if (Assets.isLoaded() && elapsedTime > SPLASH_FADE_OUT_AT + SPLASH_FADE_OUT_TIME) {
			game.setScreen(new Menu(game));
		}
		
		paint(deltaTime);
	}
	
	@Override
	public void paint(float deltaTime) {
		g.drawARGB( 255, 0, 0, 0);
		g.drawImage(splash, g.getWidth()/2-splash.getWidth()/2, g.getHeight()/2-splash.getHeight()/2);
		g.drawARGB( (int) opacity, 0, 0, 0);
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {}

	@Override
	public void backButton() {}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

}
