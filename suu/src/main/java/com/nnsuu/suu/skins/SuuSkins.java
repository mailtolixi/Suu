package com.nnsuu.suu.skins;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.opengl.GLUtils;
import android.util.Log;

import com.nnsuu.suu.other.LoadMode;
import com.nnsuu.suu.Suu;
import com.nnsuu.suu.SuuActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;

public class SuuSkins implements Skins {
    HashMap<String,TextureFilepath> skins;
    ArrayList<Integer> clearSkins;
    Fonts suuFonts;
    int textWidth;
    int textHeight;

    class TextureFilepath{
        int textureID;
        String filepath;
        int count;
        TextureFilepath(int textureID, String filepath){
            this.textureID = textureID;
            this.filepath = filepath;
            count=1;
        }
    }

    public SuuSkins(){
        skins = new HashMap<>();
        clearSkins = new ArrayList<>();
        suuFonts = new SuuFonts();
    }

    @Override
    public int loadActorBitmap(String key, String filepath, int divideX, int divideY, LoadMode loadMode) {
        if(havaSkin(key)){
            skins.get(key).count++;
            return getTextureID(key);
        }

        Bitmap bitmap = null;
        if (loadMode == LoadMode.SDCard) {
            bitmap = BitmapFactory.decodeFile(filepath);
        } else {
            try {
                bitmap = BitmapFactory.decodeStream(Suu.app.getAssets().open(filepath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int textureIDs[] = new int[1];
        if(clearSkins.size()>0){
            textureIDs[0] = clearSkins.get(0);
            clearSkins.remove(0);
        }else{
            Suu.gl.glGenTextures(1, textureIDs, 0);
        }
        skins.put(key,new TextureFilepath(textureIDs[0],filepath));

        return bindTexture(textureIDs[0],bitmap);
    }
    @Override
    public int loadTextBitmap(String key,String text, int linecount, int size,int line, int color, String ttfpath, LoadMode loadMode) {
        if(havaSkin(key)){
            skins.get(key).count++;
            return getTextureID(key);
        }

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        if (ttfpath != null) {
            suuFonts.addTTF(ttfpath, loadMode);
            paint.setTypeface(suuFonts.getTTF(ttfpath));
        }
        paint.setTextSize(size);

        Rect rect = new Rect();
        int fontWidth;
        if (text.getBytes().length == text.length()) {
            paint.getTextBounds("S", 0, 1, rect);
            fontWidth = (int) paint.measureText("S");
        } else {
            paint.getTextBounds("酥", 0, 1, rect);
            fontWidth = (int) paint.measureText("酥");
        }
        int fontHeight = rect.height();
        rect = null;
        Bitmap bitmap;
        if (linecount >= text.length()||linecount<=0) {
            linecount = text.length();
            bitmap = Bitmap.createBitmap((int) paint.measureText(text),
                    (int) (fontHeight * 1.1),
                    Bitmap.Config.ARGB_4444);
        } else {
            bitmap = Bitmap.createBitmap(fontWidth * linecount,
                    (int) (fontHeight * (Math.ceil((float) text.length() / linecount) + 0.1)) + line * text.length() / linecount,
                    Bitmap.Config.ARGB_4444);
        }

        Canvas canvas = new Canvas(bitmap);
        for (int i = 0; i < (float) text.length() / linecount; i++) {
            canvas.drawText(text.substring(i * linecount,
                    ((i + 1) * linecount > text.length()) ? text.length() : ((i + 1) * linecount)),
                    0, (i + 1) * fontHeight + i * line, paint);
        }

        int textureIDs[] = new int[1];
        if(clearSkins.size()>0){
            textureIDs[0] = clearSkins.get(0);
            clearSkins.remove(0);
        }else{
            Suu.gl.glGenTextures(1, textureIDs, 0);
        }
        skins.put(key,new TextureFilepath(textureIDs[0],key));

        textWidth = bitmap.getWidth();
        textHeight = bitmap.getHeight();
        return bindTexture(textureIDs[0],bitmap);
    }

    @Override
    public void updateTextBitmap(String key,String text, int linecount, int size,int line, int color, String ttfpath, LoadMode loadMode) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        if(ttfpath != null){
            suuFonts.addTTF(ttfpath,loadMode);
            paint.setTypeface(suuFonts.getTTF(ttfpath));
        }
        paint.setTextSize(size);

        Rect rect = new Rect();
        int fontWidth;
        if (text.getBytes().length == text.length()){
            paint.getTextBounds("S", 0, 1, rect);
            fontWidth = (int) paint.measureText("S");
        }else{
            paint.getTextBounds("酥", 0, 1, rect);
            fontWidth = (int) paint.measureText("酥");
        }
        int fontHeight = rect.height();
        rect = null;
        Bitmap bitmap;
        if(linecount>=text.length()||linecount<=0) {
            linecount = text.length();
            bitmap = Bitmap.createBitmap((int) paint.measureText(text),
                    (int) (fontHeight*1.1),
                    Bitmap.Config.ARGB_4444);
        }else {
            bitmap = Bitmap.createBitmap(fontWidth*linecount,
                    (int)(fontHeight*(Math.ceil((float)text.length()/linecount)+0.1))+line*text.length()/linecount,
                    Bitmap.Config.ARGB_4444);
        }

        Canvas canvas = new Canvas(bitmap);
        for(int i = 0; i < (float)text.length()/linecount; i++){
            canvas.drawText(text.substring(i*linecount,
                    ((i+1)*linecount>text.length())?text.length():((i+1)*linecount)),
                    0, (i+1)*fontHeight+i*line, paint);
        }

        textWidth = bitmap.getWidth();
        textHeight = bitmap.getHeight();
        bindTexture(getTextureID(key),bitmap);
    }

    @Override
    public int loadButtonBitmap(String key, String filepath,int height, int divideX, int divideY, String text, int size, int color, String ttfpath, LoadMode loadMode) {
        if(havaSkin(key)){
            skins.get(key).count++;
            return getTextureID(key);
        }

        Bitmap bitmap = null;
        if (loadMode == LoadMode.SDCard) {
            bitmap = BitmapFactory.decodeFile(filepath).copy(Bitmap.Config.ARGB_8888, true);
        } else {
            try {
                bitmap = BitmapFactory.decodeStream(Suu.app.getAssets().open(filepath)).copy(Bitmap.Config.ARGB_8888, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        if(ttfpath != null){
            suuFonts.addTTF(ttfpath,loadMode);
            paint.setTypeface(suuFonts.getTTF(ttfpath));
        }
        paint.setTextSize(size*bitmap.getHeight()/divideY/height);
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        int textWidth = rect.width();
        int textHeight = rect.height();
        rect = null;
        if (divideX * divideY > 1) {
            Canvas canvas = new Canvas(bitmap);
            for (int j = 0; j < divideY; j++) {
                for (int i = 0; i < divideX; i++) {
                    canvas.drawText(text, (bitmap.getWidth() / divideX - textWidth) / 2 +
                                    bitmap.getWidth() / divideX * i,
                            (bitmap.getHeight() / divideY + textHeight / 3 * 2) / 2 +
                                    bitmap.getHeight() / divideY * j, paint);
                }
            }
        } else {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawText(text, (bitmap.getWidth() - textWidth) / 2,
                    (bitmap.getHeight() + textHeight) / 2, paint);
        }
        int textureIDs[] = new int[1];
        if(clearSkins.size()>0){
            textureIDs[0] = clearSkins.get(0);
            clearSkins.remove(0);
        }else{
            Suu.gl.glGenTextures(1, textureIDs, 0);
        }
        skins.put(key,new TextureFilepath(textureIDs[0],key));

        return bindTexture(textureIDs[0],bitmap);
    }

    @Override
    public int bindTexture(int textureID,Bitmap bitmap){
        Suu.gl.glBindTexture(GL10.GL_TEXTURE_2D, textureID);
        Suu.gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        Suu.gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
        return textureID;
    }

    @Override
    public int getTextWidth() {
        return textWidth;
    }

    @Override
    public int getTextHeight() {
        return textHeight;
    }

    @Override
    public boolean havaSkin(String filepath) {
        return skins.containsKey(filepath);
    }

    @Override
    public int getTextureID(String key) {
        return skins.get(key).textureID;
    }

    @Override
    public void clearSkin(String key) {
        skins.get(key).count--;
        if (skins.get(key).count<=0) {
            clearSkins.add(skins.get(key).textureID);
            skins.remove(key);
        }
    }

    @Override
    public void clearAll() {
        int textureIDs[] = new int[skins.size()+clearSkins.size()];
        for (int i = skins.size()+clearSkins.size(); i > 0; i--){
            textureIDs[skins.size()+clearSkins.size()-i] = i;
        }
        Suu.gl.glDeleteTextures(skins.size()+clearSkins.size(),textureIDs,0);
        skins.clear();
        clearSkins.clear();
    }
}
