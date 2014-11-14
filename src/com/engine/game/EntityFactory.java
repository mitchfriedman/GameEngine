package com.engine.game;

import com.engine.components.Button;
import com.engine.components.Position;
import com.engine.game.managers.EntityManager;
import com.engine.game.screens.World;
import com.engine.utilities.ButtonClickListener;

public class EntityFactory {

	public EntityFactory() {}

	public static Entity createButtonEntity(final World world, final String name, final ButtonClickListener listener) {
			return new EntityManager.EntityBuilder(world.getEntityManager()).createEntity()
				.withComponent(new Position(world.getGame(), 100,100))
				.withComponent(new Button(world.getGame(), name, listener))
				.build();
	}
}
