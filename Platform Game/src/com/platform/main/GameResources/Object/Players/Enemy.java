package com.platform.main.GameResources.Object.Players;

import com.platform.main.GameManager;

public class Enemy extends MovableSprite
{
    protected float range = 200.0F;

    //create generic object
    public Enemy(GameManager mainActivity)
    {
        super(mainActivity);
        this.setImage("Enemy.png");
        this.setWidth(64);
        this.setHeight(120);
        this.columns = 4;
        this.rows = 5;
    }

    public void kill()
    {
        this.getShape().animate(this.PERSON_DYING, this.PERSON_DYING_S, this.PERSON_DYING_E, false);
        this.getShape().clearUpdateHandlers();
        this.alive = false;
    }

    public void setActive(boolean paramBoolean)
    {
        this.body.setActive(false);
    }

    public void updateAI()
    {
        if ((this.getBody().getPosition().dst(this.gameManager.getThePlayer().getBody().getPosition())*this.gameManager.getMainActivity().PIXEL_TO_METRE_RATIO) < (this.range) && (this.alive))
        {
            if (getCenterXPos() < this.gameManager.getThePlayer().getCenterXPos()) {
                moveRight();
            }
            else if (getCenterXPos() > this.gameManager.getThePlayer().getCenterXPos()) {
                moveLeft();
            }
        }
        else
        {
            moveStop();
        }
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

    @Override
    public void afterCreateObject()
    {
        //add feet
        this.addFeet();
        this.setAnimation("standing");
        this.getBody().setAwake(false);
        this.getBody().setUserData(this);
        this.getBody().setFixedRotation(true);
        this.getBody().setLinearVelocity(0.0F, 0.0F);
    }
}
