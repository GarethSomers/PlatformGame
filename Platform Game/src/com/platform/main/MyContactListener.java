package com.platform.main;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.platform.main.GameResources.Level.GameLevel;
import com.platform.main.GameResources.LevelObjects.AnimatedObjects.MoveableObjects.Frog;
import com.platform.main.GameResources.LevelObjects.AnimatedObjects.MoveableObjects.MovableSprite;
import com.platform.main.GameResources.LevelObjects.Interactions.Collectable;
import com.platform.main.GameResources.LevelObjects.Platforms.ClippingPlatform;
import com.platform.main.GameResources.LevelObjects.Interactions.Doorway;
import com.platform.main.GameResources.LevelObjects.AnimatedObjects.MoveableObjects.Enemy;
import com.platform.main.GameResources.LevelObjects.Interactions.Ladder;
import com.platform.main.GameResources.LevelObjects.AnimatedObjects.MoveableObjects.Player;
import com.platform.main.GameResources.LevelObjects.Interactions.Lemon;
import com.platform.main.GameResources.LevelObjects.Platforms.SolidClippingPlatform;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;

public class MyContactListener implements ContactListener
{
    GameManager gameManager;
    //variables
    private Player localPlayer = null;
    private boolean playersFeet = false;
    private boolean playersHead = false;
    private boolean localFeet = false;
    private Ladder localLadder = null;
    private ClippingPlatform localClippingPlatform = null;
    private Enemy localEnemy = null;
    private Doorway localDoorway = null;
    private Collectable localCollectable = null;
    private boolean twoEnemies = false;
    private SolidClippingPlatform localSolidClippingPlatform;
    private boolean localEnemyFeet = false;
    private boolean localEnemyHead = false;

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
        this.localSolidClippingPlatform = null;
        this.localEnemy = null;
        this.localDoorway = null;
        this.localCollectable = null;
        this.twoEnemies = false;
        this.localEnemyFeet = false;
        this.localEnemyHead = false;
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

            /*********************************************************************************************/
            /* CHECK THE PARENT OBJECT */
            /*********************************************************************************************/
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
            else if(parentUserData instanceof SolidClippingPlatform)
            {
                localSolidClippingPlatform = (SolidClippingPlatform)parentUserData;
            }
            else if(parentUserData instanceof Collectable)
            {
                this.localCollectable = (Lemon)parentUserData;
            }

            /*********************************************************************************************/
            /* CHILD OBJECTS */
            /*********************************************************************************************/
            if(childUserData instanceof String)
            {
                //if its a string
                String theString = (String)childUserData;
                if(theString.equals("playersFeet"))
                {
                   this.playersFeet = true;
                }
                else if(theString.equals("playersHead"))
                {
                    this.playersHead = true;
                }
                else if(theString.equals("enemyFeet"))
                {
                    this.localEnemyFeet = true;
                }
                else if(theString.equals("enemyHead"))
                {
                    this.localEnemyHead = true;
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
            /*********************************************************************************************/
            /* IF PLAYERS FEET ARE DECLARED */
            /*********************************************************************************************/

            //players feet must have hit something
            if(localEnemyHead == true && localEnemy != null && localEnemy.getAlive())
            {
                //he must have hit/left an enemy
                this.gameManager.getThePlayer().forceJump();
                //localEnemy.kill();
                //((GameLevel)this.gameManager.getLevelManager().getLevel()).getEnemiesNeedRemoving().add(localEnemy);
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
            else if(localSolidClippingPlatform != null)
            {
                //he must have hit/left a platform
                this.gameManager.getThePlayer().setJumping(newState);
            }
            else if(localCollectable != null)
            {
                localCollectable.collect();
            }
        }
        else if(localEnemyHead == true)
        {
            if(playersHead == true && localPlayer != null)
            {
                //this.gameManager.gameOver();
            }
        }
        else if(localEnemyFeet == true )
        {
            if(localClippingPlatform != null || localSolidClippingPlatform != null)
            {
                this.localEnemy.setJumping(newState);
            }
            if(localEnemy instanceof Frog && newState == true)
            {
                ((Frog)localEnemy).landed();
            }
        }
        else if(localPlayer != null)
        {
            //if the player hits an enemy etc.
            if(localDoorway != null)
            {
                //he must have hit/left a platform
                //gameManager.getThePlayer().setInfrontOfDoorway(newState);
                gameManager.getLevelManager().getHUD().setDoorButtonVisibility(newState);
                gameManager.getLevelManager().setupScheduleLoadLevel(localDoorway.getDestination(), localDoorway.getDestinationX(), localDoorway.getDestinationY());
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

        /*********************************************************************************************/
        /* CLOUD CHECKS */
        /*********************************************************************************************/
        //Check for the player (ensuring that not enemies were involved)
        if((this.playersFeet == true || this.localPlayer != null) && this.localEnemy == null && this.localEnemyFeet == false)
        {
            paramContact.setEnabled(checkCollison(this.localPlayer,this.localClippingPlatform));
        }
        else if(this.localEnemyFeet == true || this.localEnemy != null)
        {
            paramContact.setEnabled(checkCollison(this.localEnemy,this.localClippingPlatform));
        }
    }
    public boolean checkCollison(MovableSprite aPerson, ClippingPlatform aClippingPlatform)
    {
        //process player presolve
        if (aPerson != null)
        {
            // checking if the collision bodies are the ones marked as "middle" and "player"
            if(aClippingPlatform != null)
            {
                // variable to handle bodies y position
                float playerYPosition;
                float platformYPosition;

                //if is player check hes not dropping
                if(aPerson instanceof Player)
                {
                    if(((Player)aPerson).getDropping() == true && aPerson.getBody().getLinearVelocity().y > 0f)
                    {
                        return false;
                    }
                }

                //get positions
                playerYPosition=aPerson.getBody().getPosition().y*PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
                platformYPosition=aClippingPlatform.getBody().getPosition().y*PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;

                // checking distance between bodies
                float distance = platformYPosition - playerYPosition;

                float halfPlatformHeight = aClippingPlatform.getShape().getHeight()/2;
                float playerRadius = aPerson.getShape().getHeight()/2;
                // if the distance is greater than player radius + half of the platform height...

                if (distance < playerRadius+halfPlatformHeight){
                    return false;
                }
            }
        }
        return true;
    }
}

