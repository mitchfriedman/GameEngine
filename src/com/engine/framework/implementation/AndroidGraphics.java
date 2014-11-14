package com.engine.framework.implementation;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import com.engine.framework.Graphics;
import com.engine.framework.Image;
import com.engine.framework.containers.Rect;
import com.engine.framework.containers.Vector2d;

public class AndroidGraphics implements Graphics {
	
    AssetManager assets;
    Bitmap frameBuffer;
    Canvas canvas;
    Paint paint;
    Matrix matrix = new Matrix();
    
    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) 
    {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
    }
    
    @Override
    public Image newImage(String fileName, ImageFormat format) 
    {
        Config config = null;
        if (format == ImageFormat.RGB565)
            config = Config.RGB_565;
        else if (format == ImageFormat.ARGB4444)
            config = Config.ARGB_4444;
        else
            config = Config.ARGB_8888;

        Options options = new Options();
        options.inPreferredConfig = config;
        options.inScaled = false;
        
        InputStream in = null;
        Bitmap bitmap = null;
        try {
            in = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(in, null, options);
            if (bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '"
                        + fileName + "'");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '"
                    + fileName + "'");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }

        if (bitmap.getConfig() == Config.RGB_565)
            format = ImageFormat.RGB565;
        else if (bitmap.getConfig() == Config.ARGB_4444)
            format = ImageFormat.ARGB4444;
        else
            format = ImageFormat.ARGB8888;

        return new AndroidImage(bitmap, format);
    }

    @Override
    public void clearScreen(int color) {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
                (color & 0xff));
    }


    @Override
    public void drawLine(int x, int y, int x2, int y2, int color) {
        paint.setColor(color);
        canvas.drawLine(x, y, x2, y2, paint);
    }

    @Override
    public void drawRect(int x, int y, int width, int height, int color) 
    {
        drawRect(new Rect(x,y,width,height), color);
    }
    
    public void drawRect(Rect rect, int color)
    {
    	rect.scaleValues(AndroidGame.SCALE);
    	paint.setColor(color);
    	paint.setStyle(Style.FILL);
    	canvas.drawRect(rect.scaleValues(AndroidGame.SCALE).getAndroidRectOffseted(), paint);
    }
    
    @Override
    public void drawARGB(int a, int r, int g, int b) {
       paint.setStyle(Style.FILL);
       canvas.drawARGB(a, r, g, b);
    }
    
    @Override
    public void drawString(String text, int x, int y, Paint paint) {
    	canvas.drawText(text, x, y, paint);
    }
    
    @Override
    public void drawImage(Image image, Vector2d pos, Rect src, boolean reverseX, boolean reverseY) 
    {
    	matrix.reset();
    	// translates and scales to src position and size
    	//matrix.setTranslate(  (float) src.x, (float) src.y );
    	//matrix.setScale(      (float) (src.getWidth() / image.getWidth()), (float) (src.getHeight() / image.getHeight()) );
    	
    	Rect dest = new Rect(0,0,0,0);
    	dest.x = (pos.x + (reverseX ? src.getWidth()  : 0)) * AndroidGame.SCALE;
    	dest.y = (pos.y + (reverseY ? src.getHeight() : 0)) * AndroidGame.SCALE;
    	dest.width  = reverseX ? -AndroidGame.SCALE : AndroidGame.SCALE; // We're mathing the scale here, not actual pixel size.
    	dest.height = reverseY ? -AndroidGame.SCALE : AndroidGame.SCALE;
    	
    	// translates and scales to position on screen. Scales automatically to game scale size. Scales from center of image.
    	matrix.setScale( (float) (dest.width), (float) (dest.height));
    	matrix.postTranslate( (float) dest.x, (float) dest.y );
    	
    	
    	canvas.drawBitmap(((AndroidImage)image).bitmap, matrix, null);
    }
    
    @Override
    public void drawImage(Image image, Vector2d pos, boolean reverseX, boolean reverseY, float scale) {
    	matrix.reset();
    	// translates and scales to src position and size
    	//matrix.setTranslate(  (float) src.x, (float) src.y );
    	//matrix.setScale(      (float) (src.getWidth() / image.getWidth()), (float) (src.getHeight() / image.getHeight()) );
    	
    	Rect dest = new Rect(0,0,0,0);
    	dest.x = pos.x * scale;//(pos.x + (reverseX ? src.getWidth()  : 0)) * AndroidGame.SCALE;
    	dest.y = pos.y * scale;//(pos.y + (reverseY ? src.getHeight() : 0)) * AndroidGame.SCALE;
    	dest.width  = scale; // We're mathing the scale here, not actual pixel size.
    	dest.height = scale;
    	
    	// translates and scales to position on screen. Scales automatically to game scale size. Scales from center of image.
    	matrix.setScale( (float) (dest.width), (float) (dest.height));
    	matrix.postTranslate( (float) dest.x, (float) dest.y );
    	
    	
    	canvas.drawBitmap(((AndroidImage)image).bitmap, matrix, null);
    }
    
    @Override
    public void drawImage(Image image, Vector2d pos, boolean reverseX,
    		boolean reverseY) 
    {
    	Rect src = new Rect(0,0, image.getWidth(), image.getHeight());
    	drawImage(image, pos, src, reverseX, reverseY);    	
    }
    
    @Override
    public void drawImage(Image image, Vector2d pos, Rect src) 
    {
    	drawImage(image, pos, src, false, false);
    }
    
    @Override
    public void drawImage(Image image, int x, int y, Rect src) 
    {
    	drawImage(image, new Vector2d(x,y), src);
    }
    
    @Override
    public void drawImage(Image image, Vector2d pos) 
    {
    	drawImage(image, pos, false, false);
    }
    
    @Override
    public void drawImage(Image image, double x, double y) 
    {
    	drawImage(image, new Vector2d(x, y));
    }

    public int getWidth() {
        return (int)Math.ceil(frameBuffer.getWidth() / AndroidGame.SCALE);
    }

    public int getHeight() {
        return (int)Math.ceil(frameBuffer.getHeight() / AndroidGame.SCALE);
    }

    public Vector2d getSize()
    {
    	return new Vector2d(getWidth(), getHeight());
    }
    
    public Canvas getCanvas() 
    {
    	return canvas;
    }
}
