package com.engine.components;

import com.engine.framework.Graphics;
import com.engine.framework.Image;
import com.engine.game.GameEngine;
import com.engine.utilities.Assets;
import com.engine.utilities.ButtonClickListener;

public class Button extends Component {
	
	private ButtonClickListener listener;
	
	private Position position;

	boolean lastTouched = false;
	boolean lastScreenTouched = false;
	boolean wasCanceled = false;
	boolean down = false;

	private Image toggledImage;
	private Image pressedImage;
	private Image activeImage;
	private Image currentImage;

	private Graphics g;
	
	private boolean toggled = false;
	
	public Button(GameEngine game, final String name, final ButtonClickListener listener) {// ButtonClickListener listener){
		super(game);
		this.listener = listener;

		this.g = game.getGraphics();

		activeImage  = Assets.getImage(name+"-active");
		pressedImage = Assets.getImage(name+"-hover");
		toggledImage = Assets.getImage(name+"-toggled");
		
		if (toggledImage == null) {
			toggledImage = activeImage;
		}
		
		resetImage();
	}
	
	public Image getImage() {
		return currentImage;
	}
	
	public void setToggled(boolean toggled){
		this.toggled = toggled;
	}
	
	public void resetImage() {
		currentImage = toggled ? toggledImage : activeImage;
		onUp();
	}
	
	@Override
	public void onUpdate(double deltaTime) {
		boolean touched = isTouched(position.getBounds()); //offset
		boolean screenTouched = game.getInput().isTouchDown(0);
		
		if (touched && !lastTouched && (!lastScreenTouched || wasCanceled)) {
			listener.onDown();
			onDown();
			
			down = true;
			lastTouched = true;
		}
		else if (lastTouched && !touched && screenTouched) {
			listener.onCancel();
			onCancel();
			
			wasCanceled = true;
			down = false;
			lastTouched = false;
		}
		else if (lastTouched && !touched) {
			listener.onUp();
			onUp();
			
			down = false;
			lastTouched = false;
		}
		
		if (!screenTouched && lastScreenTouched) {
			lastScreenTouched = false;
			wasCanceled = false;
		}
		else if (screenTouched && !lastScreenTouched) {
			lastScreenTouched = true;
		}
	}
	
	@Override
	public void onPaint(double deltaTime) {
		g.drawImage(currentImage, position.getPosition());
	}
	
	void onDown() {
		currentImage = pressedImage;
	}
	
	void onUp() {
		setToggled(!toggled);
	}
	
	void onCancel() {
		onUp();
	}
	
	@Override
	public void onObjectCreationCompletion() {
		try {
			position = getEntity().getComponent(Position.class);
			position.setSize(currentImage.getWidth(), currentImage.getHeight());
		} catch(Exception e) {
			//couldn't get the component, did you add it BEFORE?
		}
		
	}
}
