package com.platform.main;

import com.badlogic.gdx.physics.box2d.Body;

public class ClippingPlatform
        extends RectangularPlatform
{
    public ClippingPlatform(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, MainActivity paramMainActivity)
    {
        super(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramMainActivity);
        this.body.setUserData(this);
    }
}
