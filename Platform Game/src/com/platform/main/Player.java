package com.platform.main;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import java.util.ArrayList;
import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.input.touch.TouchEvent;

public class Player
        extends Person
{
    private long[] PLAYER_KILLING;
    private int currentJumpID = -1;

    public Player(float paramFloat1, float paramFloat2, int paramInt, MainActivity paramMainActivity)
    {
        this.mActivity = paramMainActivity;
        this.JUMP_HEIGHT = 6;
        this.CLIMBING_JUMP_HEIGHT = 5;
        this.PLAYER_HEIGHT = 23;
        this.PLAYER_WIDTH = 17;
        this.PLAYER_ACCELERATION = 0.3F;
        this.MAX_SPEED = 3.0F;
        this.height = this.PLAYER_HEIGHT;
        this.width = this.PLAYER_WIDTH;
        this.acceleration = this.PLAYER_ACCELERATION;
        this.maxSpeed = this.MAX_SPEED;
        this.mTiledTextureRegion = this.mActivity.getMaterialManager().getTiledTexture("player.png", 68, 120, 4, 5);
        this.animatedSprite = new AnimatedSprite(paramFloat1, paramFloat2, this.mTiledTextureRegion, this.mActivity.getEngine().getVertexBufferObjectManager());
        this.animatedSprite.animate(this.PERSON_STANDING, this.PERSON_STANDING_S, this.PERSON_STANDING_E, true);
        this.currentAnimation = this.PERSON_STANDING;
        this.fixtureDef = PhysicsFactory.createFixtureDef(5.0F, 0.0F, 0.0F);
        createBody();
        ((Fixture)this.body.getFixtureList().get(1)).setUserData("playerFeet");
        this.body.setUserData(this);
        displayPlayer();
    }

    public void allowJumping()
    {
        this.jumpingAllowed = true;
        this.jumping = false;
    }

    public void displayPlayer()
    {
        try
        {
            this.mActivity.getLevelManager().getScene().attachChild(this.animatedSprite);
            return;
        }
        catch (Exception localException)
        {
            this.mActivity.log("Attempting to attach child to scene (Most likely already attached.");
        }
    }

    public void forceJump()
    {
        this.jumping = true;
        if (this.currentAnimation != this.PERSON_CLIMBING) {
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
        if (this.currentAnimation != this.PERSON_CLIMBING) {
            this.jumpingAllowed = false;
        }
    }

    public void moveDetect(float paramFloat1, float paramFloat2, TouchEvent paramTouchEvent)
    {
        int i = paramTouchEvent.getAction();
        if (!getAlive()) {}
        do
        {
            if ((paramFloat1 < this.mActivity.getCameraWidth() / 3) && ((i == 0) || (i == 2)) && (this.currentJumpID != paramTouchEvent.getPointerID()))
            {
                moveLeft();
                return;
            }
            if ((paramFloat1 > 2 * this.mActivity.getCameraWidth() / 3) && ((i == 0) || (i == 2)) && (this.currentJumpID != paramTouchEvent.getPointerID()))
            {
                moveRight();
                return;
            }
            if ((paramFloat1 > this.mActivity.getCameraWidth() / 3) && (paramFloat1 < 2 * this.mActivity.getCameraWidth() / 3) && (i == 0) && (this.jumpingAllowed))
            {
                jump(paramTouchEvent.getPointerID());
                return;
            }
            if ((i == 1) && (this.currentJumpID != paramTouchEvent.getPointerID()))
            {
                moveStop();
                return;
            }
        } while ((this.currentJumpID != paramTouchEvent.getPointerID()) || (i != 1));
        this.currentJumpID = -1;
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
        this.body.setLinearVelocity(this.velocity_x, this.body.getLinearVelocity().y);
    }
}
