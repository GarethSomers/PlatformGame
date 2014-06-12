package com.platform.main.GameResources.Level;

import com.platform.main.MainActivity;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;

/**
 * Created by Gareth Somers on 5/11/14.
 */
public class Level {
    protected MainActivity mActivity;
    protected Scene scene = new Scene();
    protected Music music;

    public Level(MainActivity paramMainActivity)
    {
        this.mActivity = paramMainActivity;
        this.scene = new Scene();
    }

    public Scene getScene()
    {
        return this.scene;
    }

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
            this.setMusic(MusicFactory.createMusicFromAsset(mActivity.getEngine().getMusicManager(), mActivity, "mfx/" + pMusic + ".mp3"));
            getMusic().setLooping(true);
            getMusic().play();
        }
        catch(Exception e)
        {
            mActivity.log("Could not load music (mfx/"+pMusic+".mp3)");
        }
    }


    public void completeLevelLoading()
    {

    }
    public void destroy()
    {

    }

    public void update()
    {

    }
}
