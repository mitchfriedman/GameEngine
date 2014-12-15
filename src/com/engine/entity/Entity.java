package com.engine.game;

import java.util.HashMap;
import java.util.Map;

import com.engine.game.components.Component;

public class Entity {

	public static final int DEFAULT_DEPTH = 1;

	private Map<Class<? extends Component>, Component> components;
	
	public Entity() {
		components = new HashMap<Class<? extends Component>, Component>();
	}
	
	public void update(float deltaTime) {
		for(Component component : components.values()) {
			component.onUpdate(deltaTime);
		}
	}
	
	public void paint(float deltaTime) {
		for(Component component : components.values()) {
			component.onPaint(deltaTime);
		}
	}
	
	public void onEntityCreation() {
		for(Component component : components.values()) {
			component.onEntityCreationCompletion();
		}
	}
	
	public void addComponent(Component component) {
		component.setEntity(this);
		components.put(component.getClass(), component);
		component.onComponentAdded();
	}
	
	@SuppressWarnings("unchecked")
	public<T extends Component> T getComponent(final Class<? extends Component> cls) {
		return (T)components.get(cls);
	}
	
	public void onRemoved() {
		components.clear();
	}
}