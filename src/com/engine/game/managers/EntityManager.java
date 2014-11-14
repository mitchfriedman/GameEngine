package com.engine.game.managers;

import java.util.ArrayList;
import java.util.List;

import com.engine.components.Component;
import com.engine.game.Entity;
import com.engine.game.screens.World;

public class EntityManager {

	private World world;
	
	private List<Entity> entities;
	private List<Entity> add;
	private List<Entity> remove;
		
	public EntityManager(final World world) {
		entities = new ArrayList<Entity>();
		add = new ArrayList<Entity>();
		remove = new ArrayList<Entity>();
		this.world = world;
	}
	
	public Entity createEntity() {
		Entity entity = new Entity();
		add.add(entity);
		return entity;
	}
	
	public void removeAllEntities() {
		entities.clear();
		add.clear();
		remove.clear();
	}
	
	public void removeEntity(final Entity entity) {
		remove.add(entity);
	}
	
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	public void update(float deltaTime) {
		removeEntities();
		addEntities();		
		
		for(Entity entity : entities) {
			entity.update(deltaTime);
		}
	}
	
	public void paint(float deltaTime) {
		for(Entity entity : entities) {
			entity.paint(deltaTime);
		}
	}
	
	public static class EntityBuilder {
		
		private List<Component> components = new ArrayList<Component>();
		private EntityManager manager;
		private Entity entity;
		
		public EntityBuilder(EntityManager manager) {
			this.manager = manager;
		}
		
		public EntityBuilder withComponent(Component component) {
			components.add(component);
			return this;
		}
		
		public EntityBuilder createEntity() {
			entity = manager.createEntity();
			return this;
		}
		
		public Entity build() {
			for(Component component : components) {
				entity.addComponent(component);
			}
			for(Component component : components) {
				component.onObjectCreationCompletion();
			}
			return entity;
		}
	}
	
	public void pause() {
		
	}
	
	public void resume() {
		
	}
	
	private void addEntities() {
		for(Entity entity : add) {
			entities.add(entity);
			entity.onEntityCreation();
		}
		add.clear();
	}
	
	private void removeEntities() {
		for(Entity entity : remove) {
			entities.add(entity);
			entity.onRemoved();
		}
		remove.clear();
	}
}


