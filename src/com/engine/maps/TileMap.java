package com.engine.maps;

import android.util.Log;

import com.engine.framework.implementation.AndroidGame;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitch on 14-11-09.
 */
public class TileMap {
	
    public static final int TILES_PER_ROW = 150; //MAP_WIDTH / TILE_WIDTH; 
    public static final int TILES_PER_COL = 150; //MAP_HEIGHT / TILE_HEIGHT;

    public static List<TileMap> tileMaps;
    
    private int[][] tiles;

    public TileMap() {
        tiles = new int[TILES_PER_COL][TILES_PER_ROW];
    }

    public void addTile(int tile, int col, int row) {
    	tiles[row][col] = tile;
    }

    public int[][] getTiles() {
        return tiles;
    }

    public static void createMapTemplate(String fileName) throws IOException {
        tileMaps = new ArrayList<TileMap>();
        
        InputStream in = null;
        try {
            in = AndroidGame.assets.open(fileName);
        } catch (IOException e) {
            //couldn't open file
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        
        TileMap tileMap = new TileMap();
        
        int row = 0;
        while((line = reader.readLine()) != null) {
            String tiles[] = line.split(" ");
            for(int i=0; i<tiles.length; i++) {
                int tile = Integer.parseInt(tiles[i]);
                tileMap.addTile(tile, i, row);
            }
            row++;
        }
        tileMaps.add(tileMap);
        Log.d("TileMap", "Added TileMap");
    }
}
