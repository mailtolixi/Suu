package com.nnsuu.suu.skins;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.opengl.GLUtils;

import com.nnsuu.suu.other.LoadMode;
import com.nnsuu.suu.Suu;
import com.nnsuu.suu.SuuActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.microedition.khronos.opengles.GL10;

public class SuuSkins implements Skins {
    HashMap<String,TextureFilepath> skins;
    HashMap<String,ArrayList<Bitmap>> bitmaps;
    Fonts suuFonts;

    class TextureFilepath{
        int textureID;
        String filepath;
        TextureFilepath(int textureID, String filepath){
            this.textureID = textureID;
            this.filepath = filepath;
        }
    }

    public SuuSkins(){
        skins = new HashMap<>();
        bitmaps = new HashMap<>();
        suuFonts = new SuuFonts();
    }

    @Override
    public int loadActorBitmap(String key, String filepath, int divideX, int divideY, LoadMode loadMode) {
        if(havaSkin(key)){
            return skins.get(key).textureID;
        } else {
            if(!this.bitmaps.containsKey(filepath)) {
                Bitmap bitmap = null;
                if (loadMode == LoadMode.SDCard) {
                    bitmap = BitmapFactory.decodeFile(filepath);
                } else {
                    try {
                        bitmap = BitmapFactory.decodeStream(Suu.app.getResources().getAssets().open(filepath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }



                ArrayList<Bitmap> bitmaps = new ArrayList<>();
                if (divideX * divideY > 1) {
                    for (int j = 0; j < divideY; j++) {
                        for (int i = 0; i < divideX; i++) {
                            bitmaps.add(Bitmap.createBitmap(bitmap,
                                    bitmap.getWidth() / divideX * i,
                                    bitmap.getHeight() / divideY * j,
                                    bitmap.getWidth() / divideX,
                                    bitmap.getHeight() / divideY));
                        }
                    }
                    bitmap.recycle();
                } else {
                    bitmaps.add(bitmap);
                }

                this.bitmaps.put(filepath,bitmaps);
            }

            int textureIDs[] = new int[1];
            Suu.gl.glGenTextures(1, textureIDs, 0);
            skins.put(key,new TextureFilepath(textureIDs[0],filepath));

            return bindTexture(textureIDs[0],key);
        }
    }

    @Override
    public void updateActorBitmap(String key, String filepath, int divideX, int divideY, LoadMode loadMode) {
        Bitmap bitmap = null;
        if (loadMode == LoadMode.SDCard) {
            bitmap = BitmapFactory.decodeFile(filepath);
        } else {
            try {
                bitmap = BitmapFactory.decodeStream(Suu.app.getResources().getAssets().open(filepath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        getSkin(key).clear();

        if(divideX*divideY>1){
            for (int j = 0; j < divideY; j++){
                for (int i = 0; i < divideX; i++){
                    getSkin(key).add(Bitmap.createBitmap(bitmap,
                            bitmap.getWidth()/divideX*i,
                            bitmap.getHeight()/divideY*j,
                            bitmap.getWidth()/divideX,
                            bitmap.getHeight()/divideY));
                }
            }
            bitmap.recycle();
        }else {
            getSkin(key).add(bitmap);
        }
    }

    @Override
    public int loadTextBitmap(String key,String text, int linecount, int size,int line, int color, String ttfpath, LoadMode loadMode) {
        if(havaSkin(key)){
            return getTextureID(key);
        } else {
            if(!this.bitmaps.containsKey(key)) {
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
                if (linecount >= text.length()) {
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

                ArrayList<Bitmap> bitmaps = new ArrayList<>();
                bitmaps.add(bitmap);
                this.bitmaps.put(key,bitmaps);
            }
            int textureIDs[] = new int[1];
            Suu.gl.glGenTextures(1, textureIDs, 0);
            skins.put(key,new TextureFilepath(textureIDs[0],key));
            return bindTexture(textureIDs[0],key);
        }
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
        if(linecount>=text.length()){
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
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        bitmaps.add(bitmap);
        this.bitmaps.put(key,bitmaps);
    }

    @Override
    public int loadButtonBitmap(String key, String filepath,int height, int divideX, int divideY, String text, int size, int color, String ttfpath, LoadMode loadMode) {
        if(havaSkin(key)){
            return getTextureID(key);
        } else {
            Bitmap bitmap = null;
            if (loadMode == LoadMode.SDCard) {
                bitmap = BitmapFactory.decodeFile(filepath);
            } else {
                try {
                    bitmap = BitmapFactory.decodeStream(Suu.app.getResources().getAssets().open(filepath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(!this.bitmaps.containsKey(key)) {

                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setColor(color);
                if(ttfpath != null){
                    suuFonts.addTTF(ttfpath,loadMode);
                    paint.setTypeface(suuFonts.getTTF(ttfpath));
                }
                paint.setTextSize(size*bitmap.getHeight()/divideY/height);
                ArrayList<Bitmap> bitmaps = new ArrayList<>();
                Rect rect = new Rect();
                paint.getTextBounds(text, 0, text.length(), rect);
                int textWidth = rect.width();
                int textHeight = rect.height();
                rect = null;
                if (divideX * divideY > 1) {

                    for (int j = 0; j < divideY; j++) {
                        for (int i = 0; i < divideX; i++) {
                            Bitmap nbitmap = Bitmap.createBitmap(bitmap,
                                    bitmap.getWidth() / divideX * i,
                                    bitmap.getHeight() / divideY * j,
                                    bitmap.getWidth() / divideX,
                                    bitmap.getHeight() / divideY);
                            Canvas canvas = new Canvas(nbitmap);
                            canvas.drawText(text, (nbitmap.getWidth() - textWidth) / 2,
                                    (nbitmap.getHeight() + textHeight / 3 * 2) / 2, paint);

                            bitmaps.add(nbitmap);
                        }
                    }
                    bitmap.recycle();
                } else {
                    Canvas canvas = new Canvas(bitmap);
                    canvas.drawText(text, (bitmap.getWidth() - textWidth) / 2,
                            (bitmap.getHeight() + textHeight) / 2, paint);
                    bitmaps.add(bitmap);
                }
                this.bitmaps.put(key, bitmaps);
            }
            int textureIDs[] = new int[1];
            Suu.gl.glGenTextures(1, textureIDs, 0);
            skins.put(key,new TextureFilepath(textureIDs[0],key));
            return bindTexture(textureIDs[0],key);
        }
    }

    @Override
    public void updateButtonBitmap(String key, String filepath,int height, int divideX, int divideY, String text, int size, int color, String ttfpath, LoadMode loadMode) {
        Bitmap bitmap = null;
        if (loadMode == LoadMode.SDCard) {
            bitmap = BitmapFactory.decodeFile(filepath);
        } else {
            try {
                bitmap = BitmapFactory.decodeStream(Suu.app.getResources().getAssets().open(filepath));
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

        getSkin(key).clear();
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        int textWidth = rect.width();
        int textHeight = rect.height();
        rect = null;
        if(divideX*divideY>1){

            for (int j = 0; j < divideY; j++){
                for (int i = 0; i < divideX; i++){
                    Bitmap nbitmap = Bitmap.createBitmap(bitmap,
                            bitmap.getWidth()/divideX*i,
                            bitmap.getHeight()/divideY*j,
                            bitmap.getWidth()/divideX,
                            bitmap.getHeight()/divideY);
                    Canvas canvas = new Canvas(nbitmap);
                    canvas.drawText(text,(nbitmap.getWidth()-textWidth)/2,
                            (nbitmap.getHeight()+textHeight)/2, paint);

                    getSkin(key).add(nbitmap);
                }
            }
            bitmap.recycle();
        } else {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawText(text,(bitmap.getWidth()-textWidth)/2,
                    (bitmap.getHeight()+textHeight)/2, paint);
            getSkin(key).add(bitmap);
        }
    }

    @Override
    public int bindTexture(int textureID,String key){
        Suu.gl.glBindTexture(GL10.GL_TEXTURE_2D, textureID);
        Suu.gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        Suu.gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, (Bitmap) getSkin(key).get(0), 0);
        return textureID;
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
    public ArrayList getSkin(String key) {
        return bitmaps.get(skins.get(key).filepath);
    }

    @Override
    public void clearSkin(String key) {
        skins.remove(key);
    }

    @Override
    public void clearBitmap(String key) {
        bitmaps.remove(key);
    }

    @Override
    public void clearAll() {
        int textureIDs[] = new int[skins.size()];
        for (int i = skins.size(); i > 0; i--){
            textureIDs[skins.size()-i] = i;
        }
        Suu.gl.glDeleteTextures(skins.size(),textureIDs,0);

        skins = new HashMap<>();
        bitmaps = new HashMap<>();
    }
}
