package com.platform.main.GameResources.Object;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.platform.main.GameManager;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.entity.shape.RectangularShape;

public abstract class BodyObject
{
    protected GameManager gameManager;
    protected Body body;
    protected FixtureDef fixtureDef;
    protected RectangularShape theShape;
    protected TiledTextureRegion mTiledTextureRegion;
    protected PhysicsConnector physicsConnector;
    protected BodyDef.BodyType bodyType = null;

    //whether or not to update the position (doors won't be, people will)
    protected boolean updatePosition = false;

    //these variables control whether or not to construct the object or check if its attached
    protected boolean attached = false;
    protected boolean constructed = false;

    //these are used by the JSON construction to allow the units to be entered one at a time.
    protected int width = 0;
    protected int height = 0;
    protected int xPos = 0;
    protected int yPos = 0;


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
    /* GETTER / SETTER WIDTH */
    /*********************************************************************************************/
    public float getWidth()
    {
        return this.getShape().getWidth();
    }

    public void setWidth(int newWidth)
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

    public void setHeight(int newHeight)
    {
        this.width = newHeight;
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
        this.getBody().setTransform(new Vector2(paramFloat1 / 32, paramFloat2 / 32), 0);
    }


    /*********************************************************************************************/
    /* GETTER / SETTER X POSITION*/
    /*********************************************************************************************/
    public float getX()
    {
        return this.getShape().getX();
    }
    public void setX(int paramFloat)
    {
        this.xPos = paramFloat;
        if(this.theShape != null)
        {
            this.theShape.setPosition(paramFloat, this.body.getPosition().y);
        }
    }



    /*********************************************************************************************/
    /* GETTER / SETTER Y POSITION*/
    /*********************************************************************************************/
    public float getY()
    {
        return this.getShape().getY();
    }
    public void setY(int paramFloat)
    {
        this.yPos = paramFloat;
        if(this.theShape != null)
        {
            this.theShape.setPosition(this.body.getPosition().x, paramFloat);
        }
    }





    /*********************************************************************************************/
    /* GETTER / SETTER ATTACHED */
    /*********************************************************************************************/
    public boolean getAttached()
    {
        return this.attached;
    }
    public void setAttached(boolean attached)
    {
        this.attached = attached;
    }



    /*********************************************************************************************/
    /* GETTER - BODY AND SHAPE */
    /*********************************************************************************************/

    /*
        GET BODY
     */
    public Body getBody()
    {
        return this.body;
    }

    /*
        GET SHAPE
     */
    public RectangularShape getShape()
    {
        return this.theShape;
    }





    /*********************************************************************************************/
    /* METHODS - ADD TO WORLD */
    /*********************************************************************************************/

    /*
        ADD TO PHYSICS WORLD
     */
    protected void addToPhysicsWorld()
    {
        if(this.physicsConnector != null)
        {
            this.gameManager.getPhysicsWorld().unregisterPhysicsConnector(physicsConnector);
        }
        this.physicsConnector = new PhysicsConnector(this.getShape(), this.body, this.updatePosition, false);
        this.gameManager.getPhysicsWorld().registerPhysicsConnector(physicsConnector);
    }

    /*
        ADD TO SPRITE WORLD
     */

    protected void addToSpriteWorld()
    {
        if(this.getShape().hasParent() == true)
        {
            this.getShape().getParent().detachChild(this.getShape());
        }
        this.gameManager.getLevelManager().getScene().attachChild(this.getShape());
    }

    /*
        ADD TO WORLD
     */
    public void addToWorld()
    {
        this.addToPhysicsWorld();
        this.addToSpriteWorld();
        this.attached = true;
    }




    /*********************************************************************************************/
    /* METHODS */
    /*********************************************************************************************/

    public void reload(int xPos, int yPos)
    {
        this.setPos(xPos, yPos);
        this.addToWorld();
    }

    public void createShape()
    {
        this.theShape = new Rectangle(this.xPos, this.yPos, this.width, this.height, gameManager.getMainActivity().getVertexBufferObjectManager());
    }

    public void createBody()
    {
        // Create Body
        this.body = PhysicsFactory.createBoxBody(gameManager.getPhysicsWorld(), this.theShape, this.bodyType, this.fixtureDef);
        this.body.setAwake(false);
        this.body.setUserData(this);
    }
}
