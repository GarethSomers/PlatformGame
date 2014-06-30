package com.platform.main;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.texture.region.TextureRegion;

public abstract class AreaSensor
{
    protected Body body;
    private Rectangle clipping;
    protected FixtureDef fixtureDef;
    private float height;
    protected GameManager gameManager;
    protected TextureRegion mTextureRegion;
    protected boolean solid;
    private float width;
    protected float xPos = 100.0F;
    protected float yPos = 100.0F;

    public float getBottomPos()
    {
        return this.yPos + this.height;
    }

    public Rectangle getClipping()
    {
        return this.clipping;
    }

    public float getEndPos()
    {
        return this.xPos + this.width;
    }

    public float getXPos()
    {
        return this.xPos;
    }

    public float getYPos()
    {
        return this.yPos;
    }

    public boolean isSolid()
    {
        return this.solid;
    }

    public void setClipping(Rectangle paramRectangle)
    {
        this.clipping = paramRectangle;
    }

    public void setXPos(float paramFloat)
    {
        this.xPos = paramFloat;
    }

    public void setYPos(float paramFloat)
    {
        this.yPos = paramFloat;
    }
}
