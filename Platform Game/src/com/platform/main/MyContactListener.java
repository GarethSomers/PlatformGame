package com.platform.main;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.platform.main.gameobject.ClippingPlatform;
import com.platform.main.gameobject.Doorway;
import com.platform.main.gameobject.Enemy;
import com.platform.main.gameobject.Ladder;
import com.platform.main.gameobject.Player;
import com.platform.main.gameobject.Lemon;

public class MyContactListener implements ContactListener
{
    MainActivity mActivity;
    //variables
    private Player localPlayer = null;
    private boolean playersFeet = false;
    private Ladder localLadder = null;
    private ClippingPlatform localClippingPlatform = null;
    private Enemy localEnemy = null;
    private Doorway localDoorway = null;
    private Lemon localLemon = null;

    /*
    * Constructor
    *
    */
    public MyContactListener(MainActivity paramMainActivity)
    {
        this.mActivity = paramMainActivity;
    }

    /*
    * This method is used to reset the objects used before processing a collision
    *
    * @param newState This is used to set variables with this value. For example setting jumping true or false when entering and exiting a jumping zone
    */
    public void resetObjects()
    {
        this.localPlayer = null;
        this.playersFeet = false;
        this.localLadder = null;
        this.localClippingPlatform = null;
        this.localEnemy = null;
        this.localDoorway = null;
        this.localLemon = null;
    }

    /*
    * This method is used to identify the objects
    *
    * @param arrayOfFixture This is an array of the fixtures
    */
    public void identifyObjects(Fixture[] arrayOfFixture)
    {
        for(int i = 0; i < arrayOfFixture.length; i++)
        {
            //This is the parent object. There is always a parent.
            Object parentUserData = arrayOfFixture[i].getBody().getUserData();
            //This is the child object. For to have this child there must be a parent.
            Object childUserData = arrayOfFixture[i].getUserData();

            //Do parent checks
            if(parentUserData instanceof Player)
            {
                //Its the player
                localPlayer = this.mActivity.getThePlayer();
            }
            else if(parentUserData instanceof Enemy)
            {
                localEnemy = (Enemy)parentUserData;
            }
            else if(parentUserData instanceof Ladder)
            {
                localLadder = (Ladder)parentUserData;
            }
            else if(parentUserData instanceof Doorway)
            {
                localDoorway = (Doorway)parentUserData;
                //enable can go through door?
            }
            else if(parentUserData instanceof ClippingPlatform)
            {
                localClippingPlatform = (ClippingPlatform)parentUserData;
            }
            else if(parentUserData instanceof Lemon)
            {
                this.localLemon = (Lemon)parentUserData;
            }

            //Do child checks
            if(childUserData instanceof String)
            {
                //if its a string
                String theString = (String)childUserData;
                if(theString.equals("feet"))
                {
                    //if its feet
                    playersFeet = true;
                }
            }
        }
    }

    /*
    * This method is used to process the objects
    *
    * @param newState This is used to set variables with this value. For example setting jumping true or false when entering and exiting a jumping zone
    */
    public void processObjects(boolean newState)
    {
        if( playersFeet == true)
        {
            //players feet must have hit something
            if(localEnemy != null)
            {
                //he must have hit/left an enemy
                this.mActivity.getThePlayer().forceJump();
                localEnemy.kill();
            }
            else if(localLadder != null)
            {
                //he must have hit/left a ladder
                this.mActivity.getThePlayer().setClimbing(newState);
            }
            else if(localClippingPlatform != null)
            {
                //he must have hit/left a platform
                this.mActivity.getThePlayer().setJumping(newState);
            }
            else if(localLemon != null)
            {
                localLemon.collect();
            }
        }
        else if(localPlayer != null)
        {
            //if the player hits an enemy etc.
            if(localDoorway != null)
            {
                //he must have hit/left a platform
                mActivity.getThePlayer().setInfrontOfDoorway(newState);
                mActivity.getLevelManager().setupScheduleLoadLevel(localDoorway.getDestination(), localDoorway.getDestinationX(), localDoorway.getDestinationY());
            }
        }
    }

    public void processContact(Fixture[] arrayOfFixture, boolean newState)
    {
        //reset
        this.resetObjects();
        //identify objects
        this.identifyObjects(arrayOfFixture);
        //process objects
        this.processObjects(newState);
    }
      
    public void beginContact(Contact paramContact)
    {
        this.processContact(new Fixture[]{ paramContact.getFixtureA(), paramContact.getFixtureB() }, true);
    }

    public void endContact(Contact paramContact)
    {
        this.processContact(new Fixture[]{ paramContact.getFixtureA(), paramContact.getFixtureB() }, false);
    }

    public void postSolve(Contact paramContact, ContactImpulse paramContactImpulse)
    {
        paramContact.setEnabled(true);
    }

    public void preSolve(Contact paramContact, Manifold paramManifold)
    {
        // getting the fixtures that collided
        Fixture fixtureA = paramContact.getFixtureA();
        Fixture fixtureB = paramContact.getFixtureB();
        // variable to handle bodies y position
        float playerYPosition;
        float platformYPosition;
        // checking if the collision bodies are the ones marked as "middle" and "player"
        if ((fixtureA.getBody().getUserData() instanceof ClippingPlatform && fixtureB.getBody().getUserData() instanceof Player)
          ||(fixtureA.getBody().getUserData() instanceof Player && fixtureB.getBody().getUserData() instanceof ClippingPlatform)) {
            //By default disable the event from happening
            paramContact.setEnabled(false);
            //check if the player is moving up (jumping up)
            if(this.mActivity.getThePlayer().getBody().getLinearVelocity().y < -0.1)
            {
                //if so then don't detect anything
                return;
            }
            // determining if the fixtureA represents the platform ("middle") or the player
            if (fixtureA.getBody().getUserData() instanceof ClippingPlatform) {
                // determining y positions
                playerYPosition=fixtureB.getBody().getPosition().y;
                platformYPosition=fixtureA.getBody().getPosition().y;
            }
            else
            {
                // determining y positions
                playerYPosition=fixtureA.getBody().getPosition().y;
                platformYPosition=fixtureB.getBody().getPosition().y;
            }
            // checking distance between bodies
            float distance = playerYPosition - platformYPosition;
            // if the distance is greater than player radius + half of the platform height...
            if (distance<=0){
                // don't manage the contact
                paramContact.setEnabled(true);
            }
        }
    }
}
