package com.nnsuu.suu.anime;

import com.nnsuu.suu.Suu;
import com.nnsuu.suu.scene.Scene;

public class SuuAnime implements Anime{
    String actorName;
    int x,y, fps;
    float sb,rotate;
    boolean opFlag;

    int rx,ry, rfps;
    float rsb,rrotate;
    public SuuAnime(String actorName, int fps, Scene scene){
        this.actorName = actorName;
        this.x = scene.get(actorName).getX();
        this.y = scene.get(actorName).getY();
        this.sb = scene.get(actorName).getSB();
        this.rotate = scene.get(actorName).getRotate();
        this.fps = fps;

        opFlag = true;
        if (fps < 0)
            rfps = fps;
    }

    public SuuAnime(String actorName,int x,int y,double sb,double rotate,int fps){
        this.actorName = actorName;
        this.x = x;
        this.y = y;
        this.sb = (float) sb;
        this.rotate = (float) rotate;
        this.fps = fps;

        opFlag = true;
        if (fps < 0)
            rfps = fps;
    }

    @Override
    public void play() {
        if (opFlag){
            opFlag=false;
            op();
        }
        if (fps == 0){
            Suu.scenes.getScene().get(actorName).setXY(x,y);
            Suu.scenes.getScene().get(actorName).setSB(sb);
            Suu.scenes.getScene().get(actorName).setRotate(rotate);
            ed();
            Suu.scenes.getScene().clear(this);
        }else if(fps > 0){
            Suu.scenes.getScene().get(actorName).aSetXY(
                    (x-Suu.scenes.getScene().get(actorName).getX())/ fps,
                    (y-Suu.scenes.getScene().get(actorName).getY())/ fps);
            Suu.scenes.getScene().get(actorName).aSetSB(
                    (sb-Suu.scenes.getScene().get(actorName).getSB())/ fps);
            Suu.scenes.getScene().get(actorName).aSetRotate(
                    (rotate-Suu.scenes.getScene().get(actorName).getRotate())/ fps);
            fps--;
        } else {
            if(rfps < 0){
                rx = Suu.scenes.getScene().get(actorName).getX();
                ry = Suu.scenes.getScene().get(actorName).getY();
                rsb = Suu.scenes.getScene().get(actorName).getSB();
                rrotate = Suu.scenes.getScene().get(actorName).getRotate();

                rfps = -fps;
            }else if (rfps == 0){
                rfps = -fps;
                int swapi = x;
                x = rx;
                rx=swapi;
                swapi = y;
                y = ry;
                ry=swapi;
                float swapf = sb;
                sb = rsb;
                rsb = swapf;
                swapf = rotate;
                rotate = rrotate;
                rrotate = swapf;
            }else{
                Suu.scenes.getScene().get(actorName).aSetXY(
                        (x-Suu.scenes.getScene().get(actorName).getX())/ rfps,
                        (y-Suu.scenes.getScene().get(actorName).getY())/ rfps);
                Suu.scenes.getScene().get(actorName).aSetSB(
                        (sb-Suu.scenes.getScene().get(actorName).getSB())/ rfps);
                Suu.scenes.getScene().get(actorName).aSetRotate(
                        (rotate-Suu.scenes.getScene().get(actorName).getRotate())/ rfps);
                rfps--;
            }
        }
    }

    @Override
    public void op() {

    }

    @Override
    public void ed() {

    }
}
