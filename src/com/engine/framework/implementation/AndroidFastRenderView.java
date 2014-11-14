package com.engine.framework.implementation;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("ViewConstructor")
public class AndroidFastRenderView extends SurfaceView implements Runnable {
    public AndroidGame game;
    public Bitmap framebuffer;
    public Thread renderThread = null;
    public SurfaceHolder holder;
    volatile boolean running = false;
    
    public static final float FPS = 35.0f;
    public static final float UPS = 60.0f;
    
    final double TIME_BETWEEN_UPDATES = 1000000000/UPS;
    final double TIME_BETWEEN_RENDERS = 1000000000/FPS;
    final double MAX_UPDATES_BEFORE_RENDER = 5;
    
    
    public long startTime;
    public long lastUpdate;
    public long lastRender;
    long now;
    
    public AndroidFastRenderView(AndroidGame game, Bitmap framebuffer) {
        super(game);
        this.game = game;
        this.framebuffer = framebuffer;
        this.holder = getHolder();
    }
    
    public void resume() { 
        running = true;
        renderThread = new Thread(this);
        renderThread.start();  
    }      
    
    @SuppressWarnings("unused")
	public void run() {
    	now = System.nanoTime();
        startTime  = now;
        lastUpdate = startTime;
        lastRender = startTime;
        
        int updateCount = 0;
        int frameCount = 0;
        long lastTime = now;
        long time = 0;
        int fps = 0;
        int ups = 0;
        short updates = 0;
        
        while(running) {  
            if(!holder.getSurface().isValid())
                continue;
            
            now = System.nanoTime();
            time += now-lastTime;
            lastTime = now;
            
            if (time > 1000000000) {
    			time -= 1000000000;
    			fps = frameCount;
    			ups = updateCount;
    			updateCount = 0;
    			frameCount = 0;
    			//Log.d("FPS:UPS", fps + ":"+ups);
    		}
            
            now = System.nanoTime();
            updates = 0;
            while (now - lastUpdate > TIME_BETWEEN_UPDATES && updates < MAX_UPDATES_BEFORE_RENDER) {
            	
            	game.getCurrentScreen().update((now - lastUpdate)/1000000);
            	
            	updates++;
            	lastUpdate += TIME_BETWEEN_UPDATES;
            	updateCount++; // for FPS
            }
            
            if ( now - lastUpdate > TIME_BETWEEN_UPDATES)
            {
               lastUpdate = (long) (now - TIME_BETWEEN_UPDATES);
               Log.d("Fast Render View", "Updating took too long. Catching up.");
            }
            
            drawGame();
            lastRender = now;
            frameCount++;
            
            while (now - lastRender < TIME_BETWEEN_RENDERS && now - lastUpdate < TIME_BETWEEN_RENDERS) {
            	//Thread.yield();
            	
            	// This stops the app from consuming lots of CPU. Might cause stuttering.
            	//try {Thread.sleep(1);} catch(Exception e) {}
            	now = System.nanoTime();
            }
        }
    }
    
    public void pause() {                        
        running = false;                        
        while(true) {
            try {
                renderThread.join();
                break;
            } catch (InterruptedException e) {
                // retry
            }   
        }
    }     
    
    void drawGame()
    {
    	game.getCurrentScreen().paint((now - lastRender)/1000000);
        Canvas canvas = holder.lockCanvas();
        canvas.drawBitmap(framebuffer, null, canvas.getClipBounds(), null);                           
        holder.unlockCanvasAndPost(canvas);
    }
  
}