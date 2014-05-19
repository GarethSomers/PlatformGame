package com.platform.main.GameResources.Object.Players;

import com.platform.main.MainActivity;

public class Frog extends Enemy
{
    //variables
    protected boolean alive = true;
    //animations

    //constructor
    public Frog(float paramFloat1, float paramFloat2, MainActivity mMainActivity)
    {
        super(paramFloat1, paramFloat2, 71, 23, "frog.png",2,4, mMainActivity);
        this.body.setUserData(this);

        this.PERSON_CLIMBING = new long[]{ 50L, 100L, 150L, 200L };
        this.PERSON_CLIMBING_S = 0;
        this.PERSON_CLIMBING_E = 3;
        this.PERSON_DYING = new long[]{ 100L, 200L, 300L, 400L };
        this.PERSON_DYING_S = 4;
        this.PERSON_DYING_E = 7;
        this.PERSON_JUMPING = new long[]{ 100L, 200L, 250L, 300L };
        this.PERSON_JUMPING_S = 0;
        this.PERSON_JUMPING_E = 3;
        this.PERSON_KILLING = new long[]{ 100L, 200L, 300L, 400L };
        this.PERSON_KILLING_S = 0;
        this.PERSON_KILLING_E = 3;
        this.PERSON_STANDING = new long[]{ 100L, 200L, 300L, 400L };
        this.PERSON_STANDING_S = 0;
        this.PERSON_STANDING_E = 3;
        this.PERSON_WALKING = new long[]{ 100L, 200L, 300L, 400L };
        this.PERSON_WALKING_S = 0;
        this.PERSON_WALKING_E = 3;


        this.getShape().animate(this.PERSON_STANDING, this.PERSON_STANDING_S, this.PERSON_STANDING_E, true);
    }

    public void jump()
    {
        if(this.jumpingAllowed == true)
        {
            this.jumping = true;
            this.jumpingAllowed = false;
        }
    }

    @Override
    public void updateAI()
    {
        if ((this.getBody().getPosition().dst(this.mActivity.getThePlayer().getBody().getPosition())*this.mActivity.PIXEL_TO_METRE_RATIO) < (this.range) && (this.alive))
        {
            //check pos
            if (getCenterXPos() < this.mActivity.getThePlayer().getCenterXPos()) {
                moveRight();
            }
            else if (getCenterXPos() > this.mActivity.getThePlayer().getCenterXPos()) {
                moveLeft();
            }

            //jump!
            jump();
        }
        else
        {
            moveStop();
        }
    }
}
