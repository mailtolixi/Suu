package com.nnsuu.suu.scene;

import android.util.Log;
import android.view.MotionEvent;

import com.nnsuu.suu.Suu;
import com.nnsuu.suu.SuuActivity;
import com.nnsuu.suu.actor.Actor;
import com.nnsuu.suu.anime.Anime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;

public abstract class SuuScene implements Scene{
    LinkedHashMap<String,Actor> actors;
    Iterator<Map.Entry<String, Actor>> iterator;
    Map.Entry<String, Actor> entry;
    ListIterator listIterator;

    ArrayList<Anime> animes;
    boolean inFlag;
    String actorName;

    public SuuScene(){
        actors = new LinkedHashMap<>();
        animes = new ArrayList<>();
        inFlag = true;
        actorName = "";
        op();
    }
    @Override
    public void add(String actorName,Actor actor) {
        actors.put(actorName,actor);
    }

    @Override
    public void add(Anime anime) {
        animes.add(anime);
    }

    @Override
    public Actor get(String actorName) {
        return actors.get(actorName);
    }

    @Override
    public void clear(String actorName) {
        actors.remove(actorName);
    }

    @Override
    public void clear(Actor actor) {
        actors.remove(actor);
    }

    @Override
    public void clear(Anime anime) {
        animes.remove(anime);
    }

    @Override
    public void in() {
        if (inFlag){
            iterator = actors.entrySet().iterator();
            while (iterator.hasNext()) {
                entry = iterator.next();
                entry.getValue().op();
            }
            inFlag = !inFlag;
        }
    }
    @Override
    public void play() {
        iterator = actors.entrySet().iterator();
        while (iterator.hasNext()) {
            entry = iterator.next();
            entry.getValue().play();
        }
    }

    @Override
    public void draw() {
        iterator = actors.entrySet().iterator();
        while (iterator.hasNext()) {
            entry = iterator.next();
            if (entry.getValue().inScreen()){
                entry.getValue().draw();
            }
        }
        for (int i=0;i<animes.size();i++){
            animes.get(i).play();
        }
    }

    @Override
    public void ed() {
        Suu.skins.clearAll();
    }

    @Override
    public boolean touch(MotionEvent event) {
        synchronized (event){
            int touchX = (int)event.getX()*1920/SuuActivity.phoneWidth;
            int touchY = (1080-(int)event.getY()*1080/SuuActivity.phoneHeight);


            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    if ("".equals(actorName)) {
                        listIterator = new ArrayList(actors.entrySet()).listIterator(actors.size());
                        Flag:while (listIterator.hasPrevious()) {
                            entry = (Map.Entry<String, Actor>) listIterator.previous();

                            if (entry.getValue().inActor(touchX,touchY)){
                                actorName = entry.getKey();
                                actors.get(actorName).touchDown();
                                break Flag;
                            }
                        }
                        listIterator = null;
                    } else {
                        actors.get(actorName).touchDown();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (!"".equals(actorName)) {
                        actors.get(actorName).touchMove(touchX,touchY);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (!"".equals(actorName)) {
                        actors.get(actorName).touchUp(touchX,touchY);
                    }
                    reTouch();
                    break;
            }


        }

        return true;
    }
    @Override
    public void back(){

    }

    @Override
    public void reTouch() {
        actorName = "";
    }
}
