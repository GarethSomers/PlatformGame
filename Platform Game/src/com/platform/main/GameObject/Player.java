package com.platform.main.gameobject;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.platform.main.MainActivity;
import com.platform.main.gameobject.Person;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.input.touch.TouchEvent;

public class Player
        extends Person
{
    private long[] PLAYER_KILLING;
    private int currentJumpID = -1;
    private int currentLeftID = -1;
    private int currentRightID = -1;
    private int climbing;
    private boolean infrontOfDoorway;

    public Player(float paramFloat1, float paramFloat2, int paramInt, MainActivity paramMainActivity)
    {
        this.mActivity = paramMainActivity;
        //set defaults
        this.JUMP_HEIGHT = 6;
        this.PLAYER_HEIGHT = 24;
        this.PLAYER_WIDTH = 17;
        this.PLAYER_ACCELERATION = 0.8F;
        this.MAX_SPEED = 3.5F;
        //setup object
        this.mTiledTextureRegion = this.mActivity.getMaterialManager().getTiledTexture("player.png", 68, 120, 4, 5);
        this.theShape = new AnimatedSprite(paramFloat1, paramFloat2, this.mTiledTextureRegion, this.mActivity.getEngine().getVertexBufferObjectManager());
        this.getShape().animate(this.PERSON_STANDING, this.PERSON_STANDING_S, this.PERSON_STANDING_E, true);
        this.currentAnimation = this.PERSON_STANDING_S;
        this.fixtureDef = PhysicsFactory.createFixtureDef(5.0F, 0.0F, 0.0F);
        createBody();
        ((Fixture)this.body.getFixtureList().get(1)).setUserData("playerFeet");
        this.body.setUserData(this);
        displayPlayer();
    }

    public void enableJumping()
    {
        this.jumpingAllowed = true;
        this.jumping = false;
    }

    public void disableJumping()
    {
        if(this.climbing == 0)
        {
            this.jumpingAllowed = false;
        }
        this.jumping = false;
    }

    public void disableClimbing()
    {
        //add to the climbing counter
        this.climbing -= 1;
        if(this.climbing < 1)
        {
            //exited all climbing areas. disable climbing
            this.JUMP_HEIGHT = this.ORIGINAL_JUMP_HEIGHT;
            //reset gravity
            this.body.setGravityScale(1f);
            //show animation
            setAnimation("endclimbing");
            //if player is still jumping
            if(this.getBody().getLinearVelocity().y > 1 || this.getBody().getLinearVelocity().y < -1)
            {
                this.disableJumping();
            }
            else
            {
                this.enableJumping();
            }
        }
    }

    public void enableClimbing()
    {
        this.climbing += 1;
        if(this.climbing > 0)
        {
            //still climbing
            this.JUMP_HEIGHT = this.CLIMB_JUMP_HEIGHT;
            this.body.setGravityScale(0f);
            setAnimation("climbing");
            this.enableJumping();
        }
    }

    public void displayPlayer()
    {
        addToPhysicsWorld();
        this.mActivity.getLevelManager().getScene().attachChild(this.getShape());
    }

    public void forceJump()
    {
        this.jumping = true;
        if (this.currentAnimation != this.PERSON_CLIMBING_S) {
            this.jumpingAllowed = false;
        }
    }

    public Body getBody()
    {
        return this.body;
    }

    public void jump(int paramInt)
    {
        this.jumping = true;
        this.currentJumpID = paramInt;
        if (this.currentAnimation != this.PERSON_CLIMBING_S) {
            this.jumpingAllowed = false;
        }
    }

    public void moveDetect(float paramFloat1, float paramFloat2, TouchEvent paramTouchEvent)
    {
        if (getAlive())
        {
            //this.mActivity.log("Trying to move");
            if((this.currentJumpID != paramTouchEvent.getPointerID()) & (paramTouchEvent.getAction() == TouchEvent.ACTION_DOWN))
            {
                //if he did not let go of jumping and the action is not up
                if ((paramFloat1 < this.mActivity.getCameraWidth() / 3) && (this.currentJumpID != paramTouchEvent.getPointerID()) && this.currentLeftID == -1)
                {
                    //this.mActivity.log("LEFT");
                    this.currentLeftID = paramTouchEvent.getPointerID();
                    this.currentRightID = -1;
                    moveLeft();
                }
                if ((paramFloat1 > 2 * this.mActivity.getCameraWidth() / 3) && (this.currentJumpID != paramTouchEvent.getPointerID()) && this.currentRightID == -1)
                {
                    //this.mActivity.log("RIGHT");
                    this.currentRightID = paramTouchEvent.getPointerID();
                    this.currentLeftID = -1;
                    moveRight();
                }
                if ((paramFloat1 > this.mActivity.getCameraWidth() / 3) && (paramFloat1 < 2 * this.mActivity.getCameraWidth() / 3) && (this.jumpingAllowed) && this.currentJumpID == -1)
                {
                    if(!this.infrontOfDoorway)
                    {
                        jump(paramTouchEvent.getPointerID());
                    }
                    else
                    {
                        this.mActivity.getLevelManager().confirmScheduleLoadLevel();
                    }
                }
            }
            else if ((paramTouchEvent.getAction() == TouchEvent.ACTION_UP))
            {
                //if the player let go and he wasn't using that to jump
                if(this.currentLeftID == paramTouchEvent.getPointerID())
                {
                    this.currentLeftID = -1;
                    moveStop();
                }
                else if(this.currentRightID == paramTouchEvent.getPointerID())
                {
                    this.currentRightID = -1;
                    moveStop();
                }
                else if (this.currentJumpID == paramTouchEvent.getPointerID())
                {
                    //otherwise it must have been the jump so reset the jump id
                    this.currentJumpID = -1;
                }
            }
        }
    }

    public void reload(int paramInt1, int paramInt2)
    {
        addToPhysicsWorld();
        setPos(paramInt1, paramInt2);
        displayPlayer();
    }

    public void updatePosition()
    {
        if (!getAlive()) {
            return;
        }
        handleYMovement();
        handleXMovement();
        //this.mActivity.log(this.velocity_x+" : "+this.body.getLinearVelocity().y);
        this.body.setLinearVelocity(this.velocity_x, this.body.getLinearVelocity().y);
    }

    public void setClimbing(boolean newState)
    {
        if(newState == false)
        {
            this.disableClimbing();
        }
        else
        {
            this.enableClimbing();
        }
    }

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

    public void setInfrontOfDoorway(boolean newState)
    {
        this.infrontOfDoorway = newState;
    }

    public AnimatedSprite getShape()
    {
        return (AnimatedSprite)this.theShape;
    }
}
