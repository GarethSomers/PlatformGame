package com.platform.main.GameResources.LevelObjects.StaticObject;

import com.platform.main.GameManager;
import com.platform.main.GameResources.LevelObjects.GameObject;
import com.platform.main.GameResources.LevelObjects.ObjectStatus;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;

/**
 * Created by Gareth Somers on 7/5/14.
 */
public class Background extends GameObject {
    protected String image = "";
    protected int zIndex = 1;
    public Background(GameManager gameManager1)
    {
        this.gameManager = gameManager1;
    }


    /*********************************************************************************************/
    /* OVERRIDE GET SHAPE */
    /*********************************************************************************************/
    @Override
    public Sprite getShape()
    {
        return (Sprite)this.theShape;
    }

    /*********************************************************************************************/
    /* SETTER IMAGE */
    /*********************************************************************************************/
    public void setImage(String newImage)
    {
        if(this.status != ObjectStatus.DECLARED)
        {
            gameManager.getMainActivity().gameToast("Tried to set message after it has been constructed");
        }
        else
        {
            this.image = newImage;
        }
    }

    /*********************************************************************************************/
    /* CREATE SHAPE */
    /*********************************************************************************************/
    @Override
    protected void createShape()
    {
        this.theShape = new Sprite(this.xPos, this.yPos, this.width, this.height, gameManager.getMaterialManager().getTexture(this.image,(int)this.width,(int)this.height),gameManager.getMainActivity().getVertexBufferObjectManager());
    }

    /*********************************************************************************************/
    /* DELAYED CREATION METHODS */
    /*********************************************************************************************/
    @Override
    public void preCreateObject() { }

    @Override
    public void createObject() {
        if(this.status == ObjectStatus.DECLARED)
        {
            this.preCreateObject();

            //create the shape object
            this.createShape();
            //set attached to true
            this.setStatus(ObjectStatus.CONSTRUCTED);
            //add them to the world
            this.addToSpriteWorld();
            this.setStatus(ObjectStatus.ATTACHED);

            this.afterCreateObject();
        }
    }

    @Override
    public void afterCreateObject() {
        this.getShape().setZIndex(this.zIndex);
    }



    /*********************************************************************************************/
    /* Z INDEX */
    /*********************************************************************************************/
    public void setZIndex(int zIndex)
    {
        this.zIndex = zIndex;
    }
}
