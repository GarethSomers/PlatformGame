package com.platform.main.GameResources.Object.Platforms;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.platform.main.GameManager;
import com.platform.main.GameResources.Object.BodyObject;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;

public class RectangularPlatform extends BodyObject
{
    public RectangularPlatform(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, GameManager paramMainActivity)
    {
        this.gameManager = paramMainActivity;
        this.fixtureDef = PhysicsFactory.createFixtureDef(1.0F, 0.0F, 1.0F);
        this.theShape = new Rectangle(paramFloat1, paramFloat2, paramFloat3, paramFloat4, gameManager.getMainActivity().getVertexBufferObjectManager());
        this.theShape.setAlpha(0.0F);
        this.body = PhysicsFactory.createBoxBody(paramMainActivity.getPhysicsWorld(), this.theShape, BodyDef.BodyType.StaticBody, this.fixtureDef);
        this.body.setAwake(false);
        this.body.setUserData(this);
        paramMainActivity.getPhysicsWorld().registerPhysicsConnector(new PhysicsConnector(this.theShape, this.body, false, false));
    }
}
