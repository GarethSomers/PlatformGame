package com.platform.main.GameResources.Object;

import com.badlogic.gdx.physics.box2d.BodyDef;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.entity.sprite.AnimatedSprite;

import com.platform.main.*;

public abstract class AnimatedGameObject extends BodyObject implements Animatable
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
        if(this.constructed == true)
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
