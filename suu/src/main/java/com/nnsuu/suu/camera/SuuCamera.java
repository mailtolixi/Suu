package com.nnsuu.suu.camera;

public class SuuCamera implements Camera{
    protected int x = 0;
    protected int y = 0;
    protected int width = 1920;
    protected int height = 1080;

    @Override
    public void setCamera(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void setCamera(Camera camera) {
        this.x = camera.getCameraX();
        this.y = camera.getCameraY();
        this.width = camera.getCameraWidth();
        this.height = camera.getCameraHeight();
    }

    @Override
    public void setCameraX(int x) {
        this.x = x;
    }

    @Override
    public void setCameraY(int y) {
        this.y = y;
    }

    @Override
    public void setCameraWidth(int width) {
        this.width = width;
    }

    @Override
    public void setCameraHeight(int height) {
        this.height = height;
    }

    @Override
    public int getCameraX() {
        return x;
    }

    @Override
    public int getCameraY() {
        return y;
    }

    @Override
    public int getCameraWidth() {
        return width;
    }

    @Override
    public int getCameraHeight() {
        return height;
    }
}
