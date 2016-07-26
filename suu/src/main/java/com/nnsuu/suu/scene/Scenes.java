package com.nnsuu.suu.scene;

public interface Scenes {
    void setScene(Scene scene);
    void setSceneFromHide(String sceneKey);
    void setSceneAndHide(String sceneKey, Scene scene);
    Scene getScene();
    Scene getScene(String sceneKey);
}
