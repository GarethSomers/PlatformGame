package com.platform.main;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;

public class RectangularPlatform
{
    protected Body body;
    protected FixtureDef fixtureDef;
    protected float height;
    protected MainActivity mActivity;
    protected Rectangle theRectangle;
    protected float width;
    protected float xPos = 100.0F;
    protected float yPos = 100.0F;
    protected boolean attached = false;

    public RectangularPlatform(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, MainActivity paramMainActivity)
    {
        this.mActivity = paramMainActivity;
        this.fixtureDef = PhysicsFactory.createFixtureDef(0.0F, 0.0F, 0.0F);
        this.theRectangle = new Rectangle(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramMainActivity.getVertexBufferObjectManager());
        this.theRectangle.setAlpha(0.0F);
        this.body = PhysicsFactory.createBoxBody(paramMainActivity.getPhysicsWorld(), this.theRectangle, BodyDef.BodyType.StaticBody, this.fixtureDef);
        this.body.setAwake(false);
        this.body.setUserData(this);
        paramMainActivity.getPhysicsWorld().registerPhysicsConnector(new PhysicsConnector(this.theRectangle, this.body, false, false));
    }

    public float getEndPos()
    {
        return this.xPos + this.width;
    }

    public Rectangle getRectangle()
    {
        return this.theRectangle;
    }

    public float getXPos()
    {
        return this.xPos;
    }

    public float getYPos()
    {
        return this.yPos;
    }

    public boolean isPlayerAbovePlatform(float paramFloat1, float paramFloat2, float paramFloat3)
    {
        boolean bool1 = paramFloat1 < this.xPos - paramFloat3;
        boolean bool2 = false;
        if (bool1)
        {
            boolean bool3 = paramFloat1 < getEndPos();
            bool2 = false;
            if (bool3)
            {
                boolean bool4 = paramFloat2 < getYPos();
                bool2 = false;
                if (bool4) {
                    bool2 = true;
                }
            }
        }
        return bool2;
    }

    public void setXPos(float paramFloat)
    {
        this.xPos = paramFloat;
    }

    public void setYPos(float paramFloat)
    {
        this.yPos = paramFloat;
    }

    public boolean getAttached()
    {
        return this.attached;
    }

    public void setAttached(boolean attached)
    {
        this.attached = attached;
    }
}
