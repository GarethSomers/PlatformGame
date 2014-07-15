package com.platform.main.GameResources.LevelObjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.platform.main.GameManager;
import com.platform.main.GameResources.DelayedCreationObject;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;

public abstract class BodyObject extends GameObject
{
    protected Body body;
    protected FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(1f, 0f, 0.2f);
    protected PhysicsConnector physicsConnector;
    protected BodyDef.BodyType bodyType = null;

    //whether or not to update the position (doors won't be, people will)
    protected boolean updatePosition = false;


    /*********************************************************************************************/
    /* GET BODY  */
    /*********************************************************************************************/
    public Body getBody()
    {
        return this.body;
    }


    /*********************************************************************************************/
    /* ADD TO PHYSICS WORLD */
    /*********************************************************************************************/
    protected void addToPhysicsWorld()
    {
        if(this.physicsConnector != null)
        {
            this.gameManager.getPhysicsWorld().unregisterPhysicsConnector(physicsConnector);
        }
        this.physicsConnector = new PhysicsConnector(this.getShape(), this.body, this.updatePosition, false);
        this.gameManager.getPhysicsWorld().registerPhysicsConnector(physicsConnector);
    }



    /*********************************************************************************************/
    /* METHODS */
    /*********************************************************************************************/

    public void reload(int xPos, int yPos)
    {
        this.setPos(xPos, yPos);
        this.addToSpriteWorld();
        this.addToPhysicsWorld();
        Vector2 v2 = Vector2Pool.obtain((xPos /*+ this.getShape().getWidthScaled()*/) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, (yPos /*+ this.getShape().getHeightScaled()*/) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
        body.setTransform(v2, body.getAngle());
        Vector2Pool.recycle(v2);
    }

    protected void createBody()
    {
        // Create Body
        this.body = PhysicsFactory.createBoxBody(gameManager.getPhysicsWorld(), this.getShape(), this.bodyType, this.fixtureDef);
        this.body.setAwake(false);
        this.body.setUserData(this);
    }

}
