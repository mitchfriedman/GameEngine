package com.engine.assets;

import android.content.res.XmlResourceParser;
import android.os.AsyncTask;

import com.engine.framework.Audio;
import com.engine.framework.Graphics;
import com.engine.framework.implementation.AndroidGame;
import com.engine.game.R;
import com.engine.maps.TileMap;
import com.engine.maps.TileTemplate;

import java.io.IOException;

public class LoadAssetsTask extends AsyncTask<AndroidGame, Void, Void> {

	@Override
	protected Void doInBackground(AndroidGame... args) {
		AndroidGame game = args[0];
        XmlResourceParser xrp;

        //Loads assets
		xrp = game.getResources().getXml(R.xml.asset_list);
		Graphics g = game.getGraphics();
		Audio a = game.getAudio();
		Assets.loadFromXML(xrp, g, a);
		xrp.close();

        //Loads levels
        //xrp = game.getResources().getXml(R.xml.level_list);
        //LevelProperties.loadLevels(xrp);
        //xrp.close();

		//Load tile definitions
		xrp = game.getResources().getXml(R.xml.tiles);
		TileTemplate.loadTileTemplates(xrp);
		xrp.close();

		//Load the tile map
		//xrp = game.getResources().getXml(R.xml.level_list);
		try {
			TileMap.createMapTemplate("tile_map");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//xrp.close();

		return null;
	}

}
