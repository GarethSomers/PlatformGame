package com.platform.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;

public class Level
{
    private MainActivity mActivity;
    private Scene scene = new Scene();
    //music
    private Music music;
    //backgrounds
    private ParallaxBackground.ParallaxEntity autoParallaxBackground;
    private Sprite foregroundImage;
    //objets
    private ArrayList<RectangularPlatform> platforms = new ArrayList();
    private ArrayList<Enemy> enemiesNeedRemoving = new ArrayList();
    private ArrayList<Enemy> hurtBoxes = new ArrayList();

    public Level(MainActivity paramMainActivity)
    {
        this.mActivity = paramMainActivity;
    }

    public void setBackgroundImages(String pBackground, String pForeground,int pWidth, int pHeight)
    {
        //Add Backgrounds
        ParallaxBackground localParallaxBackground = new ParallaxBackground(0.0F, 0.0F, 0.0F);
        localParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(1.0F, new Sprite(0.0F, 0.0F, this.mActivity.getMaterialManager().getMaterial(pBackground, pWidth, pHeight), this.mActivity.getVertexBufferObjectManager())));
        this.scene.setBackground(localParallaxBackground);
        this.foregroundImage = new Sprite(0.0F, 0.0F, this.mActivity.getMaterialManager().getMaterial(pForeground, pWidth, pHeight), this.mActivity.getVertexBufferObjectManager());
        this.scene.attachChild(this.foregroundImage);

        //Set the camera
        mActivity.getCamera().setBounds(0.0F, 0.0F, pWidth, pHeight);

        //Add Default Movement Constraints
        this.platforms.add(new ClippingPlatform(0.0F, -1.0F, pWidth, 1.0F, mActivity));
        this.platforms.add(new ClippingPlatform(-1.0F, 0.0F, 1.0F, pHeight, mActivity));
        this.platforms.add(new ClippingPlatform(0.0F, pHeight + 1, pWidth, 1.0F, mActivity));
        this.platforms.add(new ClippingPlatform(pWidth + 1, 0.0F, 1.0F, pHeight, mActivity));
    }

    public void addRectangularPlatform(RectangularPlatform pRectangularPlatform)
    {
        this.platforms.add(pRectangularPlatform);
    }

    public void completeLevelLoading()
    {
        //This just completes the loading of the level by loading in the platforms and enemies
        for(RectangularPlatform p : this.platforms)
        {
            if(p.getAttached() == false)
            {
                this.scene.attachChild(p.getRectangle());
            }
        }
        for(Enemy h : this.hurtBoxes)
        {
            this.scene.attachChild(h.getSprite());
        }
    }

    @Deprecated
    public Level(int paramInt1, int paramInt2, String paramString1, String paramString2, ArrayList<RectangularPlatform> paramArrayList, ArrayList<Enemy> paramArrayList1, MainActivity paramMainActivity)
    {
        this.mActivity = paramMainActivity;
        this.scene = new Scene();
        this.platforms = paramArrayList;
        this.hurtBoxes = paramArrayList1;
        ParallaxBackground localParallaxBackground = new ParallaxBackground(0.0F, 0.0F, 0.0F);
        localParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(1.0F, new Sprite(0.0F, 0.0F, this.mActivity.getMaterialManager().getMaterial(paramString1, paramInt1, paramInt2), this.mActivity.getVertexBufferObjectManager())));
        this.scene.setBackground(localParallaxBackground);
        this.foregroundImage = new Sprite(0.0F, 0.0F, this.mActivity.getMaterialManager().getMaterial(paramString2, paramInt1, paramInt2), this.mActivity.getVertexBufferObjectManager());
        this.scene.attachChild(this.foregroundImage);
        paramMainActivity.getCamera().setBounds(0.0F, 0.0F, paramInt1, paramInt2);
        paramArrayList.add(new ClippingPlatform(0.0F, -1.0F, paramInt1, 1.0F, paramMainActivity));
        paramArrayList.add(new ClippingPlatform(-1.0F, 0.0F, 1.0F, paramInt2, paramMainActivity));
        paramArrayList.add(new ClippingPlatform(0.0F, paramInt2 + 1, paramInt1, 1.0F, paramMainActivity));
        paramArrayList.add(new ClippingPlatform(paramInt1 + 1, 0.0F, 1.0F, paramInt2, paramMainActivity));

        this.setMusicByString("music");

        for(RectangularPlatform p : this.platforms)
        {
            this.scene.attachChild(p.getRectangle());
        }
        for(Enemy h : this.hurtBoxes)
        {
            this.scene.attachChild(h.getSprite());
        }
    }

    public ArrayList<Enemy> getEnemies()
    {
        return this.hurtBoxes;
    }

    public void addEnemy(Enemy e)
    {
        this.scene.attachChild(e.getSprite());
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

    public ArrayList<RectangularPlatform> getPlatforms()
    {
        return this.platforms;
    }

    public void addPlatform(RectangularPlatform p)
    {
        p.setAttached(true);
        this.platforms.add(p);
        this.scene.attachChild(p.getRectangle());
    }

    public Scene getScene()
    {
        return this.scene;
    }

    public void setEnemiesNeedRemoving(ArrayList<Enemy> paramArrayList)
    {
        this.enemiesNeedRemoving = paramArrayList;
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
}
