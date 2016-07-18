package com.nnsuu.suu.scene;
import java.util.HashMap;

public class SuuScenes implements Scenes{
    Scene scene;
    HashMap<String,Scene> hideScenes;
    public SuuScenes(){
        hideScenes = new HashMap<>();
    }
    @Override
    public void setScene(Scene scene) {
        if (this.scene!=null) {
            this.scene.ed();
        }
        this.scene = scene;
    }

    @Override
    public void setSceneFromHide(String sceneKey) {
        scene = hideScenes.get(sceneKey);
        hideScenes.remove(sceneKey);
    }

    @Override
    public void setSceneAndHide(String sceneKey,Scene scene) {
        hideScenes.put(sceneKey,this.scene);
        this.scene = scene;
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public Scene getScene(String sceneKey) {
        return hideScenes.get(sceneKey);
    }
}
