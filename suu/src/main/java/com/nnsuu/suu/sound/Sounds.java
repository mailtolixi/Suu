package com.nnsuu.suu.sound;

import com.nnsuu.suu.other.LoadMode;

/**
 * Created by user on 16-7-22.
 */
public interface Sounds {
//    void add();
    void setBackgroundSound(String filepath, LoadMode loadMode);
    void setSound(String key, String filepath, LoadMode loadMode);
    void play();
    void play(String key);
    void pause();
    void resume();
    void clearAll();
}
