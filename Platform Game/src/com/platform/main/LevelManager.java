package com.platform.main;

import com.platform.main.GameResources.Level.GameLevel;
import com.platform.main.GameResources.Level.Menu.Menu;
import com.platform.main.GameResources.Object.Platforms.ClippingPlatform;
import com.platform.main.GameResources.Object.Players.Doorway;
import com.platform.main.GameResources.Object.Players.Enemy;
import com.platform.main.GameResources.Object.Players.Frog;
import com.platform.main.GameResources.Object.Interactions.Ladder;
import com.platform.main.GameResources.Object.Interactions.Lemon;
import com.platform.main.GameResources.Level.Level;

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
    private static final String TYPE_MENU = "menu";
    private Level currentLevel;
    public int lastStartPosX = 0;
    public int lastStartPosY = 0;
    private MainActivity mActivity;
    private HeadsUpDisplay hud;
    private String scheduledDestination;
    private int scheduledDestinationX;
    private int scheduledDestinationY;
    private boolean scheduledDestinationConfirm = false;
    public LevelState currentState = LevelState.Loading;

    public LevelManager(MainActivity paramMainActivity)
    {
        this.mActivity = paramMainActivity;
    }

    public enum LevelState
    {
        Loading, Playing, Menu
    }

    public void LoadLevel()
    {
        LoadLevel("menu", 50, 50);
    }

    public void loadFirstLevel()
    {
        LoadLevel("one", 10, 305);
    }

    /* Error */
    public void LoadLevel(String levelName, int lastStartPosX, int lastStartPosY)
    {
        this.currentState = LevelState.Loading;
        //display loading screen
        this.lastStartPosX = lastStartPosX;
        this.lastStartPosY = lastStartPosY;

        if(this.currentLevel != null)
        {
            this.currentLevel.destroy();
        }


        /*
        Try load level
         */
        if(levelName.equals(this.TYPE_MENU))
        {
            /*
            Show a menu
             */
            this.currentLevel = new Menu(this.mActivity);
            mActivity.log("Starting Menu");
        }
        else
        {
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
                            this.currentLevel = new GameLevel(this.mActivity);
                            ((GameLevel)this.currentLevel).setBackgroundImages(data[1],data[2],Integer.parseInt(data[3]), Integer.parseInt(data[4]));
                            this.currentLevel.setMusicByString(data[4]);
                        }
                        else if(data[0].equals(this.TYPE_CLIPPING))
                        {
                            mActivity.log("Adding a clipping");
                            ((GameLevel)this.currentLevel).addGameObject(new ClippingPlatform(Integer.parseInt(data[1]),Integer.parseInt(data[2]),Integer.parseInt(data[3]),Integer.parseInt(data[4]),this.mActivity));
                        }
                        else if(data[0].equals(this.TYPE_LADDER))
                        {
                            mActivity.log("Adding a ladder");
                            ((GameLevel)this.currentLevel).addGameObject(new Ladder(Integer.parseInt(data[1]),Integer.parseInt(data[2]),Integer.parseInt(data[3]),Integer.parseInt(data[4]),this.mActivity));
                        }
                        else if(data[0].equals(this.TYPE_DOORWAY))
                        {
                            mActivity.log("Adding a doorway");
                            ((GameLevel)this.currentLevel).addGameObject(new Doorway(Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]), data[5], Integer.parseInt(data[6]), Integer.parseInt(data[7]), this.mActivity));
                        }
                        else if(data[0].equals(this.TYPE_LEMON))
                        {
                            mActivity.log("Adding a lemon");
                            ((GameLevel)this.currentLevel).addGameObject(new Lemon(Integer.parseInt(data[1]),Integer.parseInt(data[2]), mActivity));
                        }
                        else if(data[0].equals(this.TYPE_FROG))
                        {
                            mActivity.log("Adding a frog");
                            ((GameLevel)this.currentLevel).addEnemy(new Frog(720,920,mActivity));
                        }
                    }
                    catch(Exception e)
                    {
                        mActivity.log("Could not add "+data[0]);
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception e) {
                mActivity.log("Could not load level (levels/" + levelName + ".lvl)");
                System.exit(0);
            }
        }

        //set camrea
        this.currentLevel.getScene();
        //finalise the level
        this.currentLevel.completeLevelLoading();
        //set the new scene
        mActivity.getEngine().setScene(this.getScene());
        //reload debug draw
        this.currentLevel.getScene().registerUpdateHandler(this.mActivity);

        if(this.currentLevel instanceof GameLevel)
        {
            if(mActivity.getThePlayer() != null)
            {
                this.mActivity.setDebug();
                //reload the player
                mActivity.getThePlayer().reload(this.lastStartPosX, this.lastStartPosY);
                //reload the camera
                mActivity.getCamera().setChaseEntity(this.mActivity.getThePlayer().getShape());
            }
            this.currentState = LevelState.Playing;
        }
        else if(this.currentLevel instanceof Menu)
        {
            this.currentState = LevelState.Menu;
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
        if(this.getLevel() instanceof GameLevel)
        {
            ((GameLevel)this.getLevel()).update();
        }
    }

    public HeadsUpDisplay getHUD()
    {
        return this.hud;
    }
    public void createHud()
    {
        this.hud = new HeadsUpDisplay(this.mActivity);
    }
}
