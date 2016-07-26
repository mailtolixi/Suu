package com.nnsuu.suu.actor;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

import com.nnsuu.suu.Suu;
import com.nnsuu.suu.other.LoadMode;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public abstract class Actor implements ActorTouch{

    FloatBuffer vertices;
    FloatBuffer texture;
    ShortBuffer indices;
    int textureID;
    int playOP,playED,playID,playSpeed;

    String filepath,key;
    int x,y, width, height, divideX, divideY;
    float rotate,sb;
    LoadMode loadMode;
    boolean updateFlag = false;

    public abstract void op();
    public abstract void play();
    public void draw() {
        Suu.gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);
        Suu.gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texture);
        Suu.gl.glBindTexture(GL10.GL_TEXTURE_2D, textureID);
        Suu.gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        Suu.gl.glPushMatrix();
        Suu.gl.glRotatef(rotate,0,0,1);
        Suu.gl.glTranslatef(x,y,0);
        Suu.gl.glScalef(width*sb,height*sb,1);
        Suu.gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 6,GL10.GL_UNSIGNED_SHORT, indices);
        Suu.gl.glPopMatrix();
    }

    public void setPlayID(int playID){
        this.playID = playID;
        update();
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setWH(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setRotate(float rotate) {
        this.rotate = rotate;
    }

    public void setSB(float sb) {
        this.sb = sb;
    }

    public void aSetXY(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void aSetRotate(float rotate) {
        this.rotate += rotate;
    }

    public void aSetSB(float sb) {
        this.sb += sb;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getRotate() {
        return rotate;
    }

    public float getSB() {
        return sb;
    }

    public String getKey(){
        return key;
    }

    public void update(){
        updateFlag = true;
    }

    public boolean inActor(int touchX, int touchY){
        if((x-width*sb/2)<touchX&&(x+width*sb/2)>touchX&&(y-height*sb/2)<touchY&&(y+height*sb/2)>touchY){
            return true;
        } else {
          return false;
        }
    }

    public boolean inScreen(){
        if((x-width*sb/2)<1920&&((x+width*sb/2))>0&&(y-height*sb/2)<1080&&(y+height*sb/2)>0){
            return true;
        } else {
            return false;
        }
    }
}
