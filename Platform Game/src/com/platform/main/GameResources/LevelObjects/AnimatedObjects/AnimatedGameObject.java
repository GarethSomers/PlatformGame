package com.platform.main.GameResources.LevelObjects.AnimatedObjects;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import com.badlogic.gdx.physics.box2d.Shape;
import com.platform.main.*;
import com.platform.main.GameResources.LevelObjects.BodyObject;
import com.platform.main.GameResources.LevelObjects.ObjectStatus;

public abstract class AnimatedGameObject extends BodyObject implements Animatable
{
    protected String image = "";
    protected int columns = 0;
    protected int rows = 0;
    protected TiledTextureRegion mTiledTextureRegion;

    /*********************************************************************************************/
    /* CONSTRUCTOR */
    /*********************************************************************************************/
    public AnimatedGameObject(GameManager paramMainActivity)
    {
        this.gameManager = paramMainActivity;
        this.updatePosition = true;
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
    /* OVERRIDE SHAPE OBJECT */
    /*********************************************************************************************/

    //get the shape
    @Override
    public AnimatedSprite getShape()
    {
        return (AnimatedSprite)this.theShape;
    }

    @Override
    public void createShape()
    {
        this.theShape = new AnimatedSprite(xPos, yPos, this.mTiledTextureRegion, this.gameManager.getMainActivity().getEngine().getVertexBufferObjectManager());
    }

    /*********************************************************************************************/
    /* OBJECT CONSTRUCTION */
    /*********************************************************************************************/
    @Override
    public void createObject() {
        if(this.status == ObjectStatus.DECLARED)
        {
            this.preCreateObject();

            //create the shape object
            this.createShape();
            //create the body object
            this.createBody();
            //set attached to true
            this.setStatus(ObjectStatus.CONSTRUCTED);
            //add them to the world
            this.addToSpriteWorld();
            this.addToPhysicsWorld();
            this.setStatus(ObjectStatus.ATTACHED);

            this.afterCreateObject();
        }
    }
}
