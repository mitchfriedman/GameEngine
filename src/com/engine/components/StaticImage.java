package com.engine.components;

import com.engine.framework.Graphics;
import com.engine.framework.Image;
import com.engine.game.GameEngine;
import com.engine.utilities.Assets;

public class StaticImage extends Component {
	
    private Image image;
    private boolean invertHorizontal;
    private boolean invertVertical;
    
    private Position position;
    private Graphics g;
    
    public StaticImage(GameEngine game) {
    	super(game);
    	this.g = game.getGraphics();
    }
    
    public StaticImage(GameEngine game, String imageID, boolean invertHorizontal, boolean invertVertical) {
    	this(game);
    	
    	this.image = Assets.getImage(imageID);
    	this.invertHorizontal = invertHorizontal;
    	this.invertVertical = invertVertical;
    }
    
    public void setImage(final Image image) {
    	this.image = image;
    }
    
    public boolean getInvertHorizontal() {
    	return invertHorizontal;
    }
    
    public boolean getInvertVertical() {
    	return invertVertical;
    }
    
    public Image getImage() {
    	return image;
    }
    
    @Override
    public void onPaint(double deltaTime) {
    	g.drawImage(image, position.getPosition());
    }
    
    @Override
    public void onObjectCreationCompletion() {
    	try {
    		position = getEntity().getComponent(Position.class);
    		position.setSize(image.getWidth(), image.getHeight());
    	} catch(Exception e) {
    		
    	}
    }
}
