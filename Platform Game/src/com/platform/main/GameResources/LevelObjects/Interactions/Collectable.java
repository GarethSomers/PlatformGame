package com.platform.main.GameResources.LevelObjects.Interactions;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.platform.main.GameManager;
import com.platform.main.GameResources.LevelObjects.AnimatedObjects.AnimatedGameObject;

public abstract class Collectable extends AnimatedGameObject
{
    protected boolean alive = true;
    public Collectable(GameManager gameManager)
    {
        super(gameManager);
        this.bodyType = BodyDef.BodyType.StaticBody;
        this.fixtureDef.isSensor = true;
    }

    public void collect(){}

    @Override
    public void setAnimation(String paramString) {}

    @Override
    public void afterCreateObject() {
        this.getBody().setAwake(true);
        this.getBody().setUserData(this);
        this.getShape().setZIndex(this.zIndex);
    }
}
