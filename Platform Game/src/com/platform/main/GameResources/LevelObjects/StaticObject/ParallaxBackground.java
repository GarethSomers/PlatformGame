package com.platform.main.GameResources.LevelObjects.StaticObject;

import com.platform.main.GameManager;
import com.platform.main.GameResources.LevelObjects.ObjectStatus;

/**
 * Created by Gareth Somers on 7/5/14.
 */
public class ParallaxBackground extends Background {
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
        this.gameManager.getLevelManager().getLevel().attachParralaxBackground(this.getShape());
    }
}
