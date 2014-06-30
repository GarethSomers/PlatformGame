package com.platform.main.GameResources.Level;

import com.platform.main.GameManager;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.entity.scene.Scene;

/**
 * Created by Gareth Somers on 5/11/14.
 */
public class Level {
    protected GameManager gameManager;
    protected Scene scene = new Scene();
    protected Music music;

    public Level(GameManager paramMainActivity)
    {
        this.gameManager = paramMainActivity;
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
            this.setMusic(MusicFactory.createMusicFromAsset(gameManager.getMainActivity().getEngine().getMusicManager(), gameManager.getMainActivity(), "mfx/" + pMusic + ".mp3"));
            getMusic().setLooping(true);
            getMusic().play();
        }
        catch(Exception e)
        {
            gameManager.getMainActivity().log("Could not load music (mfx/"+pMusic+".mp3)");
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
