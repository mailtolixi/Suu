package com.nnsuu.suu;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.nnsuu.suu.camera.SuuCamera;
import com.nnsuu.suu.scene.SuuScenes;
import com.nnsuu.suu.skins.SuuSkins;

import junit.framework.Assert;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public abstract class SuuActivity extends Activity implements SuuActivityInterface{
//    public static AssetManager assetManager;
    public static int phoneWidth;
    public static int phoneHeight;
    GLSurfaceView glSurfaceView;
    GLSurfaceView.Renderer renderer;

    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        super.onCreate(savedInstanceState);

        glSurfaceView = new GLSurfaceView(this);

        renderer = new GLSurfaceView.Renderer() {
            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                Log.d("nnsuu.com/n","surface created");
                init(gl);
                Suu.gl.glClearColor(1f, 1f, 1f, 1f);
                Suu.gl.glMatrixMode(GL10.GL_PROJECTION);
                Suu.gl.glEnable(GL10.GL_TEXTURE_2D);
                Suu.gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
                Suu.gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
                Suu.gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
                Suu.gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
                Suu.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
                Suu.gl.glEnable(GL10.GL_BLEND);
                op();
            }

            @Override
            public void onSurfaceChanged(GL10 gl, int width, int height) {
                Log.d("nnsuu.com/n","surface changed: " + width + "x" + height);
                phoneWidth = width;
                phoneHeight = height;
            }

            @Override
            public void onDrawFrame(GL10 gl) {
                Suu.scenes.getScene().in();
                Suu.scenes.getScene().play();
                Suu.gl.glLoadIdentity();
                Suu.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
                Suu.gl.glOrthof(Suu.camera.getCameraX(), Suu.camera.getCameraWidth(), Suu.camera.getCameraY(), Suu.camera.getCameraHeight(), 1, -1);

                Suu.scenes.getScene().draw();
            }
        };
        glSurfaceView.setRenderer(renderer);

        setContentView(glSurfaceView);
    }

    protected void init(GL10 gl) {
        Suu.app = this;
        Suu.gl = gl;
        Suu.camera = new SuuCamera();
        Suu.skins = new SuuSkins();
        Suu.scenes = new SuuScenes();
    }

    protected void onPause(){
        super.onPause();
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
    protected void onResume(){
        Log.d("nnsuu.com/n","surface resume");
        super.onResume();
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    protected void onDistory(){}

    protected void onStart(){
        super.onStart();
    }

    protected void onStop(){
        super.onStop();
    }

    public boolean onTouchEvent(MotionEvent event) {
        return Suu.scenes.getScene().touch(event);
    }
}
