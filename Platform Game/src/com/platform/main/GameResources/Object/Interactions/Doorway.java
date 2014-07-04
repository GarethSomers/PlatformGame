package com.platform.main.GameResources.Object.Interactions;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.platform.main.GameManager;
import com.platform.main.GameResources.Object.Platforms.RectangularPlatform;

public class Doorway
        extends RectangularPlatform
{
    private String destination;
    private int destinationX;
    private int destinationY;

    public Doorway(GameManager gameManager)
    {
        super(gameManager);
    }

    public String getDestination()
    {
        return this.destination;
    }

    public int getDestinationX()
    {
        return this.destinationX;
    }

    public int getDestinationY()
    {
        return this.destinationY;
    }


    /*********************************************************************************************/
    /* CREATE OBJECT METHOD */
    /*********************************************************************************************/

    @Override
    public void preCreateObject() {
        this.updatePosition = false;
    }

    @Override
    public void afterCreateObject() {
        this.getShape().setAlpha(0);
        ((Fixture)this.body.getFixtureList().get(0)).setSensor(true);
        this.getBody().setUserData(this);
    }
}
