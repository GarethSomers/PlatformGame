package com.platform.main.GameResources.Object;

import org.andengine.entity.sprite.AnimatedSprite;

import com.platform.main.*;

public abstract class AnimatedGameObject extends GameObject implements Animatable
{
    protected String image = "";
    protected int columns = 0;
    protected int rows = 0;

    /*********************************************************************************************/
    /* CONSTRUCTOR */
    /*********************************************************************************************/
    public AnimatedGameObject(GameManager paramMainActivity)
    {
        this.gameManager = paramMainActivity;
    }



    /*********************************************************************************************/
    /* SETTER IMAGE */
    /*********************************************************************************************/
    public void setImage(String newImage)
    {
        if(this.status != ObjectStatus.ATTACHED)
        {
            gameManager.getMainActivity().gameToast("Tried to set message after it has been constructed");
        }
        else
        {
            this.image = newImage;
        }
    }




    /*********************************************************************************************/
    /* OVERRIDE SHAPE OBJECT */
    /*********************************************************************************************/

    //get the shape
    public AnimatedSprite getShape()
    {
        return (AnimatedSprite)this.theShape;
    }

    @Override
    public void createShape()
    {
        this.theShape = new AnimatedSprite(xPos, yPos, this.mTiledTextureRegion, this.gameManager.getMainActivity().getEngine().getVertexBufferObjectManager());
    }
}
