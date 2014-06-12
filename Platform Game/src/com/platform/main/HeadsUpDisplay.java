package com.platform.main;

import android.graphics.Typeface;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;

/**
 * Created by Gareth Somers on 6/12/14.
 */

public class HeadsUpDisplay extends HUD
{
    private Text speedText;
    private MainActivity mainActivity;
    private Font mFont;
    private Sprite jumpIcon;
    private float hudPadding = 20f;
    public HeadsUpDisplay(MainActivity mainActivity)
    {
        //create main activity
        this.mainActivity = mainActivity;

        //create fonts
        this.mFont = FontFactory.create(this.mainActivity.getFontManager(), this.mainActivity.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, 1), 24.0F);
        this.mFont.load();

        //add speed text
        this.speedText = new Text(hudPadding, hudPadding, this.mFont, "0", 99999, this.mainActivity.getVertexBufferObjectManager());
        this.speedText.setScaleCenter(0,0);
        this.speedText.setScale(mainActivity.zoomFactor, mainActivity.zoomFactor);
        this.attachChild(this.speedText);

        //add jump icon
        this.jumpIcon = new Sprite(mainActivity.getCameraWidth() - hudPadding - 28, hudPadding,this.mainActivity.getMaterialManager().getTexture("jumpGuy.png",28,45),mainActivity.getVertexBufferObjectManager());
        this.jumpIcon.setScaleCenter(28,0);
        this.jumpIcon.setScale(mainActivity.zoomFactor, mainActivity.zoomFactor);
        this.attachChild(this.jumpIcon);
        this.jumpIcon.setVisible(false);
    }

    public void setJumpIconVisibility(boolean visibility)
    {
        this.jumpIcon.setVisible(visibility);
    }
}
