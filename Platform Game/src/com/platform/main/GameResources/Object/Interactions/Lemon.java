package com.platform.main.GameResources.Object.Interactions;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.platform.main.GameManager;
import com.platform.main.GameResources.Object.AnimatedGameObject;

public class Lemon extends AnimatedGameObject
{
    protected boolean alive = true;
    public Lemon(float paramFloat1, float paramFloat2, GameManager mMainActivity)
    {
        super(paramFloat1, paramFloat2, 16, 16, "lemon.png", BodyDef.BodyType.StaticBody, mMainActivity);
        this.body.setUserData(this);
        this.fixtureDef.isSensor = true;
        ((Fixture)this.body.getFixtureList().get(0)).setSensor(true);
    }

    public void collect()
    {
        if(this.alive == true)
        {
            this.getShape().setVisible(false);
            this.alive = false;
            this.gameManager.getThePlayer().addHealth(5);
        }
    }
}
