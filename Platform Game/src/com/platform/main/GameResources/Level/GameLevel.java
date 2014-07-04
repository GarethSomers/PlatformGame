package com.platform.main.GameResources.Level;

import com.platform.main.GameManager;
import com.platform.main.GameResources.Object.GameObject;
import com.platform.main.GameResources.Object.DelayedCreationObject;
import com.platform.main.GameResources.Object.Players.Enemy;
import com.platform.main.GameResources.Object.Platforms.SolidClippingPlatform;
import com.platform.main.ObjectStatus;

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

    //dimensions
    private int width = 100;
    private int height = 100;

    public GameLevel(GameManager paramMainActivity)
    {
        super(paramMainActivity);
    }

    public void addBackgroundImage(Sprite backgroundImage)
    {

    }

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

    public ArrayList<Enemy> getEnemies()
    {
        return this.hurtBoxes;
    }

    public ArrayList<Enemy> getEnemiesNeedRemoving()
    {
        return this.enemiesNeedRemoving;
    }

    public Music getMusic()
    {
        return this.music;
    }

    public Scene getScene()
    {
        return this.scene;
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

    public void update()
    {
        for(Enemy e : this.getEnemies())
        {
            e.updatePosition();
        }
        for(Enemy e : this.getEnemiesNeedRemoving())
        {
            e.setActive(false);
        }
        this.getEnemiesNeedRemoving().clear();
    }

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
                        gameManager.getPhysicsWorld().destroyBody(objects.get(myI).getBody());
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

    @Override
    public void preCreateObject() {
        //Add Top Solid Block
        SolidClippingPlatform topPlatform = new SolidClippingPlatform(gameManager);
        topPlatform.setPos(0.0F,-1.0F);
        topPlatform.setDimensions(this.width, 1.0F);
        this.addGameObject(topPlatform);
        //Add Left Solid Block
        SolidClippingPlatform leftPlatform = new SolidClippingPlatform(gameManager);
        topPlatform.setPos(-1.0F,-0.0F);
        topPlatform.setDimensions(1.0F, this.height);
        this.addGameObject(topPlatform);
        //Add Left Solid Block
        SolidClippingPlatform rightPlatform = new SolidClippingPlatform(gameManager);
        topPlatform.setPos(this.width+1,0.0F);
        topPlatform.setDimensions(1.0F, this.height);
        this.addGameObject(topPlatform);
        //Add Left Solid Block
        SolidClippingPlatform bottomPlatform = new SolidClippingPlatform(gameManager);
        topPlatform.setPos(0.0F,this.height+1);
        topPlatform.setDimensions(this.width, 1.0F);
        this.addGameObject(topPlatform);

        gameManager.getMainActivity().getCamera().setBounds(0.0F, 0.0F, this.width, this.height);
    }

    @Override
    public void createObject() {
        this.preCreateObject();

        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).createObject();
        }
        this.status = ObjectStatus.DECLARED;

        this.afterCreateObject();
    }

    @Override
    public void afterCreateObject() {

    }
}
