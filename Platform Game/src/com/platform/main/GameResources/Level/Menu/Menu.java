package com.platform.main.GameResources.Level.Menu;

import com.platform.main.GameResources.Level.Level;
import com.platform.main.MainActivity;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;

public class Menu extends Level implements MenuScene.IOnMenuItemClickListener {
    protected AnimatedSprite foregroundImage;

    protected long[] RAIN = { 100, 100, 100, 100};
    protected int RAIN_S = 0;
    protected int RAIN_E = 3;


    public Menu(MainActivity paramMainActivity)
    {
        super(paramMainActivity);
        this.scene = new MenuScene();

        //create menu items
        final IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(1, 190, 49, this.mActivity.getMaterialManager().getTexture("btnplay.png", 190, 49), this.mActivity.getVertexBufferObjectManager()), 1.1f, 1);
        final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(2, 190, 49, this.mActivity.getMaterialManager().getTexture("btnquit.png", 190, 49), this.mActivity.getVertexBufferObjectManager()), 1.1f, 1);

        //set positions (not working?)
        optionsMenuItem.setPosition(mActivity.getCameraWidth() / 2 - optionsMenuItem.getWidth() / 2, mActivity.getCameraHeight() / 4);
        playMenuItem.setPosition(mActivity.getCameraWidth() / 2 - playMenuItem.getWidth() / 2, 400);

        //add menu items
        this.getScene().addMenuItem(optionsMenuItem);
        this.getScene().addMenuItem(playMenuItem);

        //set the camera
        this.getScene().setCamera(this.mActivity.getCamera());

        //build animations
        this.getScene().buildAnimations();

        //create tiled texture
        TiledTextureRegion mTiledTextureRegion = this.mActivity.getMaterialManager().getTiledTexture("rain.png", 100, 40, 2, 2);

        //create shape
        this.foregroundImage = new AnimatedSprite(0, 0, this.mActivity.getCameraWidth(), this.mActivity.getCameraHeight(),mTiledTextureRegion, this.mActivity.getEngine().getVertexBufferObjectManager());

        //attempt to animate
        this.foregroundImage.animate(RAIN, RAIN_S, RAIN_E, true);
        this.scene.setBackground(new SpriteBackground(this.foregroundImage));

        //register click listerner
        this.getScene().setOnMenuItemClickListener(this);
    }

    /*
    GETTER AND SETTERS
     */
    @Override
    public MenuScene getScene()
    {
        return ((MenuScene)this.scene);
    }

    /*
    METHODS
     */
    public void createPlayButton()
    {
        /*ITextureRegion a = this.mActivity.getMaterialManager().getTiledTexture("btnUp.png", 190, 49);
        ITextureRegion b = this.mActivity.getMaterialManager().getTiledTexture("btnDown.png", 190, 49);
        playButton = new ButtonSprite(0, 0, a, b, this.mActivity.getVertexBufferObjectManager(), new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mActivity.toastOnUIThread("Clicked");
                    }
                });
            }
        });*/
    }

    @Override
    public void completeLevelLoading()
    {
        //nothing
    }

    @Override
    public void destroy()
    {

        /*for (int i = 0; i < objects.size(); i++) {
            try {
                final int myI = i;
                scene.detachChild(objects.get(myI).getShape());
                mActivity.getPhysicsWorld().destroyBody(objects.get(myI).getBody());
            } catch (Exception e) {
                mActivity.log("Level : Could not destroy enemy " + e);
            }
        }
        hurtBoxes.clear();*/


        //Delete music
        if(this.music != null)
        {
            music.stop();
            music.release();
        }

        //Delete foreground
        System.gc();

    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY)
    {
        //handleButtonActions();

        switch(pMenuItem.getID())
        {
            case 1:
                //action
                mActivity.toastOnUIThread("1");
                this.mActivity.completeLoadingScene();
                return true;
            case 2:
                mActivity.toastOnUIThread("2");
                return true;
            default:
                return false;
        }
    }
}
