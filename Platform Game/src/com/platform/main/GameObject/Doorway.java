package com.platform.main.gameobject;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.platform.main.MainActivity;
import com.platform.main.gameobject.RectangularPlatform;

public class Doorway
        extends RectangularPlatform
{
    private String destination;
    private int destinationX;
    private int destinationY;

    public Doorway(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, String paramString, int paramInt1, int paramInt2, MainActivity paramMainActivity)
    {
        super(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramMainActivity);
        this.destination = paramString;
        this.destinationX = paramInt1;
        this.destinationY = paramInt2;
        this.body.setUserData(this);
        ((Fixture)this.body.getFixtureList().get(0)).setSensor(true);
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
}
