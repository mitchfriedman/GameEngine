package com.engine.framework;

import java.util.List;

import com.engine.framework.containers.Vector2d;

public interface Input {
    
    public static class TouchEvent {
        public static final int TOUCH_DOWN = 0;
        public static final int TOUCH_UP = 1;
        public static final int TOUCH_DRAGGED = 2;
        public static final int TOUCH_HOLD = 3;

        public int type;
        public int x, y;
        public int pointer;


    }

    public boolean isTouchDown(int pointer);
    public int getTouchX(int pointer);
    public int getTouchY(int pointer);
    public List<TouchEvent> getTouchEvents();
    public Vector2d getTilt();
	public float GetTiltY();
	public float GetTiltX();
	public void onResume();
	public void onPause();
}
 