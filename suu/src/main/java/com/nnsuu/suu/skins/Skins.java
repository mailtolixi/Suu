package com.nnsuu.suu.skins;

import android.graphics.Bitmap;
import android.graphics.Paint;

import com.nnsuu.suu.other.LoadMode;

import java.util.ArrayList;

public interface Skins {

    int loadActorBitmap(String key, String filepath, int divideX, int divideY, LoadMode loadMode);
    void updateActorBitmap(String key, String filepath, int divideX, int divideY, LoadMode loadMode);
    int loadTextBitmap(String key, String text, int linecount, int size, int line, int color, String ttfpath, LoadMode loadMode);
    void updateTextBitmap(String key, String text, int linecount, int size, int line, int color, String ttfpath, LoadMode loadMode);
    int loadButtonBitmap(String key, String filepath, int height, int divideX, int divideY, String text, int size, int color, String ttfpath, LoadMode loadMode);
    void updateButtonBitmap(String key, String filepath, int height, int divideX, int divideY, String text, int size, int color, String ttfpath, LoadMode loadMode);
    int bindTexture(int textureID, String key);

    boolean havaSkin(String key);
    int getTextureID(String key);
    ArrayList getSkin(String key);
    void clearSkin(String key);
    void clearBitmap(String key);
    void clearAll();
}
