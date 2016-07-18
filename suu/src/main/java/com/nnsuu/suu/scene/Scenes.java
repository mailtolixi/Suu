package com.nnsuu.suu.scene;

public interface Scenes {
    public void setScene(Scene scene);
    public void setSceneFromHide(String sceneKey);
    public void setSceneAndHide(String sceneKey, Scene scene);
    public Scene getScene();
    public Scene getScene(String sceneKey);
}
