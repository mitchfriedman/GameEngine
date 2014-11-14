package com.engine.components;

import com.engine.framework.containers.Rect;
import com.engine.framework.containers.Vector2d;
import com.engine.game.GameEngine;

public class Position extends Component {
	
	private Rect bounds;
	
	public Position(GameEngine game, final double x, final double y) {
		super(game);
		bounds = new Rect(new Vector2d(x, y), new Vector2d(0, 0));
	}
	
	public void setPosition(final Vector2d position) {
		bounds.setPosition(position);
	}
	
	public Vector2d getPosition() {
		return bounds.getRealPosition();
	}
	
	public void setSize(int width, int height) {
		bounds.setSize(new Vector2d(width, height));
	}

	public Rect getBounds() {
		return bounds;
	}

	public void setBounds(Rect bounds) {
		this.bounds = bounds;
	}
}
