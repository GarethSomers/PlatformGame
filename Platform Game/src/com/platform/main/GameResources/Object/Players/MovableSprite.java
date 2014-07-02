package com.platform.main.GameResources.Object.Players;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.platform.main.GameManager;
import com.platform.main.GameResources.Object.AnimatedGameObject;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;

public abstract class MovableSprite extends AnimatedGameObject
{
    protected int ORIGINAL_JUMP_HEIGHT = 6;
    protected int CLIMB_JUMP_HEIGHT = 3;
    protected int JUMP_HEIGHT = ORIGINAL_JUMP_HEIGHT;
    protected float MAX_SPEED = 2.5F;
    protected float ACCELERATION = 0.5F;

    //animation
    protected long[] PERSON_CLIMBING = { 50L, 100L, 150L, 200L };
    protected int PERSON_CLIMBING_E = 3;
    protected int PERSON_CLIMBING_S = 0;
    protected long[] PERSON_DYING = { 100L, 200L, 300L, 400L };
    protected int PERSON_DYING_E = 11;
    protected int PERSON_DYING_S = 8;
    protected long[] PERSON_JUMPING = { 100L, 200L, 250L, 300L };
    protected int PERSON_JUMPING_E = 19;
    protected int PERSON_JUMPING_S = 16;
    protected long[] PERSON_KILLING = { 100L, 200L, 300L, 400L };
    protected int PERSON_KILLING_E = 15;
    protected int PERSON_KILLING_S = 12;
    protected long[] PERSON_STANDING = { 100L, 200L, 300L, 400L };
    protected int PERSON_STANDING_E = 3;
    protected int PERSON_STANDING_S = 0;
    protected long[] PERSON_WALKING = { 100L, 200L, 300L, 400L };
    protected int PERSON_WALKING_E = 7;
    protected int PERSON_WALKING_S = 4;
    protected float PLAYER_ACCELERATION;
    protected int PLAYER_HEIGHT;
    protected int PLAYER_WIDTH;
    protected int currentAnimation;

    //additional object
    protected Fixture feet;

    //movement
    protected boolean jumping = false;
    protected boolean jumpingAllowed = true;
    protected boolean moveLeft = false;
    protected boolean moveRight = false;
    protected float velocity_x = 0.0F;
    protected boolean alive = true;
    protected int health = 50;


    /*********************************************************************************************/
    /* CONSTRUCTOR */
    /*********************************************************************************************/
    public MovableSprite(GameManager mainActivity)
    {
        super(mainActivity);

        //setup animation

        this.getBody().getFixtureList().get(0).setFriction(0.1f);
    }


    /*********************************************************************************************/
    /* SPEED  */
    /*********************************************************************************************/
    public String getSpeed()
    {
        return String.valueOf(this.velocity_x);
    }
    public void setSpeed(float velocity_x)
    {
        this.velocity_x = velocity_x;
    }

    /*********************************************************************************************/
    /* HANDLE X MOVEMENT */
    /*********************************************************************************************/
    public void handleXMovement()
    {
        if (this.moveLeft)
        {
            //move left
            this.velocity_x -= this.ACCELERATION;
            this.getShape().setFlippedHorizontal(true);

            //if velocity is now < 0 show him walking
            if (this.velocity_x < 0.0F) {
                setAnimation("walking");
            }
            //ensure he doesn't go past max speed
            if (this.velocity_x < (-this.MAX_SPEED)) {
                this.velocity_x = (-this.MAX_SPEED);
            }
        }
        else if (this.moveRight)
        {
            //move right
            this.velocity_x += this.ACCELERATION;
            this.getShape().setFlippedHorizontal(false);
            //if velocity is greater then 0 then show him walking
            if (this.velocity_x > 0.0F) {
                setAnimation("walking");
            }
            //max sure he doesn't go past max speed
            if (this.velocity_x > this.MAX_SPEED) {
                this.velocity_x = this.MAX_SPEED;
            }
        }
        else  if (this.velocity_x > this.ACCELERATION)
        {
            //if hes still moving right but not pressing the button slow him down
            this.velocity_x -= this.ACCELERATION;
        }
        else if (this.velocity_x < -this.ACCELERATION)
        {
            //if hes still moving left but not pressing the button slow him down
            this.velocity_x += this.ACCELERATION;
        }
        else
        {
            //otherwise stop him
            this.velocity_x = 0.0F;
            setAnimation("standing");
        }
    }

    /*********************************************************************************************/
    /* HANDLE Y MOVEMENT */
    /*********************************************************************************************/
    public void handleYMovement()
    {
        if (this.jumping)
        {
            this.body.setLinearVelocity(this.body.getLinearVelocity().x, -this.JUMP_HEIGHT);
            setAnimation("jumping");
            this.jumping = false;
        }
    }




    /*********************************************************************************************/
    /* MOVEMENT SETTERS */
    /*********************************************************************************************/
    public void moveFullStop()
    {
        this.getBody().setLinearVelocity(0,0);
        this.velocity_x = 0;
        this.moveStop();
    }

    public void moveLeft()
    {
        this.moveLeft = true;
        this.moveRight = false;
    }

    public void moveRight()
    {
        this.moveLeft = false;
        this.moveRight = true;
    }

    public void moveStop()
    {
        this.moveLeft = false;
        this.moveRight = false;
    }









    /*********************************************************************************************/
    /* ANIMATION */
    /*********************************************************************************************/
    public void setAnimation(String paramString)
    {
        if (paramString.equals("climbing") && !(this.currentAnimation == this.PERSON_CLIMBING_S))
        {
            this.getShape().animate(this.PERSON_CLIMBING, this.PERSON_CLIMBING_S, this.PERSON_CLIMBING_E, true);
            this.currentAnimation = this.PERSON_CLIMBING_S;
        }
        else if (paramString.equals("jumping") && !(this.currentAnimation == this.PERSON_JUMPING_S ))
        {
            this.getShape().animate(this.PERSON_JUMPING, this.PERSON_JUMPING_S, this.PERSON_JUMPING_E, false);
            this.currentAnimation = this.PERSON_JUMPING_S;
        }
        else if ( ( paramString.equals("walking") || paramString.equals("endclimbing")) && !(this.currentAnimation == this.PERSON_WALKING_S))
        {
            this.getShape().animate(this.PERSON_WALKING, this.PERSON_WALKING_S, this.PERSON_WALKING_E, true);
            this.currentAnimation = this.PERSON_WALKING_S;
        }
        else if(paramString.equals("standing"))
        {
            this.getShape().animate(this.PERSON_STANDING, this.PERSON_STANDING_S, this.PERSON_STANDING_E, true);
            this.currentAnimation = this.PERSON_STANDING_S;
        }
    }





    /*********************************************************************************************/
    /* KILL / ALIVE / HEALTH */
    /*********************************************************************************************/

    //ALIVE
    public boolean getAlive()
    {
        return this.alive;
    }
    public void setAlive(boolean paramBoolean)
    {
        this.alive = paramBoolean;
    }

    //HEALTH
    public void addHealth(int newHealth) { this.health += newHealth; }
    public int getHealth()
    {



        return this.health;
    }

    //KILL
    public void kill() { this.alive = false; }


    /*********************************************************************************************/
    /* ADD FEET */
    /*********************************************************************************************/
    public void addFeet()
    {
        //create new polygon shape
        PolygonShape localPolygonShape = new PolygonShape();
        localPolygonShape.setAsBox(0.1F, 0.1F, new Vector2(0.0F, 0.4F), 0.0F);
        FixtureDef localFixtureDef = PhysicsFactory.createFixtureDef(1.0F, 0.0F, 0.0F, true);
        localFixtureDef.isSensor = true;
        localFixtureDef.shape = localPolygonShape;
        //localPolygonShape.dispose();
        //create feet
        this.feet = this.getBody().createFixture(localFixtureDef);
        //set userdata
        ((Fixture)this.body.getFixtureList().get(1)).setUserData("feet");
    }




    /*********************************************************************************************/
    /* JUMPING */
    /*********************************************************************************************/
    public void setJumping(boolean newState)
    {
        if(newState == false)
        {
            this.disableJumping();
        }
        else
        {
            this.enableJumping();
        }
    }

    public void enableJumping()
    {
        this.jumpingAllowed = true;
        this.jumping = false;
    }

    public void disableJumping()
    {
        this.jumpingAllowed = false;
        this.jumping = false;
    }



    /*********************************************************************************************/
    /* CREATE OBJECT CALLS */
    /*********************************************************************************************/
    @Override
    public void preCreateObject() {
        this.mTiledTextureRegion = this.gameManager.getMaterialManager().getTiledTexture(this.image, this.width*this.columns, this.height*this.rows, this.columns, this.rows);
        this.bodyType = BodyDef.BodyType.StaticBody;
    }

    @Override
    public void afterCreateObject() {
        this.setAnimation("standing");
        this.getBody().setAwake(false);
        this.getBody().setUserData(this);
        this.getBody().setFixedRotation(true);
        this.getBody().setLinearVelocity(0.0F, 0.0F);
    }
}
