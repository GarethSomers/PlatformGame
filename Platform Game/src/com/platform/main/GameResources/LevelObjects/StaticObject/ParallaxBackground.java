package com.platform.main.GameResources.LevelObjects.StaticObject;

import com.platform.main.GameManager;
import com.platform.main.GameResources.LevelObjects.ObjectStatus;

/**
 * Created by Gareth Somers on 7/5/14.
 */
public class ParallaxBackground extends Background {
    int offset = 0;
    float parallaxSpeed = 0.2f;
    int repeat = -1;
    public ParallaxBackground(GameManager gameManager1) {
        super(gameManager1);
    }

    /*********************************************************************************************/
    /* ADD TO WORLD */
    /*********************************************************************************************/
    @Override
    protected void addToSpriteWorld()
    {
        if(this.getShape().hasParent() == true)
        {
            this.getShape().getParent().detachChild(this.getShape());
        }
        this.gameManager.getLevelManager().getLevel().attachParralaxBackground(this);
    }

    public int getParallaxOffset()
    {
        return this.offset;
    }
    public void setParallaxOffset(int currentValue) {
        this.offset = currentValue;
    }

    public float getParallaxSpeed()
    {
        return this.parallaxSpeed;
    }
    public void setParallaxSpeed(float i) {
        this.parallaxSpeed = i;
    }

    public int getRepeat()
    {
        return this.repeat;
    }
    public void setRepeat(int newRepeat)
    {
        this.repeat = newRepeat;
    }
}
