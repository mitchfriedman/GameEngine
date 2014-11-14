package com.engine.framework;

import android.graphics.Bitmap;

import com.engine.framework.Graphics.ImageFormat;
import com.engine.framework.containers.Vector2d;

public interface Image {
    public int getWidth();
    public int getHeight();
    public Vector2d getSize();
    public ImageFormat getFormat();
    public Bitmap getBitmap();
    public void dispose();
}
