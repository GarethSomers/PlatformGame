package com.platform.main.gameobject;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.platform.main.MainActivity;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;

public class Enemy extends MovableSprite
{
    private Fixture mFeet;
    private float range;

    //create generic object
    public Enemy(float xPos, float yPos, String paramString, MainActivity mainActivity)
    {
        this(xPos, yPos, 64, 120, "Enemy.png", 4, 5, mainActivity);
    }

    //create enemy
    public Enemy(float xPos,float yPos,int width, int height, String image, int columns, int rows, MainActivity mainActivity)
    {
        //call movable sprite constructor
        super(xPos, yPos, width, height, image, columns, rows, mainActivity);
        //range
        this.range = 60.0F;
        //add feet
        this.addFeet();
        //set userdata
        this.body.setUserData(this);
    }

    public void displayBox()
    {
        this.mActivity.getScene().attachChild(this.getShape());
    }

    public boolean isPlayerAboveEnemy(float paramFloat1, float paramFloat2, float paramFloat3)
    {
        boolean bool1 = paramFloat1 < getEndPos();
        boolean bool2 = false;
        if (bool1)
        {
            boolean bool3 = paramFloat2 < getY();
            bool2 = false;
            if (bool3) {
                bool2 = true;
            }
        }
        return bool2;
    }

    public void kill()
    {
        this.getShape().animate(this.PERSON_DYING, this.PERSON_DYING_S, this.PERSON_DYING_E, false);
        this.getShape().clearUpdateHandlers();
        this.mActivity.getLevelManager();
        this.alive = false;
    }

    public void setActive(boolean paramBoolean)
    {
        this.body.setActive(false);
    }

    public void updateAI()
    {
        if ((Math.abs(getCenterXPos() - this.mActivity.getThePlayer().getCenterXPos()) < this.range) && (Math.abs(getCenterYPos() - this.mActivity.getThePlayer().getCenterYPos()) < this.range) && (this.alive))
        {
            if (getCenterXPos() < this.mActivity.getThePlayer().getCenterXPos()) {
                moveRight();
            }
            while (getCenterXPos() <= this.mActivity.getThePlayer().getCenterXPos()) {
                return;
            }
            moveLeft();
            return;
        }
        moveStop();
    }

    public void updatePosition()
    {
        if (!getAlive()) {
            return;
        }
        updateAI();
        handleYMovement();
        handleXMovement();
        this.body.setLinearVelocity(this.velocity_x, this.body.getLinearVelocity().y);
    }
}
