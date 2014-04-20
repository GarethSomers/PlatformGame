package com.platform.main.gameobject;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import com.platform.main.*;

public class AnimatedGameObject extends BodyObject
{
    public AnimatedGameObject(float xPos, float yPos, int width, int height, String image, MainActivity paramMainActivity)
    {
        this(xPos, yPos, width, height, image, 1, 1, paramMainActivity);
    }

    public AnimatedGameObject(float xPos, float yPos, int width, int height, String image, int columns, int rows, MainActivity paramMainActivity)
    {
        this.mActivity = paramMainActivity;
        this.fixtureDef = PhysicsFactory.createFixtureDef(0.0F, 0.0F, 0.0F);
        this.mTiledTextureRegion = this.mActivity.getMaterialManager().getTiledTexture(image, width*columns, height*rows, columns, rows);
        this.theShape = new AnimatedSprite(xPos, yPos, this.mTiledTextureRegion, this.mActivity.getEngine().getVertexBufferObjectManager());
        this.body = PhysicsFactory.createBoxBody(paramMainActivity.getPhysicsWorld(), this.getShape(), BodyDef.BodyType.StaticBody, this.fixtureDef);
        this.body.setAwake(false);
        this.body.setUserData(this);
        paramMainActivity.getPhysicsWorld().registerPhysicsConnector(new PhysicsConnector(this.getShape(), this.body, false, false));
    }

    public AnimatedSprite getShape()
    {
        return (AnimatedSprite)this.theShape;
    }
}
