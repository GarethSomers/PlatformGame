package com.platform.main.gameobject;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.platform.main.MainActivity;

public class Frog extends AnimatedGameObject
{
    //variables
    protected boolean alive = true;
    //animations
    protected long[] FROG_JUMPING = { 100L, 200L, 300L, 400L };
    protected int FROG_JUMPING_S = 0;
    protected int FROG_JUMPING_E = 3;

    //constructor
    public Frog(float paramFloat1, float paramFloat2, MainActivity mMainActivity)
    {
        super(paramFloat1, paramFloat2, 71, 23, "frog.png",2,2, mMainActivity);
        this.body.setUserData(this);
        this.getShape().animate(this.FROG_JUMPING, this.FROG_JUMPING_S, this.FROG_JUMPING_E, true);
    }
}
