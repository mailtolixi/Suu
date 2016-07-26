package com.nnsuu.suu.scene;

import android.view.MotionEvent;

import com.nnsuu.suu.actor.Actor;
import com.nnsuu.suu.anime.Anime;

public interface Scene {
    void add(String actorName, Actor actor);
    void add(Anime anime);
    Actor get(String actorName);
    void clear(String actorName);
    void clear(Actor actor);
    void clear(Anime anime);
    void op();
    void in();
    void play();
    void draw();
    void pause();
    void resume();
    void ed();
    boolean touch(MotionEvent event);
    void back();
    void retouch();
}
