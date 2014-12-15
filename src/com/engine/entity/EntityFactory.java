package com.engine.entity;

import com.engine.game.Entity;
import com.engine.game.components.Button;
import com.engine.game.components.Position;
import com.engine.game.components.TileMapComponent;
import com.engine.game.screens.World;
import com.engine.maps.TileMap;
import com.engine.utilities.ButtonClickListener;

public class EntityFactory {

	public EntityFactory() {}

	public static Entity createButtonEntity(final World world, final String name, final ButtonClickListener listener) {
			return new EntityManager.EntityBuilder(world.getEntityManager()).createEntity()
				.withComponent(new Position(world.getGame(), 100,100))
				.withComponent(new Button(world.getGame(), name, listener))
				.build();
	}
	
	public static Entity createTileMap(World world) {
		return new EntityManager.EntityBuilder(world.getEntityManager()).createEntity()
				.withComponent(new TileMapComponent(world.getGame(), TileMap.tileMaps.get(0).getTiles()))
				.build();
	}
}
