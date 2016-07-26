package com.nnsuu.suu.actor;

import android.graphics.Bitmap;

import com.nnsuu.suu.other.LoadMode;
import com.nnsuu.suu.Suu;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.opengles.GL10;

public class SuuText extends Actor{

    String text;
    int linecount,size,line,color;

    public SuuText(String key, String text, int x, int y, int linecount, int size,int line, int color){
        this.key = key;
        this.text = text;
        this.x = x;
        this.y = y;
        this.linecount = linecount;
        this.size = size;
        this.line = line;
        this.color = color;
        this.filepath = null;
        this.rotate = 0;
        this.sb = 1;
        this.loadMode = LoadMode.Assert;
    }

    public SuuText(String key, String text, int x, int y, int linecount, int size,int line, int color, String ttfpath, LoadMode loadMode){
        this.key = key;
        this.text = text;
        this.x = x;
        this.y = y;
        this.linecount = linecount;
        this.size = size;
        this.line = line;
        this.color = color;
        this.filepath = ttfpath;
        this.rotate = 0;
        this.sb = 1;
        this.loadMode = loadMode;
    }


    @Override
    public void op() {
        playOP = 0;
        playID = 0;
        playSpeed = 6;
        textureID = Suu.skins.loadTextBitmap(key,text,linecount,size,line,color,filepath, loadMode);

        this.width=Suu.skins.getTextWidth();
        this.height=Suu.skins.getTextHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * 2 * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        vertices = byteBuffer.asFloatBuffer();
        vertices.put( new float[] {
                -0.5f,  -0.5f,
                0.5f,  -0.5f,
                -0.5f,  0.5f,
                0.5f,  0.5f});

        ByteBuffer indicesBuffer = ByteBuffer.allocateDirect(6 * 2);
        indicesBuffer.order(ByteOrder.nativeOrder());
        indices = indicesBuffer.asShortBuffer();
        indices.put(new short[] { 0, 1, 2, 1, 2, 3});

        ByteBuffer textureBuffer = ByteBuffer.allocateDirect(4 * 2 * 4);
        textureBuffer.order(ByteOrder.nativeOrder());
        texture = textureBuffer.asFloatBuffer();
        texture.put( new float[] {
                0f,  1f,
                1f,  1f,
                0f,  0f,
                1f,  0f});

        indices.position(0);
        vertices.position(0);
        texture.position(0);

        Suu.gl.glBindTexture(GL10.GL_TEXTURE_2D, textureID);
        Suu.gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);
        Suu.gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texture);
    }

    public void update(String text){
        this.text = text;
        Suu.skins.updateTextBitmap(key,text,linecount,size,line,color,filepath, loadMode);
        this.width=Suu.skins.getTextWidth();
        this.height=Suu.skins.getTextHeight();
    }

    public void update(String text, int x, int y, int linecount, int size, int line, int color, String filepath){
        this.text = text;
        this.x = x;
        this.y = y;
        this.linecount = linecount;
        this.size = size;
        this.line = line;
        this.color = color;
        this.filepath = filepath;
        Suu.skins.updateTextBitmap(key,text,linecount,size,line,color,filepath,loadMode);
        this.width=Suu.skins.getTextWidth();
        this.height=Suu.skins.getTextHeight();
    }

    @Override
    public void play() {
    }

    @Override
    public void touchDown() {

    }

    @Override
    public void touchMove(int x, int y) {

    }

    @Override
    public void touchUp(int x, int y) {

    }
}
