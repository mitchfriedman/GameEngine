package com.engine.game.screens;

import com.engine.game.BaseScreen;
import com.engine.game.EntityFactory;
import com.engine.game.GameEngine;
import com.engine.utilities.ButtonClickListener;

public class Menu2 extends BaseScreen {

	ButtonClickListener embarkListener  = new ButtonClickListener() {
		@Override
		public void onUp() {
			game.setScreenClearEntities(new Level(game));
		}
	};
	
	public Menu2(GameEngine game) {
		super(game);
		createButtons();
	}
	
	@Override
	public void initialize() {}
	
	private void createButtons() {
		EntityFactory.createButtonEntity(world, "Buttons/embark", embarkListener);
	}
}
