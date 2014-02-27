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
    private ParallaxBackground.ParallaxEntity autoParallaxBackground;
    private ArrayList<Enemy> enemiesNeedRemoving = new ArrayList();
    private Sprite foregroundImage;
    private ArrayList<Enemy> hurtBoxes;
    private MainActivity mActivity;
    private Music music;
    private ArrayList<RectangularPlatform> platforms;
    private Scene scene;

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

        try
        {
            setMusic(MusicFactory.createMusicFromAsset(paramMainActivity.getEngine().getMusicManager(), paramMainActivity, "mfx/music.mp3"));
            getMusic().setLooping(true);
            getMusic().play();
            return;
        }
        catch (IOException localIOException)
        {
            paramMainActivity.gameToast("Could not load music for level");
        }

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

    public Scene getScene()
    {
        return this.scene;
    }

    public void setEnemiesNeedRemoving(ArrayList<Enemy> paramArrayList)
    {
        this.enemiesNeedRemoving = paramArrayList;
    }

    public void setMusic(Music paramMusic)
    {
        this.music = paramMusic;
    }
}
