package com.platform.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import org.andengine.entity.scene.Scene;
import org.andengine.util.level.LevelLoader;

public class LevelManager
{
    private static final String TYPE_CLIPPING = "clipping";
    private static final String TYPE_DOORWAY = "doorway";
    private static final String TYPE_ENEMY = "enemy";
    private static final String TYPE_IGNORE = "ignore";
    private static final String TYPE_LADDER = "ladder";
    private static final String TYPE_MESSAGE = "message";
    private static final String TYPE_PLATFORM = "platform";
    private static final String TYPE_PLATFORM_POLYGON = "platform-polygon";
    private static final int TYPE_PLATFORM_POLYGON_TOTAL_INDEX = 8;
    private static final String TYPE_SETUP = "setup";
    private Level currentLevel;
    public int lastStartPosX = 0;
    public int lastStartPosY = 0;
    private MainActivity mActivity;
    private String scheduledDestination;
    private int scheduledDestinationX;
    private int scheduledDestinationY;

    public LevelManager(MainActivity paramMainActivity)
    {
        this.mActivity = paramMainActivity;
    }

    public void LoadLevel()
    {
        LoadLevel("one", 50, 50);
    }

    /* Error */
    public void LoadLevel(String levelName, int lastStartPosX, int lastStartPosY)
    {
        //display loading screen
        this.lastStartPosX = lastStartPosX;
        this.lastStartPosY = lastStartPosY;



        //TODO FIX THIS

        this.currentLevel = new Level(this.mActivity);
        /*
        Try load level
         */
        try {
            /*
            Open input Stream
             */
            InputStream file = mActivity.getAssets().open("levels/"+levelName+".lvl");
            BufferedReader br = new BufferedReader(new InputStreamReader(file, "UTF-8"));
            String line;
            /*
            Loop through CSV entries
             */
            while ((line = br.readLine()) != null) {
                String data[] = line.split(",");
                //mActivity.log(data[0]);

                /*
                If its a setup item (usually the first item)
                 */
                try
                {
                    if(data[0].equals(this.TYPE_SETUP))
                    {
                        mActivity.log("Adding to setup");
                        this.currentLevel.setBackgroundImages(data[1],data[2],Integer.parseInt(data[3]), Integer.parseInt(data[4]));
                        this.currentLevel.setMusicByString(data[4]);
                    }
                    else if(data[0].equals(this.TYPE_CLIPPING))
                    {
                        mActivity.log("Adding a clipping");
                        this.currentLevel.addPlatform(new ClippingPlatform(Integer.parseInt(data[1]),Integer.parseInt(data[2]),Integer.parseInt(data[3]),Integer.parseInt(data[4]),this.mActivity));
                    }
                }
                catch(Exception e)
                {
                    mActivity.log("Could not add "+data[0]);
                }

            }
        }
        catch (Exception e) {
            mActivity.log("Could not load level (levels/" + levelName + ".lvl)");
        }

        //declare level
        this.currentLevel.completeLevelLoading();
    }

    public Level getLevel()
    {
        return this.currentLevel;
    }

    public Scene getScene()
    {
        return this.currentLevel.getScene();
    }

    public void scheduleLoadLevel(String paramString, int scheduledDestinationX, int scheduledDestinationY)
    {
        this.scheduledDestination = paramString;
        this.scheduledDestinationX = scheduledDestinationX;
        this.scheduledDestinationY = scheduledDestinationY;
    }

    public void updateLevel()
    {
        if (this.scheduledDestination != null)
        {
            LoadLevel(this.scheduledDestination, this.scheduledDestinationX, this.scheduledDestinationY);
            this.scheduledDestination = null;
            return;
        }
        for(Enemy e : getLevel().getEnemies())
        {
            e.updatePosition();
        }
        for(Enemy e : getLevel().getEnemiesNeedRemoving())
        {
            e.setActive(false);
        }
        getLevel().getEnemiesNeedRemoving().clear();
    }
}
