package com.platform.main;

import android.content.Context;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public class MainActivity
        extends SimpleBaseGameActivity
{
    private static int CAMERA_HEIGHT = 720;
    private static int CAMERA_WIDTH = 1280;
    private ZoomCamera camera;
    private GameManager gameManager;
    public float zoomFactor = 2.5f;
    public float zoomFactorHigh = 5f;
    public float menuScale = 2f;
    public float menuScaleHigh = 4f;





    private void createEngineDefaults()
    {
        getEngine().registerUpdateHandler(new FPSLogger());
        getVertexBufferObjectManager();
        Looper.prepare();
    }



    public void gameToast(final String paramString)
    {
        runOnUiThread(new Runnable()
        {
            public void run()
            {
                Toast.makeText(MainActivity.this.getThis(), paramString, 0).show();
            }
        });
    }

    public ZoomCamera getCamera()
    {
        return this.camera;
    }

    public int getCameraHeight()
    {
        return CAMERA_HEIGHT;
    }

    public int getCameraWidth()
    {
        return CAMERA_WIDTH;
    }



    public Context getThis()
    {
        return this;
    }

    public void log(String paramString)
    {
        Log.i("Game Debug Message", paramString);
    }

    public EngineOptions onCreateEngineOptions()
    {
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        this.CAMERA_WIDTH = displayMetrics.widthPixels;
        this.CAMERA_HEIGHT = displayMetrics.heightPixels;

        System.gc();
        this.camera = new ZoomCamera(0.0F, 0.0F, getCameraWidth(), getCameraHeight());
        EngineOptions localEngineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), getCamera());
        localEngineOptions.getTouchOptions().setNeedsMultiTouch(true);
        localEngineOptions.getAudioOptions().setNeedsMusic(true);
        localEngineOptions.getAudioOptions().setNeedsSound(true);

        //density
        if(this.getCameraWidth() / displayMetrics.density > 800)
        {
            this.zoomFactor = this.zoomFactorHigh;
            this.menuScale = this.menuScaleHigh;
        }
        this.log("Screen : Density("+displayMetrics.density+") Width("+this.getCameraWidth()+") Height("+this.getCameraHeight()+")");
        getCamera().setZoomFactor(zoomFactor);
        return localEngineOptions;
    }

    public void onCreateResources()
    {

    }

    public Scene onCreateScene()
    {
        createEngineDefaults();
        this.gameManager = new GameManager(this);
        return this.gameManager.getScene();
    }



    /*public boolean onSceneTouchEvent(Scene paramScene, TouchEvent paramTouchEvent)
    {
        //TODO fix the close and reset buttons
        if ((paramTouchEvent.getX() < 50.0F) & (paramTouchEvent.getY() < 100.0F))
        {
            //if left side
            this.reset();
        }
        else if ((paramTouchEvent.getX() > (this.getCameraWidth() - 50.0F)) & (paramTouchEvent.getY() < 50.0F))
        {
            this.gameManager.closeGame();
        }
        this.gameManager.getThePlayer().moveDetect(paramTouchEvent.getX(), paramTouchEvent.getY(), paramTouchEvent);
        return true;
    }*/





}

