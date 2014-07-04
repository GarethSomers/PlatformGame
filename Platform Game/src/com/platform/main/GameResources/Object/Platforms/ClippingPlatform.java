package com.platform.main.GameResources.Object.Platforms;

import com.platform.main.GameManager;

public class ClippingPlatform
        extends RectangularPlatform
{
    public ClippingPlatform(GameManager gameManager)
    {
        super(gameManager);
    }

    @Override
    public void preCreateObject() {
        this.updatePosition = false;
    }

    @Override
    public void afterCreateObject() {
        this.getShape().setAlpha(0);
        this.getBody().setUserData(this);
    }
}
