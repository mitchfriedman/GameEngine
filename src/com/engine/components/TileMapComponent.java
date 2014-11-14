package com.engine.components;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.engine.framework.Graphics;
import com.engine.framework.Image;
import com.engine.framework.containers.Vector2d;
import com.engine.framework.implementation.AndroidGame;
import com.engine.game.GameEngine;
import com.engine.game.screens.Camera;
import com.engine.utilities.Assets;
import com.engine.utilities.TileMap;
import com.engine.utilities.TileTemplate;

public class TileMapComponent extends Component {
	
    public static final int TILE_WIDTH = 20;
    public static final int TILE_HEIGHT = 20;
    
    public static final int MAP_HEIGHT = AndroidGame.SCREEN_HEIGHT * 2;
    public static final int MAP_WIDTH =  AndroidGame.SCREEN_WIDTH  * 2;
    
	private int[][] tileMap;
	
	private boolean hasDrawn;
	    
    private Camera camera;
    private Graphics g;
    
    private Map<Integer, Image> images;
    private Map<Integer, Vector2d> cordinates;
	
	public TileMapComponent(GameEngine game, int[][] tileMap) {
		super(game);
		this.tileMap = tileMap;
		
        this.g = game.getGraphics();
        camera = game.getWorld().getCamera();

        images = new HashMap<Integer, Image>();
        cordinates = new HashMap<Integer, Vector2d>();
        
        configureTiles();
        hasDrawn = false;
	}

	private void configureTiles() {
		for(int i=0; i<tileMap.length; i++) {
			for(int j=0; j<tileMap[i].length; j++) {
				if(!images.containsKey(tileMap[i][j])) {
					images.put(tileMap[i][j], Assets.getImage(TileTemplate.tileTemplates.get(tileMap[i][j])));
				}
				
				cordinates.put(generateMapKey(i,j), new Vector2d(j * TILE_WIDTH, i * TILE_HEIGHT));
			}
		}
		Log.d("MAP","Configuration Completed");
	}
	
	private int generateMapKey(int row, int col) {
		return row * TileMap.TILES_PER_COL + col;
	}

    @Override
    public void onUpdate(double deltaTime) {
    }
    
    @Override
    public void onPaint(double deltaTime) {
    	if(camera.cameraUpdated() || !hasDrawn) {
    		Vector2d offset = camera.getOffset();
    		
			for(int i=0; i<tileMap.length; i++) {
				for(int j=0; j<tileMap[i].length; j++) { 
					int tile = tileMap[i][j];
					if(tile == 0) break;
					
					Vector2d screenPosition = cordinates.get(generateMapKey(i,j)).subtract(offset);
					
					g.drawImage(images.get(tile), screenPosition, 
						false, false, camera.getScale());
					
				}
			}
			hasDrawn = true;
    	}
    }
}
