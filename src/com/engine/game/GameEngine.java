package com.engine.game;

import android.os.Bundle;

import com.engine.framework.Screen;
import com.engine.framework.implementation.AndroidGame;
import com.engine.game.screens.Loading;
import com.engine.game.screens.World;
import com.engine.utilities.Assets;
import com.engine.utilities.LoadAssetsTask;

public class GameEngine extends AndroidGame {
	
	//public static final float MAX_SENSITIVITY = 2.0f;
	//public static final float MIN_SENSITIVITY = 0.3f;
	//public static final float DEFAULT_SENSITIVITY = 1;
    public static final boolean SHOW_COLLIDER_BOXES = false;
    public static final boolean DEBUG = true;
    //public static boolean muted = false;
    
    private World world;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public Screen getInitScreen() {
		world = new World(this);
		if (!Assets.isLoaded()) {
			new LoadAssetsTask().execute(this);
		}
		
		return new Loading(this);
	}
	
	public void setScreenClearEntities(BaseScreen screen) {
		world.getEntityManager().removeAllEntities();
		setScreen(screen);
	}
	
	public void setScreen(BaseScreen screen) {
		super.setScreen(screen);
		screen.initialize();
	}
	
	public void setWorld(final World world) {
		this.world = world;
	}
	
	public World getWorld() {
		return world;
	}
	
	
}
