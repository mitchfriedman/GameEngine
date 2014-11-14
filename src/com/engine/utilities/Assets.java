package com.engine.utilities;

import java.io.IOException;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;
import android.util.Log;

import com.engine.framework.Audio;
import com.engine.framework.Graphics;
import com.engine.framework.Image;
import com.engine.framework.Music;
import com.engine.framework.Sound;
import com.engine.framework.Graphics.ImageFormat;

public class Assets {
	
	static boolean loaded = false;
	static HashMap<String, Image> images = new HashMap<String, Image>();
	static HashMap<String, Sound> sounds = new HashMap<String, Sound>();
	static HashMap<String, Music> music = new HashMap<String, Music>();
	
	public static void loadFromXML(XmlResourceParser xrp, Graphics g, Audio a)
	{
		while (true) {
			try {
				int eventType = xrp.next();
				if (eventType == XmlResourceParser.END_DOCUMENT) {
					break;
				}
				if (eventType != XmlResourceParser.START_TAG) {
					continue;
				}
				
			} catch (XmlPullParserException e) {
				e.printStackTrace();
				Log.d("XmlPullParserException", e.getLocalizedMessage());
				continue;
			} catch (IOException e) {
				e.printStackTrace();
				Log.d("IOException", e.getLocalizedMessage());
				continue;
			}
			
			String type   = xrp.getName();
			String key    = xrp.getAttributeValue(null, "key");
			String path   = xrp.getAttributeValue(null, "path");
			String locale = xrp.getAttributeValue(null, "locale");
			
			
			if (key == null || path == null) {
				Log.d("XML Asset Error", "Key or path is null.");
			}
			else if (type.equals("image")) {
				loadImage(key, path, locale, g);
			}
			else if (type.equals("sound")) {
				loadSound(key, path, locale, a);		
			}
			else if (type.equals("music")) {
				loadMusic(key, path, locale, a);
			}
			else {
				Log.d("XML Asset Error", "No tag with name " + type);
			}
		}
		
		loaded = true;
	}
	
	public static boolean loadImage(String key, String path, String locale, Graphics g)
	{
		if ( (locale == null && !images.containsKey(key)) ||  getLocale().equals(locale) ) {
			Image image = g.newImage(path, ImageFormat.RGB565);
			images.put(key, image);
			return image != null;
		}
		
		return false;
	}
	
	public static boolean loadSound(String key, String path, String locale, Audio a)
	{
		
		if ( (locale == null && !images.containsKey(key)) ||  getLocale().equals(locale) ) {
			Sound sound = a.createSound(path);
			sounds.put(key, sound);
			return sound != null;
		}
		
		return false;
	}
	
	public static boolean loadMusic(String key, String path, String locale, Audio a)
	{
		
		if ( (locale == null && !images.containsKey(key)) ||  getLocale().equals(locale) ) {
			Music sound = a.createMusic(path);
			music.put(key, sound);
			return sound != null;
		}
		
		return false;
	}
	
	public static boolean isLoaded()
	{
		return loaded;
	}
	
	public static Image getImage(String name)
	{
		return images.get(name);
	}
	
	public static Sound getSound(String name)
	{
		return sounds.get(name);
	}
	
	public static Music getMusic(String name)
	{
		return music.get(name);
	}
	
	public static String getLocale()
	{
		return "en_us";
	}
	
}
