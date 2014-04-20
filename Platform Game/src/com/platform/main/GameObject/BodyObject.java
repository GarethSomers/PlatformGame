package com.platform.main.gameobject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.platform.main.MainActivity;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.entity.shape.RectangularShape;

public abstract class BodyObject
{
    protected Body body;
    protected FixtureDef fixtureDef;
    protected MainActivity mActivity;
    protected RectangularShape theShape;
    protected TiledTextureRegion mTiledTextureRegion;
    protected boolean attached = false;
    protected PhysicsConnector physicsConnector;

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

    public float getWidth()
    {
        return this.getShape().getWidth();
    }

    public float getHeight()
    {
        return this.getShape().getHeight();
    }

    public float getX()
    {
        return this.getBody().getPosition().x;
    }

    public float getY()
    {
        return this.getBody().getPosition().y;
    }

    public void setX(float paramFloat)
    {
        this.theShape.setPosition(paramFloat, this.body.getPosition().y);
    }

    public void setY(float paramFloat)
    {
        this.theShape.setPosition(this.body.getPosition().x, paramFloat);
    }

    public void setPos(float paramFloat1, float paramFloat2)
    {
        this.getBody().setTransform(new Vector2(paramFloat1 / 32, paramFloat2 / 32), 0);
    }

    public boolean getAttached()
    {
        return this.attached;
    }

    public void setAttached(boolean attached)
    {
        this.attached = attached;
    }

    public Body getBody()
    {
        return this.body;
    }

    public RectangularShape getShape()
    {
        return this.theShape;
    }
}
