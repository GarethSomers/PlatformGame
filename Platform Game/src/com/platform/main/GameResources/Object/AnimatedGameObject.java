package com.platform.main.GameResources.Object;

import com.badlogic.gdx.physics.box2d.BodyDef;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.entity.sprite.AnimatedSprite;

import com.platform.main.*;

public class AnimatedGameObject extends BodyObject implements DelayedCreationObject
{
    String image = "";
    int columns = 0;
    int rows = 0;

    //main constructor
    public AnimatedGameObject(GameManager paramMainActivity)
    {
        //save main activity
        this.gameManager = paramMainActivity;

        //create box body with shape
        this.body = PhysicsFactory.createBoxBody(paramMainActivity.getPhysicsWorld(), this.getShape(), this.bodyType, this.fixtureDef);
    }


    //get the shape
    public AnimatedSprite getShape()
    {
        return (AnimatedSprite)this.theShape;
    }

    @Override
    public void preCreateObject() {
        this.mTiledTextureRegion = this.gameManager.getMaterialManager().getTiledTexture(this.image, this.width*this.columns, this.height*this.rows, this.columns, this.rows);
        this.bodyType = BodyDef.BodyType.StaticBody;
    }

    @Override
    public void createShape()
    {
        this.theShape = new AnimatedSprite(xPos, yPos, this.mTiledTextureRegion, this.gameManager.getMainActivity().getEngine().getVertexBufferObjectManager());
    }

    @Override
    public void createObject() {
        this.createShape();
        this.createBody();
    }

    @Override
    public void afterCreateObject() {
        this.getBody().setAwake(false);
        this.getBody().setUserData(this);
        this.getBody().setFixedRotation(true);
        this.getBody().setLinearVelocity(0.0F, 0.0F);
    }
}
