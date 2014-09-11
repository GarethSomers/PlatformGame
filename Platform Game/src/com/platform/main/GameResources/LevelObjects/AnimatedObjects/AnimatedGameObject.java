package com.platform.main.GameResources.LevelObjects.AnimatedObjects;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.platform.main.*;
import com.platform.main.GameResources.LevelObjects.BodyObject;
import com.platform.main.GameResources.LevelObjects.ObjectStatus;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import java.util.ArrayList;

public abstract class AnimatedGameObject extends BodyObject implements Animatable
{
    protected ArrayList<Float> polygonShape;
    protected String image = "";
    protected int columns = 1;
    protected int rows = 1;
    protected int zIndex = 5;
    protected TiledTextureRegion mTiledTextureRegion;

    /*********************************************************************************************/
    /* CONSTRUCTOR */
    /*********************************************************************************************/
    public AnimatedGameObject(GameManager paramMainActivity)
    {
        this.gameManager = paramMainActivity;
        this.updatePosition = true;
        this.bodyType = BodyDef.BodyType.DynamicBody;
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

    /*********************************************************************************************/
    /* CREATE OBJECT CALLS */
    /*********************************************************************************************/
    @Override
    public void preCreateObject() {
        this.mTiledTextureRegion = this.gameManager.getMaterialManager().getTiledTexture(this.image, ((int)this.width)*this.columns,  ((int)this.height)*this.rows, this.columns, this.rows);
    }



    /*********************************************************************************************/
    /* GETTER / SETTER WIDTH */
    /*********************************************************************************************/
    public float getWidth()
    {
        return this.getShape().getWidth();
    }

    public void setWidth(float newWidth)
    {
        this.width = newWidth;
        if(this.theShape != null)
        {
            this.theShape.setWidth(newWidth);
        }
    }



    /*********************************************************************************************/
    /* GETTER / SETTER HEIGHT */
    /*********************************************************************************************/
    public float getHeight()
    {
        return this.getShape().getHeight();
    }

    public void setHeight(float newHeight)
    {
        this.height = newHeight;
        if(this.theShape != null)
        {
            this.theShape.setHeight(newHeight);
        }
    }

    public void setPolygon(ArrayList<Float> newPolygon)
    {
        this.polygonShape = newPolygon;
    }
}
