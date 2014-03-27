package com.platform.main;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.WorldManifold;

public class MyContactListener
        implements ContactListener
{
    MainActivity mActivity;

    public MyContactListener(MainActivity paramMainActivity)
    {
        this.mActivity = paramMainActivity;
    }

    public void beginContact(Contact paramContact)
    {
        Fixture[] arrayOfFixture = { paramContact.getFixtureA(), paramContact.getFixtureB() };
        Player localPlayer = null;
        Ladder localLadder = null;
        ClippingPlatform localClippingPlatform = null;
        Enemy localEnemy = null;
        Object localObject1 = null;
        Doorway localDoorway = null;

        int i = arrayOfFixture.length;
        int j = 0;
        if (j >= i)
        {
            if (localPlayer != null)
            {
                getThePlayer().enableClimbing();
            }
            if (localObject1 != null)
            {
                getThePlayer().forceJump();
                localEnemy.kill();
            }
        }

        while (localClippingPlatform == null)
        {
            Fixture localFixture = arrayOfFixture[j];
            Object localObject2 = localFixture.getBody().getUserData();
            Object localObject3 = localFixture.getUserData();
            if ((localObject2 instanceof ClippingPlatform)) {
                localClippingPlatform = (ClippingPlatform)localObject2;
            }

            if ((localObject3 instanceof String))
            {
                String str = (String)localObject3;
                if ((str != null) && (str.equals("playerFeet"))) {
                    localObject1 = localFixture;
                }
            }
            j++;

            if ((localObject2 instanceof Ladder)) {
                //ladder
                getThePlayer().allowJumping();
                localLadder = (Ladder)localObject2;
            } else if ((localObject2 instanceof Player)) {
                //player
                localPlayer = (Player)localObject2;
            } else if ((localObject2 instanceof Enemy)) {
                //enemy
                localEnemy = (Enemy)localObject2;
            } else if ((localObject2 instanceof Doorway)) {
                //doorway
                localDoorway = (Doorway)localObject2;
                this.mActivity.log("Going Through Doorway");
                this.mActivity.getLevelManager().scheduleLoadLevel(localDoorway.getDestination(), localDoorway.getDestinationX(), localDoorway.getDestinationY());
            }
        }

    }

    public void endContact(Contact paramContact)
    {
        int i = 0;
        Fixture[] arrayOfFixture = { paramContact.getFixtureA(), paramContact.getFixtureB() };
        Player localPlayer = null;
        Ladder localLadder = null;
        int j = arrayOfFixture.length;
        if (i >= j)
        {
            if ((localPlayer != null) && (localLadder != null)) {
                getThePlayer().disableClimbing();
            }
            return;
        }
        Fixture localFixture = arrayOfFixture[i];
        Object localObject1 = localFixture.getBody().getUserData();
        Object localObject2 = localFixture.getUserData();
        if ((localObject1 instanceof Ladder)) {
            localLadder = (Ladder)localObject1;
        }

        if ((localObject2 instanceof Player)) {
            localPlayer = (Player)localObject2;
        }
        i++;
        if ((localObject1 instanceof Player)) {
            localPlayer = (Player)localObject1;
        }
    }

    public Player getThePlayer()
    {
        return this.mActivity.getThePlayer();
    }

    public void postSolve(Contact paramContact, ContactImpulse paramContactImpulse)
    {
        paramContact.setEnabled(true);
    }

    public void preSolve(Contact paramContact, Manifold paramManifold)
    {
        /*Fixture localFixture1 = paramContact.getFixtureA();
        Fixture localFixture2 = paramContact.getFixtureB();
        Fixture localFixture3;
        Fixture localFixture4;
        Body localBody1;
        Body localBody2;
        WorldManifold localWorldManifold;
        int i = 0;
        if ((localFixture1.getBody().getUserData() != null) && ((localFixture1.getBody().getUserData() instanceof ClippingPlatform)))
        {
            localFixture3 = localFixture1;
            localFixture4 = localFixture2;
            localBody1 = localFixture3.getBody();
            localBody2 = localFixture4.getBody();
            localWorldManifold = paramContact.getWorldManifold();
            i = localWorldManifold.getPoints().length;
        }
        for (int j = 0;; j++)
        {
            if (j >= i) {
                paramContact.setEnabled(false);
            }
            Vector2 localVector2;
            do
            {
                do
                {
                    return;
                } while ((localFixture2.getBody().getUserData() == null) || (!(localFixture2.getBody().getUserData() instanceof ClippingPlatform)));
                localFixture3 = localFixture2;
                localFixture4 = localFixture1;
                break;
                localVector2 = localBody1.getLocalVector(localBody1.getLinearVelocityFromWorldPoint(localWorldManifold.getPoints()[j]).sub(localBody2.getLinearVelocityFromWorldPoint(localWorldManifold.getPoints()[j])));
            } while ((localVector2.y < -2.0F) || ((localVector2.y < 2.0F) && (localBody1.getLocalPoint(localWorldManifold.getPoints()[j]).y < 1.0F)));
        }*/
    }
}
