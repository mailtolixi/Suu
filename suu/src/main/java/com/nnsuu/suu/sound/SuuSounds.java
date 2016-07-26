package com.nnsuu.suu.sound;

import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

import com.nnsuu.suu.Suu;
import com.nnsuu.suu.other.LoadMode;

import junit.framework.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by user on 16-7-22.
 */
@TargetApi(Build.VERSION_CODES.N)
public class SuuSounds implements Sounds {
    MediaPlayer mediaPlayer;
    SoundPool soundPool;
    HashMap<String,Integer> sounds;
    Iterator<Map.Entry<String,Integer>> iterator;
    Map.Entry entry;
    boolean isPlaying;
    public SuuSounds(){
        isPlaying = false;
        mediaPlayer = new MediaPlayer();

        soundPool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(
                new AudioAttributes.Builder().setLegacyStreamType(
                        AudioManager.STREAM_MUSIC).build()).build();

        sounds = new HashMap<>();
    }

    @Override
    public void setBackgroundSound(String filepath, LoadMode loadMode) {
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            if (loadMode == LoadMode.SDCard) {
                mediaPlayer.setDataSource(filepath);
            } else {
//                mediaPlayer.setDataSource();
                mediaPlayer.setDataSource(Suu.app.getAssets().openFd(filepath).getFileDescriptor());

            }
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
            isPlaying = false;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setSound(String key, String filepath, LoadMode loadMode) {
        try {
            if (loadMode == LoadMode.SDCard) {
                sounds.put(key,soundPool.load(filepath,1));
            } else {
                AssetFileDescriptor assetFileDescriptor = Suu.app.getAssets().openFd(filepath);
                sounds.put(key,soundPool.load(assetFileDescriptor.getFileDescriptor(),
                        assetFileDescriptor.getStartOffset(),
                        assetFileDescriptor.getLength(),1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void play() {
        mediaPlayer.start();
        isPlaying = true;
    }

    @Override
    public void play(String key) {
        soundPool.play(sounds.get(key),1, 1, 0, 0, 1);
    }

    @Override
    public void pause() {
        if(isPlaying) {
            mediaPlayer.pause();
        }
        soundPool.autoPause();
    }

    @Override
    public void resume() {
        if(isPlaying){
            mediaPlayer.start();
        }
        soundPool.autoResume();
    }

    @Override
    public void clearAll() {
        iterator = sounds.entrySet().iterator();
        while (iterator.hasNext()) {
            entry = iterator.next();
            soundPool.unload((Integer) entry.getValue());
        }

        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            isPlaying = false;
        }
    }
}
