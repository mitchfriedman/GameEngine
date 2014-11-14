package com.engine.utilities;

import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mitch on 14-11-09.
 */
public class TileTemplate {

    public static Map<Integer,String> tileTemplates;

    private String image;

    public TileTemplate(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public static void loadTileTemplates(XmlResourceParser xrp) {
        while(true) {
            try {
                int eventType = xrp.next();
                if (eventType == XmlResourceParser.END_DOCUMENT) {
                    break;
                }
                if (eventType != XmlResourceParser.START_TAG) {
                    continue;
                }
            } catch(XmlPullParserException e) {
                e.printStackTrace();
                Log.d("XMLPullParserException", e.getLocalizedMessage());
                continue;
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("IOException", e.getLocalizedMessage());
                continue;
            }

            String tagName = xrp.getName();

            if(tagName.equals(XMLConstants.TILES)) {
                tileTemplates = new HashMap<Integer, String>();
                Log.d("TileTemplates", "Generating tile templates...");
            } else if(tagName.equals(XMLConstants.TILE)) {
                int key = Integer.parseInt(xrp.getAttributeValue(null, "key"));
                String image = xrp.getAttributeValue(null, "image");
                TileTemplate tile = new TileTemplate(image);
                tileTemplates.put(key, image);
            }
        }
        Log.d("TileTemplate", tileTemplates.size() + " Tiles Loaded");
    }
}
