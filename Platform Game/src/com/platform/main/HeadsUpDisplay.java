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
    private GameManager gameManager;
    private Font mFont;
    private Sprite jumpIcon;
    private Sprite doorIcon;
    private float hudPadding = 20f;
    public HeadsUpDisplay(GameManager gameManager)
    {
        //create main activity
        this.gameManager = gameManager;

        //create fonts
        this.mFont = FontFactory.create(this.gameManager.getMainActivity().getFontManager(), this.gameManager.getMainActivity().getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, 1), 24.0F);
        this.mFont.load();

        //add speed text
        this.speedText = new Text(hudPadding, hudPadding, this.mFont, "0", 99999, this.gameManager.getMainActivity().getVertexBufferObjectManager());
        this.speedText.setScaleCenter(0,0);
        this.speedText.setScale(gameManager.getMainActivity().zoomFactor, gameManager.getMainActivity().zoomFactor);
        this.attachChild(this.speedText);

        //add jump icon
        this.jumpIcon = new Sprite(gameManager.getMainActivity().getCameraWidth() - hudPadding - 28, hudPadding,this.gameManager.getMaterialManager().getTexture("jumpGuy.png",28,45),gameManager.getMainActivity().getVertexBufferObjectManager());
        this.jumpIcon.setScaleCenter(28,0);
        this.jumpIcon.setScale(gameManager.getMainActivity().zoomFactor, gameManager.getMainActivity().zoomFactor);
        this.attachChild(this.jumpIcon);
        this.jumpIcon.setVisible(false);

        //add door icon
        this.doorIcon = new Sprite(gameManager.getMainActivity().getCameraWidth() - hudPadding - 28, hudPadding,this.gameManager.getMaterialManager().getTexture("doorIcon.png",28,28),gameManager.getMainActivity().getVertexBufferObjectManager());
        this.doorIcon.setScaleCenter(28,0);
        this.doorIcon.setScale(gameManager.getMainActivity().zoomFactor, gameManager.getMainActivity().zoomFactor);
        this.attachChild(this.doorIcon);
        this.doorIcon.setVisible(false);
    }

    public void setJumpIconVisibility(boolean visibility)
    {
        this.jumpIcon.setVisible(visibility);
    }

    public void setDoorIconVisibility(boolean visibility)
    {
        this.doorIcon.setVisible(visibility);
    }
}
