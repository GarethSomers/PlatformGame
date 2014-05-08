package com.platform.main.gameobject;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.platform.main.MainActivity;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;

public class RectangularPlatform extends BodyObject
{
    public RectangularPlatform(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, MainActivity paramMainActivity)
    {
        this.mActivity = paramMainActivity;
        this.fixtureDef = PhysicsFactory.createFixtureDef(1.0F, 0.0F, 1.0F);
        this.theShape = new Rectangle(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramMainActivity.getVertexBufferObjectManager());
        this.theShape.setAlpha(0.0F);
        this.body = PhysicsFactory.createBoxBody(paramMainActivity.getPhysicsWorld(), this.theShape, BodyDef.BodyType.StaticBody, this.fixtureDef);
        this.body.setAwake(false);
        this.body.setUserData(this);
        paramMainActivity.getPhysicsWorld().registerPhysicsConnector(new PhysicsConnector(this.theShape, this.body, false, false));
    }
}
