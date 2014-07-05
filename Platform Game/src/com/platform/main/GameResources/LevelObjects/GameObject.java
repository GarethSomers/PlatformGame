package com.platform.main.GameResources.LevelObjects;

import com.badlogic.gdx.math.Vector2;
import com.platform.main.GameManager;
import com.platform.main.GameResources.DelayedCreationObject;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.shape.RectangularShape;

/**
 * Created by Gareth Somers on 7/5/14.
 */
public abstract class GameObject implements DelayedCreationObject
{
    //these are used by the JSON construction to allow the units to be entered one at a time.
    protected float width = 0;
    protected float height = 0;
    protected float xPos = 0;
    protected float yPos = 0;

    protected GameManager gameManager;
    //these variables control whether or not to construct the object or check if its attached
    protected ObjectStatus status = ObjectStatus.DECLARED;
    protected RectangularShape theShape;

    /*********************************************************************************************/
    /* SET AND GET STATUS */
    /*********************************************************************************************/
    public ObjectStatus setStatus()
    {
        return this.status;
    }
    public void setStatus(ObjectStatus newStatus)
    {
        this.status = newStatus;
    }


    /*********************************************************************************************/
    /* GETTER / SETTER X POSITION*/
    /*********************************************************************************************/
    public float getX()
    {
        return this.getShape().getX();
    }
    public void setX(float paramFloat)
    {
        this.xPos = paramFloat;
        if(this.theShape != null)
        {
            this.theShape.setX(paramFloat);
        }
    }



    /*********************************************************************************************/
    /* GETTER / SETTER Y POSITION*/
    /*********************************************************************************************/
    public float getY()
    {
        return this.getShape().getY();
    }
    public void setY(float paramFloat)
    {
        this.yPos = paramFloat;
        if(this.theShape != null)
        {
            this.theShape.setY(paramFloat);
        }
    }



    /*********************************************************************************************/
    /* GETTER ALTERNATIVE POSITION */
    /*********************************************************************************************/
    public float getCenterXPos()
    {
        return this.getX() + this.getWidth() / 2.0F;
    }

    public float getCenterYPos()
    {
        return  this.getY() + this.getHeight() / 2.0F;
    }

    public float getEndPos()
    {
        return this.getX() + this.getWidth();
    }

    public float getBottomPos()
    {
        return this.getX() + this.getWidth();
    }



    /*********************************************************************************************/
    /* SET DIMENSIONS */
    /*********************************************************************************************/
    public void setDimensions(float newWidth, float newHeight)
    {
        this.setWidth(newWidth);
        this.setHeight(newHeight);
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



    /*********************************************************************************************/
    /* SET POSITION */
    /*********************************************************************************************/
    public void setPos(float paramFloat1, float paramFloat2)
    {
        this.setX(paramFloat1);
        this.setY(paramFloat2);
    }



    /*********************************************************************************************/
    /* GET SHAPE */
    /*********************************************************************************************/
    public RectangularShape getShape()
    {
        return this.theShape;
    }



    /*********************************************************************************************/
    /* CREATE SHAPE */
    /*********************************************************************************************/
    protected void createShape()
    {
        this.theShape = new Rectangle(this.xPos, this.yPos, this.width, this.height, gameManager.getMainActivity().getVertexBufferObjectManager());
    }


    /*********************************************************************************************/
    /* ADD TO WORLD */
    /*********************************************************************************************/
    protected void addToSpriteWorld()
    {
        if(this.getShape().hasParent() == true)
        {
            this.getShape().getParent().detachChild(this.getShape());
        }
        this.gameManager.getLevelManager().getScene().attachChild(this.getShape());
    }
}
