package com.engine.framework.implementation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Vibrator;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

//import com.mitch.flyship.GoogleAPIManager;
import com.engine.framework.Audio;
import com.engine.framework.FileIO;
import com.engine.framework.Game;
import com.engine.framework.Graphics;
import com.engine.framework.Input;
import com.engine.framework.Screen;
import com.engine.framework.containers.Vector2d;

public abstract class AndroidGame extends Activity implements Game {
	
	public static AssetManager assets;
	public static float SCALE = 1.0f;
	
	public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 800;
	
    public AndroidFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    WakeLock wakeLock;
    Vector2d screenSize;
	
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        Display display = getWindowManager().getDefaultDisplay();
        screenSize = new Vector2d(display.getWidth(), display.getHeight());
        assets = getAssets();
        
        createFrameBuffer();
        
        fileIO = new AndroidFileIO(this);
        audio = new AndroidAudio(this);
        screen = getInitScreen();
        setContentView(renderView);
        
        
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Airship! At the Helm!");
    }
    
    public void createFrameBuffer() {  	
        Bitmap frameBuffer = Bitmap.createBitmap(SCREEN_WIDTH,
        		SCREEN_HEIGHT, Config.RGB_565);
        
        float scaleX = (float) (SCREEN_WIDTH / screenSize.x);
        float scaleY = (float) (SCREEN_HEIGHT / screenSize.y);

    	
    	renderView = new AndroidFastRenderView(this, frameBuffer);
    	graphics = new AndroidGraphics(getAssets(), frameBuffer);
    	input = new AndroidInput(this, renderView, scaleX/SCALE, scaleY/SCALE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { }

    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
        screen.resume();
        renderView.resume();
        input.onResume();
    }
    
    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();
        renderView.pause();
        screen.pause();
        input.onPause();
        
        if (isFinishing())
            screen.dispose();
    }

    @Override
    public Input getInput() {
        return input;
    }
    public void Vibrate(int time) {
    	Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    	v.vibrate(time);
    }
    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public Audio getAudio() {
    	
        return audio;
    }

    @Override
    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }
    
    public Screen getCurrentScreen() {
    	return screen;
    }
}