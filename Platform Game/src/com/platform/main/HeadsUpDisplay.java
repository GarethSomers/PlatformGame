package com.platform.main;

import android.graphics.Typeface;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;

/**
 * Created by Gareth Somers on 6/12/14.
 */

public class HeadsUpDisplay extends HUD
{
    private Text speedText;
    protected GameManager gameManager;
    private Font mFont;
    private Sprite jumpIcon;
    private float hudPadding = 50f;
    //buttons
    ButtonSprite leftButton;
    ButtonSprite rightButton;
    ButtonSprite jumpButton;
    ButtonSprite doorButton;

    public HeadsUpDisplay(final GameManager gameManager)
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




        /*********************************************************************************************/
        /* LEFT BUTTON */
        /*********************************************************************************************/
        //left button off is around the actual left button which disables movement when you move out of the area
        /*this.leftButtonOff = new ButtonSprite(25, gameManager.getMainActivity().getCameraHeight()-75, gameManager.getMaterialManager().getTexture("HUD/buttonOff.png",100,100), gameManager.getMainActivity().getEngine().getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pTouchEvent.isActionDown() || pTouchEvent.isActionMove())
                {
                    gameManager.getThePlayer().moveStop();
                }
                return super.onAreaTouched(pTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        this.leftButtonOff.setScaleCenter(0,100);
        this.leftButtonOff.setScale(gameManager.getMainActivity().zoomFactor, gameManager.getMainActivity().zoomFactor);
        this.registerTouchArea(leftButtonOff);
        //this.attachChild(leftButtonOff);*/

        this.leftButton = new ButtonSprite(hudPadding, gameManager.getMainActivity().getCameraHeight()-50-hudPadding, gameManager.getMaterialManager().getTexture("HUD/moveLeft.png",50,50), gameManager.getMainActivity().getEngine().getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pTouchEvent.isActionDown()) {
                    gameManager.getThePlayer().moveLeft();
                }
                else if (pTouchEvent.isActionUp())
                {
                    gameManager.getThePlayer().moveStop();
                }
                return super.onAreaTouched(pTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        this.leftButton.setScaleCenter(0,50);
        this.leftButton.setScale(gameManager.getMainActivity().zoomFactor, gameManager.getMainActivity().zoomFactor);
        this.registerTouchArea(leftButton);
        this.attachChild(leftButton);


        /*********************************************************************************************/
        /* RIGHT BUTTON */
        /*********************************************************************************************/
        /*this.rightButtonOff = new ButtonSprite(gameManager.getMainActivity().getCameraWidth()-75, gameManager.getMainActivity().getCameraHeight()-75, gameManager.getMaterialManager().getTexture("HUD/buttonOff.png",100,100), gameManager.getMainActivity().getEngine().getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pTouchEvent.isActionDown() || pTouchEvent.isActionMove())
                {
                    gameManager.getThePlayer().moveStop();
                }
                gameManager.getMainActivity().log("is action outside : "+pTouchEvent.isActionOutside());
                return super.onAreaTouched(pTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        this.rightButtonOff.setScaleCenter(100,100);
        this.rightButtonOff.setScale(gameManager.getMainActivity().zoomFactor,gameManager.getMainActivity().zoomFactor);
        this.registerTouchArea(rightButtonOff);*/
        //this.attachChild(rightButtonOff);
        this.rightButton = new ButtonSprite(hudPadding*gameManager.getMainActivity().zoomFactor+hudPadding+hudPadding, gameManager.getMainActivity().getCameraHeight()-100, gameManager.getMaterialManager().getTexture("HUD/moveRight.png",50,50), gameManager.getMainActivity().getEngine().getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pTouchEvent.isActionDown()) {
                    gameManager.getThePlayer().moveRight();
                }
                else if (pTouchEvent.isActionUp() || pTouchEvent.isActionOutside())
                {
                    gameManager.getThePlayer().moveStop();
                }
                gameManager.getMainActivity().log("is action outside : "+pTouchEvent.isActionOutside());
                return super.onAreaTouched(pTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        this.rightButton.setScaleCenter(0,50);
        this.rightButton.setScale(gameManager.getMainActivity().zoomFactor);
        this.registerTouchArea(rightButton);
        this.attachChild(rightButton);






        /*********************************************************************************************/
        /* JUMP BUTTON */
        /*********************************************************************************************/
        this.jumpButton = new ButtonSprite(gameManager.getMainActivity().getCameraWidth()-hudPadding-50, gameManager.getMainActivity().getCameraHeight()-hudPadding-50, gameManager.getMaterialManager().getTexture("HUD/moveJump.png",50,50), gameManager.getMainActivity().getEngine().getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pTouchEvent.isActionDown()) {
                    gameManager.getThePlayer().jump();
                }
                return super.onAreaTouched(pTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        this.jumpButton.setScaleCenter(50,50);
        this.jumpButton.setScale(gameManager.getMainActivity().zoomFactor,gameManager.getMainActivity().zoomFactor);
        this.registerTouchArea(jumpButton);
        this.attachChild(jumpButton);



        /*********************************************************************************************/
        /* DOOR BUTTON */
        /*********************************************************************************************/
        this.doorButton = new ButtonSprite(gameManager.getMainActivity().getCameraWidth()-hudPadding-50, gameManager.getMainActivity().getCameraHeight()-(50*gameManager.getMainActivity().zoomFactor)-hudPadding-hudPadding-50, gameManager.getMaterialManager().getTexture("HUD/doorButton.png",50,50), gameManager.getMainActivity().getEngine().getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pTouchEvent.isActionDown()) {
                    gameManager.getLevelManager().confirmScheduleLoadLevel();
                }
                return super.onAreaTouched(pTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        this.doorButton.setScaleCenter(50,50);
        this.doorButton.setScale(gameManager.getMainActivity().zoomFactor,gameManager.getMainActivity().zoomFactor);
        this.doorButton.setVisible(false);
        this.registerTouchArea(doorButton);
        this.attachChild(doorButton);
    }

    public void setJumpIconVisibility(boolean visibility)
    {
        this.jumpIcon.setVisible(visibility);
    }

    public void setDoorButtonVisibility(boolean visibility)
    {
        this.doorButton.setVisible(visibility);
    }
}
