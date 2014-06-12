package com.platform.main.GameResources.Level;

import com.platform.main.GameResources.Object.BodyObject;
import com.platform.main.MainActivity;
import com.platform.main.GameResources.Object.AnimatedGameObject;
import com.platform.main.GameResources.Object.Platforms.ClippingPlatform;
import com.platform.main.GameResources.Object.Players.Enemy;
import com.platform.main.GameResources.Object.Platforms.RectangularPlatform;
import com.platform.main.GameResources.Object.Platforms.SolidClippingPlatform;

import java.util.ArrayList;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;

public class GameLevel extends Level
{
    //backgrounds
    private ParallaxBackground.ParallaxEntity autoParallaxBackground;
    protected Sprite foregroundImage;
    //objects
    private ArrayList<Enemy> enemiesNeedRemoving = new ArrayList();
    private ArrayList<Enemy> hurtBoxes = new ArrayList();
    private ArrayList<BodyObject> objects = new ArrayList();

    public GameLevel(MainActivity paramMainActivity)
    {
        super(paramMainActivity);
    }

    public void setBackgroundImages(String pBackground, String pForeground,int pWidth, int pHeight)
    {
        //Add Backgrounds
        ParallaxBackground localParallaxBackground = new ParallaxBackground(0.0F, 0.0F, 0.0F);
        localParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(1.0F, new Sprite(0.0F, 0.0F, this.mActivity.getMaterialManager().getTexture(pBackground, pWidth, pHeight), this.mActivity.getVertexBufferObjectManager())));
        this.scene.setBackground(localParallaxBackground);
        this.foregroundImage = new Sprite(0.0F, 0.0F, this.mActivity.getMaterialManager().getTexture(pForeground, pWidth, pHeight), this.mActivity.getVertexBufferObjectManager());
        this.scene.attachChild(this.foregroundImage);

        //Set the camera
        mActivity.getCamera().setBounds(0.0F, 0.0F, pWidth, pHeight);

        //Add Default Movement Constraints
        this.addGameObject(new SolidClippingPlatform(0.0F, -1.0F, pWidth, 1.0F, mActivity));
        this.addGameObject(new SolidClippingPlatform(-1.0F, 0.0F, 1.0F, pHeight, mActivity));
        this.addGameObject(new SolidClippingPlatform(0.0F, pHeight + 1, pWidth, 1.0F, mActivity));
        this.addGameObject(new SolidClippingPlatform(pWidth + 1, 0.0F, 1.0F, pHeight, mActivity));
    }

    public void addRectangularPlatform(RectangularPlatform pRectangularPlatform)
    {
        this.objects.add(pRectangularPlatform);
    }

    public void addGameObject(BodyObject aAnimatedGameObject)
    {
        aAnimatedGameObject.setAttached(true);
        this.objects.add(aAnimatedGameObject);
        this.scene.attachChild(aAnimatedGameObject.getShape());
    }

    public void completeLevelLoading()
    {
        //This just completes the loading of the level by loading in the platforms and enemies
        for(BodyObject p : this.objects)
        {
            if(p.getAttached() == false)
            {
                p.addToWorld();
            }
        }
        for(Enemy h : this.hurtBoxes)
        {
            if(h.getAttached() == false)
            {
                h.addToWorld();
            }
        }
    }

    public ArrayList<Enemy> getEnemies()
    {
        return this.hurtBoxes;
    }

    public void addEnemy(Enemy e)
    {
        this.scene.attachChild(e.getShape());
        this.hurtBoxes.add(e);
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
            this.setMusic(MusicFactory.createMusicFromAsset(mActivity.getEngine().getMusicManager(), mActivity, "mfx/" + pMusic + ".mp3"));
            getMusic().setLooping(true);
            getMusic().play();
        }
        catch(Exception e)
        {
            mActivity.log("Could not load music (mfx/"+pMusic+".mp3)");
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
        /*mActivity.runOnUpdateThread(new Runnable() {
            @Override
            public void run() {*/
                /*Iterator<Body> allMyBodies = mActivity.getPhysicsWorld().getBodies();
                while(allMyBodies.hasNext())
                {
                    try {
                        final Body myCurrentBody = allMyBodies.next();
                        mActivity.runOnUpdateThread(new Runnable() {
                            @Override
                            public void run() {
                                mActivity.getPhysicsWorld().destroyBody(myCurrentBody);
                            }
                        });
                    } catch (Exception e) {
                        mActivity.log("Level : Could not destroy body " + e);
                    }
                }*/
                for (int i = 0; i < objects.size(); i++) {
                    try {
                        final int myI = i;
                        scene.detachChild(objects.get(myI).getShape());
                        mActivity.getPhysicsWorld().destroyBody(objects.get(myI).getBody());
                    } catch (Exception e) {
                        mActivity.log("Level : Could not destroy enemy " + e);
                    }
                }
                hurtBoxes.clear();

                for (int i = 0; i < hurtBoxes.size(); i++) {
                    try {
                        final int myI = i;
                        scene.detachChild(hurtBoxes.get(myI).getShape());
                        mActivity.getPhysicsWorld().destroyBody(hurtBoxes.get(myI).getBody());
                    } catch (Exception e) {
                        mActivity.log("Level : Could not destroy enemy " + e);
                    }
                }
                hurtBoxes.clear();

                //Delete music
                if(this.music != null)
                {
                    music.stop();
                    music.release();
                }

                scene.detachChild(mActivity.getThePlayer().getShape());
                scene.detachChild(mActivity.getDebug());
                //Delete foreground
                scene.detachChild(foregroundImage);
                System.gc();
            /*}
        });*/
    }
}
