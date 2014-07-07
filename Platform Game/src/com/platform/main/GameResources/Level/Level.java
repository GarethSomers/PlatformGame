package com.platform.main.GameResources.Level;

import com.platform.main.GameManager;
import com.platform.main.GameResources.LevelObjects.ObjectStatus;
import com.platform.main.GameResources.LevelObjects.StaticObject.ParallaxBackground;
import com.platform.main.GameResources.LevelObjects.StaticObject.ParallaxLayer;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

/**
 * Created by Gareth Somers on 5/11/14.
 */
public abstract class Level {
    protected GameManager gameManager;
    protected Scene scene = new Scene();
    protected Music music;
    protected ObjectStatus status = ObjectStatus.ATTACHED;
    protected ParallaxLayer parallaxLayer;

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

    public void destroy()
    {

    }

    public void update()
    {


    }


    /*********************************************************************************************/
    /* ATTACH PARRALAX BACKGROUND */
    /*********************************************************************************************/

    public void attachParralaxBackground(com.platform.main.GameResources.LevelObjects.StaticObject.ParallaxBackground background) {
        if(background.getParallaxOffset() == 0)
        {
            this.parallaxLayer.attachParallaxEntity(new ParallaxLayer.ParallaxEntity(background.getParallaxSpeed(), background.getShape(), true));
        }
        else
        {
            this.parallaxLayer.attachParallaxEntity(new ParallaxLayer.ParallaxEntity(background.getParallaxSpeed(), background.getShape(), true, background.getParallaxOffset()));
        }
    }

}
