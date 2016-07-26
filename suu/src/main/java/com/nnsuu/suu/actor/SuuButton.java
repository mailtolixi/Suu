package com.nnsuu.suu.actor;

import com.nnsuu.suu.other.LoadMode;
import com.nnsuu.suu.Suu;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.opengles.GL10;

public class SuuButton extends Actor{

    String text,ttfpath;
    int size,color;

    public SuuButton(String key, String filepath, int x, int y, int width, int height, int divideX, int divideY,
                     String text, int size, int color, String ttfpath, LoadMode loadMode){
        this.key = key;
        this.filepath = filepath;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.divideX = divideX;
        this.divideY = divideY;

        this.text = text;
        this.size = size;
        this.color = color;
        this.ttfpath = ttfpath;
        this.rotate = 0;
        this.sb = 1;
        this.loadMode = loadMode;
    }


    @Override
    public void op() {
        playOP = 0;
        playID = 0;
        playED = divideX * divideY;
        playSpeed = 6;
        textureID = Suu.skins.loadButtonBitmap(key,filepath,height,divideX,divideY,text,size,color,ttfpath,loadMode);
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
                0f,  1f/divideY,
                1f/divideX,  1f/divideY,
                0f,  0f,
                1f/divideX,  0f});
        indices.position(0);
        vertices.position(0);
        texture.position(0);

        Suu.gl.glBindTexture(GL10.GL_TEXTURE_2D, textureID);
        Suu.gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);
        Suu.gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texture);
    }

    @Override
    public void play() {
        if (playID/playSpeed == playED-1){
            playID = playOP;
        }else{
            playID++;
        }

    }

    public void setXYid(int xid, int yid){
        texture.clear();
        texture.put(new float[] {
                1f/divideX*(xid-1),  1f/divideY*yid,
                1f/divideX*xid,  1f/divideY*yid,
                1f/divideX*(xid-1),  1f/divideY*(yid-1),
                1f/divideX*xid,  1f/divideY*(yid-1)});
        vertices.position(0);
        texture.position(0);
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
