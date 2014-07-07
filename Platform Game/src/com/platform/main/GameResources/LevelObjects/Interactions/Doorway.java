package com.platform.main.GameResources.LevelObjects.Interactions;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.platform.main.GameManager;
import com.platform.main.GameResources.LevelObjects.Platforms.RectangularPlatform;

public class Doorway
        extends RectangularPlatform
{
    private String destination;
    private int destinationX = 50;
    private int destinationY = 50;

    public Doorway(GameManager gameManager)
    {
        super(gameManager);
    }


    /*********************************************************************************************/
    /* SET DESTINATION X */
    /*********************************************************************************************/
    public int getDestinationX()
    {
        return this.destinationX;
    }
    public void setDestinationX(int destinationX) { this.destinationX = destinationX; }

    /*********************************************************************************************/
    /* SET DESTINATION Y */
    /*********************************************************************************************/
    public int getDestinationY() { return this.destinationY; }
    public void setDestinationY(int destinationY) { this.destinationY = destinationY; }

    /*********************************************************************************************/
    /* DESTINATION SETTER AND GETTER */
    /*********************************************************************************************/
    public String getDestination()
    {
        return this.destination;
    }
    public void setDestination(String destination) { this.destination = destination; }

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
