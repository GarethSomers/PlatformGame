package com.platform.main.GameResources.Level;

import com.platform.main.GameManager;
import com.platform.main.GameResources.LevelObjects.BodyObject;
import com.platform.main.GameResources.DelayedCreationObject;
import com.platform.main.GameResources.LevelObjects.AnimatedObjects.MoveableObjects.Enemy;
import com.platform.main.GameResources.LevelObjects.GameObject;
import com.platform.main.GameResources.LevelObjects.Platforms.SolidClippingPlatform;
import com.platform.main.GameResources.LevelObjects.ObjectStatus;
import com.platform.main.GameResources.LevelObjects.StaticObject.Background;
import com.platform.main.GameResources.LevelObjects.StaticObject.ParallaxLayer;

import java.util.ArrayList;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.Sprite;

public class GameLevel extends Level implements DelayedCreationObject
{
    //backgrounds
    private ParallaxBackground.ParallaxEntity autoParallaxBackground;
    protected Sprite foregroundImage;
    //objects
    private ArrayList<Enemy> enemiesNeedRemoving = new ArrayList();
    private ArrayList<Enemy> hurtBoxes = new ArrayList();
    private ArrayList<GameObject> objects = new ArrayList();
    private ArrayList<Background> parralaxBackgrounds = new ArrayList();

    //dimensions
    private int width = 100;
    private int height = 100;

    public GameLevel(GameManager paramMainActivity)
    {
        super(paramMainActivity);
    }


    /*********************************************************************************************/
    /* ADD GAME OBJECT */
    /*********************************************************************************************/
    public void addGameObject(GameObject aAnimatedGameObject)
    {
        //if its an enemy
        if(aAnimatedGameObject instanceof Enemy)
        {
            this.hurtBoxes.add((Enemy) aAnimatedGameObject);
        }
        //also add it to the objects
        this.objects.add(aAnimatedGameObject);
    }


    /*********************************************************************************************/
    /* ADD PARALLAX BACKGROUND */
    /*********************************************************************************************/
    public void addParallaxBackground(Background aBackground)
    {
        this.parralaxBackgrounds.add(aBackground);
    }



    /*********************************************************************************************/
    /* GET OBJECTS */
    /*********************************************************************************************/
    public ArrayList<Enemy> getEnemies()
    {
        return this.hurtBoxes;
    }

    public ArrayList<Enemy> getEnemiesNeedRemoving()
    {
        return this.enemiesNeedRemoving;
    }


    /*********************************************************************************************/
    /* GET SET MUSIC */
    /*********************************************************************************************/
    public Music getMusic()
    {
        return this.music;
    }
    public void setMusic(Music pMusic) throws Exception
    {
        this.music = pMusic;
    }
    public void setMusicByString(String pMusic)
    {
        try
        {
            this.setMusic(MusicFactory.createMusicFromAsset(gameManager.getMainActivity().getEngine().getMusicManager(), gameManager.getMainActivity(), "mfx/" + pMusic + ".mp3"));
            getMusic().setLooping(true);
            getMusic().play();
        }
        catch(Exception e)
        {
            gameManager.getMainActivity().log("Could not load music (mfx/"+pMusic+".mp3)");
        }
    }



    /*********************************************************************************************/
    /* UPDATE SCENE */
    /*********************************************************************************************/

    public void update()
    {
        for(Enemy e : this.getEnemies())
        {
            e.updatePosition();
        }
        for(Enemy e : this.getEnemiesNeedRemoving())
        {
            //disabled because need object to fall into death place (eg frog).
            //e.setActive(false);
        }
        this.getEnemiesNeedRemoving().clear();
    }



    /*********************************************************************************************/
    /* GETTER / SETTER WIDTH */
    /*********************************************************************************************/
    public int getWidth()
    {
        return this.width;
    }

    public void setWidth(int newWidth)
    {
        this.width = newWidth;
    }



    /*********************************************************************************************/
    /* GETTER / SETTER HEIGHT */
    /*********************************************************************************************/
    public int getHeight()
    {
        return this.height;
    }

    public void setHeight(int newHeight)
    {
        this.height = newHeight;
    }




    /*********************************************************************************************/
    /* DESTROYABLE OBJECT */
    /*********************************************************************************************/
    public void destroy()
    {
        //Delete Enemies
        /*gameManager.runOnUpdateThread(new Runnable() {
            @Override
            public void run() {*/
                for (int i = 0; i < objects.size(); i++) {
                    try {
                        final int myI = i;
                        scene.detachChild(objects.get(myI).getShape());
                        if(objects.get(myI) instanceof BodyObject)
                        {
                            gameManager.getPhysicsWorld().destroyBody(((BodyObject) objects.get(myI)).getBody());
                        }
                    } catch (Exception e) {
                        gameManager.getMainActivity().log("Level : Could not destroy enemy " + e);
                    }
                }
                hurtBoxes.clear();

                for (int i = 0; i < hurtBoxes.size(); i++) {
                    try {
                        final int myI = i;
                        scene.detachChild(hurtBoxes.get(myI).getShape());
                        gameManager.getPhysicsWorld().destroyBody(hurtBoxes.get(myI).getBody());
                    } catch (Exception e) {
                        gameManager.getMainActivity().log("Level : Could not destroy enemy " + e);
                    }
                }
                hurtBoxes.clear();

                //Delete music
                if(this.music != null)
                {
                    music.stop();
                    music.release();
                }

                scene.detachChild(gameManager.getThePlayer().getShape());
                scene.detachChild(gameManager.getDebug());
                //Delete foreground
                scene.detachChild(foregroundImage);
                System.gc();
            /*}
        });*/
    }


    /*********************************************************************************************/
    /* DELAYED CREATION */
    /*********************************************************************************************/

    @Override
    public void preCreateObject() {
        //Add Top Solid Block
        SolidClippingPlatform topPlatform = new SolidClippingPlatform(gameManager);
        topPlatform.setPos(0.0F,-1.0F);
        topPlatform.setDimensions(this.width, 1.0F);
        this.addGameObject(topPlatform);
        //Add Left Solid Block
        SolidClippingPlatform leftPlatform = new SolidClippingPlatform(gameManager);
        leftPlatform.setPos(-1.0F,-0.0F);
        leftPlatform.setDimensions(1.0F, this.height);
        this.addGameObject(leftPlatform);
        //Add Left Solid Block
        SolidClippingPlatform rightPlatform = new SolidClippingPlatform(gameManager);
        rightPlatform.setPos(this.width+1,0.0F);
        rightPlatform.setDimensions(1.0F, this.height);
        this.addGameObject(rightPlatform);
        //Add Left Solid Block
        SolidClippingPlatform bottomPlatform = new SolidClippingPlatform(gameManager);
        bottomPlatform.setPos(0.0F,this.height+1);
        bottomPlatform.setDimensions(this.width, 1.0F);
        this.addGameObject(bottomPlatform);

        gameManager.getMainActivity().getCamera().setBounds(0.0F, 0.0F, this.width, this.height);

        //SETUP PARRALAX BACKGROUND
        this.parallaxLayer = new ParallaxLayer(gameManager.getMainActivity().getCamera(), true, this.getWidth());
        this.parallaxLayer.setParallaxChangePerSecond(2);
        this.parallaxLayer.setParallaxScrollFactor(1);
    }

    @Override
    public void createObject() {
        this.preCreateObject();

        for(GameObject p : parralaxBackgrounds )
        {
            p.createObject();
        }
        this.scene.attachChild(this.parallaxLayer);

        for(GameObject g : objects )
        {
            g.createObject();
        }
        this.status = ObjectStatus.CONSTRUCTED;

        this.afterCreateObject();
    }

    @Override
    public void afterCreateObject()
    {
        this.gameManager.setDebug();
        this.getScene().sortChildren();
    }



}
