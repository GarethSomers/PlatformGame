package com.platform.main.GameResources.Object;

import com.badlogic.gdx.physics.box2d.BodyDef;

import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.entity.sprite.AnimatedSprite;

import com.platform.main.*;

public class AnimatedGameObject extends BodyObject
{
    //create dynamic (moveable) animated object with no animations.
    public AnimatedGameObject(float xPos, float yPos, int width, int height, String image, GameManager paramMainActivity)
    {
        this(xPos, yPos, width, height, image, 1, 1,BodyDef.BodyType.DynamicBody, paramMainActivity);
    }

    //create animatnion object with passed body type
    public AnimatedGameObject(float xPos, float yPos, int width, int height, String image, BodyDef.BodyType bodyType, GameManager paramMainActivity)
    {
        this(xPos, yPos, width, height, image, 1, 1,bodyType, paramMainActivity);
    }

    //create animated game object moveable
    public AnimatedGameObject(float xPos, float yPos, int width, int height, String image, int columns, int rows, GameManager paramMainActivity)
    {
        this(xPos, yPos, width, height, image, columns, rows,BodyDef.BodyType.DynamicBody, paramMainActivity);
    }

    //main constructor
    public AnimatedGameObject(float xPos, float yPos, int width, int height, String image, int columns, int rows, BodyDef.BodyType bodyType, GameManager paramMainActivity)
    {
        //save main activity
        this.gameManager = paramMainActivity;

        //create fixture
        this.fixtureDef = PhysicsFactory.createFixtureDef(1.0F, 0F, 1.0F);

        //create tiled texture
        this.mTiledTextureRegion = this.gameManager.getMaterialManager().getTiledTexture(image, width*columns, height*rows, columns, rows);

        //create shape
        this.theShape = new AnimatedSprite(xPos, yPos, this.mTiledTextureRegion, this.gameManager.getMainActivity().getEngine().getVertexBufferObjectManager());

        //create box body with shape
        this.body = PhysicsFactory.createBoxBody(paramMainActivity.getPhysicsWorld(), this.getShape(), bodyType, this.fixtureDef);

        //see variables
        this.body.setAwake(false);
        this.body.setUserData(this);
        this.body.setFixedRotation(true);
        this.body.setLinearVelocity(0.0F, 0.0F);

        //register physics connection
        paramMainActivity.getPhysicsWorld().registerPhysicsConnector(new PhysicsConnector(this.getShape(), this.body, true, true));

        //add to the physics world
        //this.addToWorld();
    }


    //get the shape
    public AnimatedSprite getShape()
    {
        return (AnimatedSprite)this.theShape;
    }
}
