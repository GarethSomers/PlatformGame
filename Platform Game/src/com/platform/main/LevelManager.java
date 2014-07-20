package com.platform.main;

import com.platform.main.GameResources.JsonLoader;
import com.platform.main.GameResources.Level.GameLevel;
import com.platform.main.GameResources.Level.Menu.Menu;
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
    public int lastStartPosX = 100;
    public int lastStartPosY = 100;
    private GameManager gameManager;
    private HeadsUpDisplay hud;
    private String scheduledDestination;
    private int scheduledDestinationX;
    private int scheduledDestinationY;
    private boolean scheduledDestinationConfirm = false;
    public LevelState currentState = LevelState.Loading;
    public JsonLoader jsonLoader;

    public LevelManager(GameManager paramMainActivity)
    {
        this.gameManager = paramMainActivity;
        this.jsonLoader = new JsonLoader(gameManager);
    }

    public enum LevelState
    {
        Loading, Playing, Menu
    }

    public void LoadLevel()
    {
        this.scheduledDestinationX = 100;
        this.scheduledDestinationY = 100;
        LoadLevel("menu");
    }

    public void loadFirstLevel()
    {
        this.scheduledDestinationX = 700;
        this.scheduledDestinationY = 400;
        LoadLevel("two");
    }

    public void LoadLevel(String levelName)
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
            this.currentLevel = new Menu(this.gameManager);
            gameManager.getMainActivity().log("Starting Menu");
        }
        else
        {
            this.currentLevel = this.jsonLoader.loadLevel(levelName);
        }

        //set the new scene
        gameManager.getMainActivity().getEngine().setScene(this.getScene());
        //reload debug draw
        this.currentLevel.getScene().registerUpdateHandler(this.gameManager.getMainActivity());

        if(this.currentLevel instanceof GameLevel)
        {
            ((GameLevel)this.currentLevel).createObject();
            if(gameManager.getThePlayer() != null)
            {
                this.gameManager.setDebug();
                //reload the player
                gameManager.getThePlayer().reload(this.scheduledDestinationX, this.scheduledDestinationY);
                //reload the camera
                gameManager.getMainActivity().getCamera().setChaseEntity(this.gameManager.getThePlayer().getShape());
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
        this.lastStartPosX = scheduledDestinationX;
        this.lastStartPosY = scheduledDestinationY;
    }

    public void confirmScheduleLoadLevel()
    {
        this.scheduledDestinationConfirm = true;
    }

    public void updateLevel()
    {
        if (this.scheduledDestinationConfirm == true)
        {
            this.gameManager.getThePlayer().moveFullStop();
            LoadLevel(this.scheduledDestination);
            this.scheduledDestinationConfirm = false;
            //this.gameManager.getThePlayer().setInfrontOfDoorway(false);
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
        this.hud = new HeadsUpDisplay(this.gameManager);
    }
}
