package com.engine.game.screens;

import com.engine.entity.EntityFactory;
import com.engine.game.GameEngine;
import com.engine.utilities.ButtonClickListener;

public class Menu extends BaseScreen {

	ButtonClickListener embarkListener  = new ButtonClickListener() {
		@Override
		public void onUp() {
			game.setScreenClearEntities(new Level(game));
		}

		@Override
		public void onDown() {}

		@Override
		public void onCancel() {}
	};
	
	public Menu(GameEngine game) {
		super(game);
		createButtons();
	}
	
	@Override
	public void initialize() {}
	
	private void createButtons() {
		EntityFactory.createButtonEntity(world, "Buttons/embark", embarkListener);
	}
}
