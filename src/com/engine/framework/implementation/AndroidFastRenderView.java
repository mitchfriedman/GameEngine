package com.engine.framework.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AndroidFastRenderView extends SurfaceView implements Runnable {
	
	public static final int DELTA_TIME_SCALE = 100;
	
    private AndroidGame game;
    private Bitmap framebuffer;
    private Thread renderThread = null;
    private SurfaceHolder holder;
    private volatile boolean running = false;
    
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
    
    public void run() {
        Rect destRect = new Rect();
        long startTime = System.nanoTime();
        while(running) {  
            if(!holder.getSurface().isValid())
                continue;           
            
            float deltaTime = (System.nanoTime()-startTime) / 1000000000.0f;
            startTime = System.nanoTime();

            game.getCurrentScreen().update(deltaTime*DELTA_TIME_SCALE);
            game.getCurrentScreen().paint(deltaTime*DELTA_TIME_SCALE);
            
            render(destRect);
        }
    }
    
    private void render(Rect destRect) {
    	Canvas canvas = holder.lockCanvas();
        canvas.getClipBounds(destRect);
        canvas.drawBitmap(framebuffer, null, destRect, null);                           
        holder.unlockCanvasAndPost(canvas);
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
}