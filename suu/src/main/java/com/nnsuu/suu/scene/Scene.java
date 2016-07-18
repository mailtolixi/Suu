package com.nnsuu.suu.scene;

import android.view.MotionEvent;

import com.nnsuu.suu.actor.Actor;
import com.nnsuu.suu.anime.Anime;

public interface Scene {
    public void add(String actorName, Actor actor);
    public void add(Anime anime);
    public Actor get(String actorName);
    public void clear(String actorName);
    public void clear(Actor actor);
    public void clear(Anime anime);
    public void op();
    public void in();
    public void play();
    public void draw();
    public void ed();
    public boolean touch(MotionEvent event);
    public void back();
    public void reTouch();
}
