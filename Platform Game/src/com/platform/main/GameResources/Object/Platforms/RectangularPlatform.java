package com.platform.main.GameResources.Object.Platforms;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.platform.main.GameManager;
import com.platform.main.GameResources.Object.BodyObject;
import com.platform.main.GameResources.Object.DelayedCreationObject;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;

public abstract class RectangularPlatform extends BodyObject implements DelayedCreationObject
{
    //variables
    public RectangularPlatform(GameManager gameManager)
    {
        this.updatePosition = false;
        this.bodyType = BodyDef.BodyType.StaticBody;
        this.fixtureDef = PhysicsFactory.createFixtureDef(1.0F, 0.0F, 1.0F);
        this.gameManager = gameManager;
    }


}
