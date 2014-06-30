package com.platform.main;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.platform.main.GameResources.Object.Platforms.ClippingPlatform;
import com.platform.main.GameResources.Object.Players.Doorway;
import com.platform.main.GameResources.Object.Players.Enemy;
import com.platform.main.GameResources.Object.Interactions.Ladder;
import com.platform.main.GameResources.Object.Players.Player;
import com.platform.main.GameResources.Object.Interactions.Lemon;

public class MyContactListener implements ContactListener
{
    GameManager gameManager;
    //variables
    private Player localPlayer = null;
    private boolean playersFeet = false;
    private boolean localFeet = false;
    private Ladder localLadder = null;
    private ClippingPlatform localClippingPlatform = null;
    private Enemy localEnemy = null;
    private Doorway localDoorway = null;
    private Lemon localLemon = null;
    private boolean twoEnemies = false;

    /*
    * Constructor
    *
    */
    public MyContactListener(GameManager paramMainActivity)
    {
        this.gameManager = paramMainActivity;
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
        this.localFeet = false;
        this.localLadder = null;
        this.localClippingPlatform = null;
        this.localEnemy = null;
        this.localDoorway = null;
        this.localLemon = null;
        this.twoEnemies = false;
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
                localPlayer = this.gameManager.getThePlayer();
            }
            else if(parentUserData instanceof Enemy)
            {
                if(localEnemy != null)
                {
                    this.twoEnemies = true;
                }
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
                if(theString.equals("playersFeet"))
                {
                    //if its feet
                   this.playersFeet = true;
                }
                else if(theString.equals("feet"))
                {
                    this.localFeet = true;
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
            if(localEnemy != null && localEnemy.getAlive())
            {
                //he must have hit/left an enemy
                //this.gameManager.getThePlayer().forceJump();
                localEnemy.kill();
            }
            else if(localLadder != null)
            {
                //he must have hit/left a ladder
                this.gameManager.getThePlayer().setClimbing(newState);
                localLadder.triggerAction();
            }
            else if(localClippingPlatform != null)
            {
                //he must have hit/left a platform
                this.gameManager.getThePlayer().setJumping(newState);
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
                gameManager.getThePlayer().setInfrontOfDoorway(newState);
                gameManager.getLevelManager().setupScheduleLoadLevel(localDoorway.getDestination(), localDoorway.getDestinationX(), localDoorway.getDestinationY());
            }
        }
        else if(localEnemy != null)
        {
            if(localFeet != false && localClippingPlatform != null)
            {
                this.localEnemy.setJumping(newState);
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
        //reset
        this.resetObjects();
        //identify objects
        this.identifyObjects(new Fixture[]{ paramContact.getFixtureA(), paramContact.getFixtureB()});
        //By default disable the event from happening
        paramContact.setEnabled(false);

        //process player presolve
        if (this.localPlayer != null || this.playersFeet == true)
        {
            // checking if the collision bodies are the ones marked as "middle" and "player"
            if(this.localClippingPlatform != null)
            {
                // variable to handle bodies y position
                float playerYPosition;
                float platformYPosition;
                //check if the player is moving up (jumping up)
                if(this.localPlayer.getBody().getLinearVelocity().y < -0.1)
                {
                    //if so then don't detect anything
                    return;
                }

                //get positions
                playerYPosition=this.localPlayer.getBody().getPosition().y;
                platformYPosition=this.localClippingPlatform.getBody().getPosition().y;

                // checking distance between bodies
                float distance = playerYPosition - platformYPosition;
                // if the distance is greater than player radius + half of the platform height...
                if (distance>0){
                    return;
                }
            }
        }

        if((this.localEnemy != null || this.localFeet == true)&&(this.localPlayer != null || this.playersFeet == true || this.twoEnemies == true ))
        {
            //if enemy is alive
            if(this.localEnemy.getAlive() == false)
            {
                //disable the contact
                return;
            }
        }
        //By default disable the event from happening
        paramContact.setEnabled(true);
    }
}
