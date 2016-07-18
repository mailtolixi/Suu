package com.nnsuu.suu.skins;

import android.graphics.Color;
import android.graphics.Typeface;

import com.nnsuu.suu.other.LoadMode;

public interface Fonts {
    public void addTTF(String filepath, LoadMode loadMode);
    public Typeface getTTF(String filepath);
    public void draw(int x, int y, int fontSize, Color color, String text, String filepath);
}
