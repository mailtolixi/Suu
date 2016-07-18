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
        Suu.skins.updateButtonBitmap(key,filepath,height,divideX,divideY,text,size,color,ttfpath,loadMode);
    }

    public void update(String filepath, int x, int y, int width, int height, int divideX, int divideY,
                       String text, int size, int color, String ttfpath, LoadMode loadMode){
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
        Suu.skins.updateButtonBitmap(key,filepath,height,divideX,divideY,text,size,color,ttfpath,loadMode);
    }

    @Override
    public void play() {
        if (playID/playSpeed == playED-1){
            playID = playOP;
        }else{
            playID++;
        }

    }

    @Override
    public void draw() {
        Suu.gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);
        Suu.gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texture);
        if(updateFlag){
            updateTexture();
            updateFlag = false;
        }else{
            Suu.gl.glBindTexture(GL10.GL_TEXTURE_2D, textureID);
        }
        Suu.gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        Suu.gl.glPushMatrix();
        Suu.gl.glRotatef(rotate,0,0,1);
        Suu.gl.glTranslatef(x,y,0);
        Suu.gl.glScalef(width*sb,height*sb,1);
        Suu.gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 6,GL10.GL_UNSIGNED_SHORT, indices);
        Suu.gl.glPopMatrix();
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
