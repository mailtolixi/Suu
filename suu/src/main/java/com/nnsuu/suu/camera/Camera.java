package com.nnsuu.suu.camera;

public interface Camera {
    public void setCamera(int x, int y, int width, int height);
    public void setCamera(Camera camera);

    public void setCameraX(int x);
    public void setCameraY(int y);
    public void setCameraWidth(int width);
    public void setCameraHeight(int height);

    public int getCameraX();
    public int getCameraY();
    public int getCameraWidth();
    public int getCameraHeight();
}
