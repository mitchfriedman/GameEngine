package com.engine.framework.implementation;

import android.graphics.Bitmap;

import com.engine.framework.Image;
import com.engine.framework.Graphics.ImageFormat;
import com.engine.framework.containers.Vector2d;

public class AndroidImage implements Image {
    Bitmap bitmap;
    ImageFormat format;
    
    public AndroidImage(Bitmap bitmap, ImageFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }
    
    @Override
    public Bitmap getBitmap() {
    	return bitmap;
    }
    
    @Override
    public Vector2d getSize() {
    	return new Vector2d(getWidth(), getHeight());
    }
    
    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    @Override
    public ImageFormat getFormat() {
        return format;
    }

    @Override
    public void dispose() {
        bitmap.recycle();
    }      
}
