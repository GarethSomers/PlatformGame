package com.platform.main.GameResources.LevelObjects.Interactions;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.platform.main.GameManager;
import com.platform.main.GameResources.LevelObjects.AnimatedObjects.AnimatedGameObject;

import org.andengine.extension.physics.box2d.PhysicsFactory;

public class Lemon extends Collectable
{
    protected boolean alive = true;
    protected int timeAdded = 10;
    public Lemon(GameManager gameManager)
    {
        super(gameManager);
        this.setWidth(16);
        this.setHeight(16);
        this.setImage("lemon.png");
    }

    public void collect()
    {
        if(this.alive == true)
        {
            this.getShape().setVisible(false);
            this.alive = false;
            this.gameManager.getEventsManager().addTime(timeAdded);
        }
    }

    @Override
    protected void createBody()
    {
        // Create Body
        //this.body = PhysicsFactory.createBoxBody(gameManager.getPhysicsWorld(), this.getShape(), this.bodyType, this.fixtureDef);
        this.body = PhysicsFactory.createCircleBody(gameManager.getPhysicsWorld(), this.getShape(), this.bodyType, this.fixtureDef);
        //this.body.setAwake(true);
    }
}
