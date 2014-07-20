package com.platform.main.GameResources.LevelObjects.Platforms;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.platform.main.GameManager;
import com.platform.main.GameResources.LevelObjects.BodyObject;
import com.platform.main.GameResources.LevelObjects.ObjectStatus;

import org.andengine.extension.physics.box2d.PhysicsFactory;

public abstract class RectangularPlatform extends BodyObject
{
    //variables
    public RectangularPlatform(GameManager gameManager)
    {
        this.updatePosition = false;
        this.bodyType = BodyDef.BodyType.StaticBody;
        this.fixtureDef = PhysicsFactory.createFixtureDef(1.0F, 0.0F, 0.6F);
        this.gameManager = gameManager;
    }

    /*********************************************************************************************/
    /* OBJECT CONSTRUCTION */
    /*********************************************************************************************/
    @Override
    public void createObject() {
        if(this.status == ObjectStatus.DECLARED)
        {
            this.preCreateObject();

            //create the shape object
            this.createShape();
            //create the body object
            this.createBody();
            //set attached to true
            this.setStatus(ObjectStatus.CONSTRUCTED);
            //add them to the world
            this.addToSpriteWorld();
            this.addToPhysicsWorld();
            this.setStatus(ObjectStatus.ATTACHED);

            this.afterCreateObject();
        }
    }
}
