package com.nnsuu.suu.skins;

import android.graphics.Color;
import android.graphics.Typeface;

import com.nnsuu.suu.Suu;
import com.nnsuu.suu.other.LoadMode;
import com.nnsuu.suu.SuuActivity;

import java.util.HashMap;

public class SuuFonts implements Fonts {
    HashMap<String,Typeface> typefaces;
    public SuuFonts(){
        typefaces = new HashMap<>();
    }

    @Override
    public void addTTF(String filepath,LoadMode loadMode) {
        if (!typefaces.containsKey(filepath)){
            if(loadMode == LoadMode.SDCard) {
                typefaces.put(filepath, Typeface.createFromFile(filepath));
            } else {
                typefaces.put(filepath, Typeface.createFromAsset(Suu.app.getResources().getAssets(), filepath));
            }
        }
    }

    @Override
    public Typeface getTTF(String filepath) {
        return typefaces.get(filepath);
    }

    @Override
    public void draw(int x, int y, int fontSize, Color color, String text, String filepath) {

    }
}
