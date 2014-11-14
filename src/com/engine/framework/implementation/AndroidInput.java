package com.engine.framework.implementation;

import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build.VERSION;
import android.view.View;

import com.engine.framework.Input;
import com.engine.framework.containers.Vector2d;

public class AndroidInput implements Input, SensorEventListener {    
    private TouchHandler touchHandler;
    private Vector2d tilt = new Vector2d(0,0);
    private SensorManager sensorManager;
    private float[] gravity;
    private float[] geomagnetic;

	public AndroidInput(Context context, View view, float scaleX, float scaleY) {
        if(VERSION.SDK_INT < 5) 
            touchHandler = new SingleTouchHandler(view, scaleX, scaleY);
        else
            touchHandler = new MultiTouchHandler(view, scaleX, scaleY); 
        
        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        registerListeners();
    }
    
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) { }
	
	@Override
	public void onSensorChanged(SensorEvent event) 
	{
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			gravity = event.values;
		}
		
		if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
			geomagnetic = event.values;
		}
		
		updateTilt();
	}
	
	private void registerListeners()
	{
		if (sensorManager != null
					&& sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null
					&& sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
			sensorManager.registerListener(this, 
        		sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 
        		SensorManager.SENSOR_DELAY_GAME);
			sensorManager.registerListener(this, 
        		sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), 
        		SensorManager.SENSOR_DELAY_GAME);
		}
	}
	
	private void unregisterListeners()
	{
		sensorManager.unregisterListener(this);
	}
	
	public void updateTilt()
	{
		if (gravity == null || geomagnetic == null)
			return;
		
		float[] r = new float[9];
		float[] i = new float[9];
		if (!SensorManager.getRotationMatrix(r, i, gravity, geomagnetic))
			return;
		
		float[] orientation = SensorManager.getOrientation(r, new float[3]);
		tilt = new Vector2d(Math.toDegrees(orientation[2]), -Math.toDegrees(orientation[1]));
		
		tilt.x += 180; // gets x in range 0 - 359
		if (tilt.x < 90 || tilt.x > 270) {
    		tilt.y = 180 - tilt.y;
    	}
		tilt.y += 90;
	}
	
	public Vector2d getTilt()
	{
		return tilt;
	}
	
    public float GetTiltY() {
    	return (float) tilt.y;
    }
    public float GetTiltX() {
    	return (float) tilt.x;
    }
    @Override
    public boolean isTouchDown(int pointer) {
        return touchHandler.isTouchDown(pointer);
    }
    @Override
    public int getTouchX(int pointer) {
        return touchHandler.getTouchX(pointer);
    }
    @Override
    public int getTouchY(int pointer) {
        return touchHandler.getTouchY(pointer);
    }
    @Override
    public List<TouchEvent> getTouchEvents() {
        return touchHandler.getTouchEvents();
    }
    
    @Override
    public void onPause() {
    	unregisterListeners();
    }
    
    @Override
    public void onResume() {
    	registerListeners();
    }
}
