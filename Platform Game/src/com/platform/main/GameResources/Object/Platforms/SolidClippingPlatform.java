package com.platform.main.GameResources.Object.Platforms;

import com.platform.main.GameManager;

public class SolidClippingPlatform
        extends RectangularPlatform
{
    public SolidClippingPlatform(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, GameManager paramMainActivity)
    {
        super(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramMainActivity);
        this.body.setUserData(this);
    }
}
