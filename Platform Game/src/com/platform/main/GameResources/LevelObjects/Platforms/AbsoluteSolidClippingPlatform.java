package com.platform.main.GameResources.LevelObjects.Platforms;

import com.platform.main.GameManager;

/*********************************************************************************************/
/* ABSOLUTE SOLID - NEVER ALLOWS JUMPING */
/*********************************************************************************************/
public class AbsoluteSolidClippingPlatform
        extends RectangularPlatform
{
    public AbsoluteSolidClippingPlatform(GameManager gameManager)
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
