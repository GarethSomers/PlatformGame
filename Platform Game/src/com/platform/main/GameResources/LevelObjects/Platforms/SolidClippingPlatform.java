package com.platform.main.GameResources.LevelObjects.Platforms;

import com.platform.main.GameManager;

/*********************************************************************************************/
/* SOLID CLIPPING PLATFORM - ALLOWS JUMPING */
/*********************************************************************************************/
public class SolidClippingPlatform
        extends RectangularPlatform
{
    public SolidClippingPlatform(GameManager gameManager)
    {
        super(gameManager);
    }

    @Override
    public void preCreateObject() {
        this.updatePosition = false;
    }

    @Override
    public void createObject() {
        this.createShape();
        this.createBody();
    }

    @Override
    public void afterCreateObject() {
        this.getShape().setAlpha(0);
        this.getBody().setUserData(this);
    }
}
