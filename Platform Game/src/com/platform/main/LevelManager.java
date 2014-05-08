package com.platform.main;

import com.platform.main.gameobject.ClippingPlatform;
import com.platform.main.gameobject.Doorway;
import com.platform.main.gameobject.Enemy;
import com.platform.main.gameobject.Frog;
import com.platform.main.gameobject.Ladder;
import com.platform.main.gameobject.Lemon;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.andengine.entity.scene.Scene;

public class LevelManager
{
    private static final String TYPE_CLIPPING = "clipping";
    private static final String TYPE_DOORWAY = "doorway";
    private static final String TYPE_ENEMY = "enemy";
    private static final String TYPE_IGNORE = "ignore";
    private static final String TYPE_LADDER = "ladder";
    private static final String TYPE_MESSAGE = "message";
    private static final String TYPE_LEMON = "lemon";
    private static final String TYPE_PLATFORM = "platform";
    private static final String TYPE_PLATFORM_POLYGON = "platform-polygon";
    private static final int TYPE_PLATFORM_POLYGON_TOTAL_INDEX = 8;
    private static final String TYPE_SETUP = "setup";
    private static final String TYPE_FROG = "frog";
    private Level currentLevel;
    public int lastStartPosX = 0;
    public int lastStartPosY = 0;
    private MainActivity mActivity;
    private String scheduledDestination;
    private int scheduledDestinationX;
    private int scheduledDestinationY;
    private boolean scheduledDestinationConfirm = false;

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

        if(this.currentLevel != null)
        {
            this.currentLevel.destroy();
        }

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
                    else if(data[0].equals(this.TYPE_LADDER))
                    {
                        mActivity.log("Adding a ladder");
                        this.currentLevel.addPlatform(new Ladder(Integer.parseInt(data[1]),Integer.parseInt(data[2]),Integer.parseInt(data[3]),Integer.parseInt(data[4]),this.mActivity));
                    }
                    else if(data[0].equals(this.TYPE_DOORWAY))
                    {
                        mActivity.log("Adding a doorway");
                        this.currentLevel.addPlatform(new Doorway(Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]), data[5], Integer.parseInt(data[6]), Integer.parseInt(data[7]), this.mActivity));
                    }
                    else if(data[0].equals(this.TYPE_LEMON))
                    {
                        mActivity.log("Adding a lemon");
                        this.currentLevel.addGameObject(new Lemon(Integer.parseInt(data[1]),Integer.parseInt(data[2]), mActivity));
                    }
                    else if(data[0].equals(this.TYPE_FROG))
                    {
                        this.currentLevel.addGameObject(new Frog(720,920,mActivity));
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

        //finalise the level
        this.currentLevel.completeLevelLoading();
        //set the new scene
        mActivity.getEngine().setScene(this.getScene());
        //reload debug draw
        this.currentLevel.getScene().registerUpdateHandler(this.mActivity);

        if(mActivity.getThePlayer() != null)
        {
            this.mActivity.setDebug();
            //reload the player
            mActivity.getThePlayer().reload(this.lastStartPosX, this.lastStartPosY);
            //reload the camera
            mActivity.getCamera().setChaseEntity(this.mActivity.getThePlayer().getShape());
        }
    }

    public Level getLevel()
    {
        return this.currentLevel;
    }

    public Scene getScene()
    {
        return this.currentLevel.getScene();
    }

    public void setupScheduleLoadLevel(String paramString, int scheduledDestinationX, int scheduledDestinationY)
    {
        this.scheduledDestinationConfirm = false;
        this.scheduledDestination = paramString;
        this.scheduledDestinationX = scheduledDestinationX;
        this.scheduledDestinationY = scheduledDestinationY;
    }

    public void confirmScheduleLoadLevel()
    {
        this.scheduledDestinationConfirm = true;
    }

    public void updateLevel()
    {
        if (this.scheduledDestinationConfirm == true)
        {
            this.mActivity.getThePlayer().moveFullStop();
            LoadLevel(this.scheduledDestination, this.scheduledDestinationX, this.scheduledDestinationY);
            this.scheduledDestinationConfirm = false;
            this.mActivity.getThePlayer().setInfrontOfDoorway(false);
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
