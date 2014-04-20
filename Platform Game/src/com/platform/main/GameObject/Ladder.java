package com.platform.main.gameobject;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.platform.main.MainActivity;
import com.platform.main.gameobject.RectangularPlatform;

public class Ladder
        extends RectangularPlatform
{
    public Ladder(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, MainActivity paramMainActivity)
    {
        super(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramMainActivity);
        ((Fixture)this.body.getFixtureList().get(0)).setSensor(true);
        this.body.setUserData(this);
    }
}
