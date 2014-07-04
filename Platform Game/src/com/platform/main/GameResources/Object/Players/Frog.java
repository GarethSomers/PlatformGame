package com.platform.main.GameResources.Object.Players;

import com.platform.main.GameManager;

public class Frog extends Enemy
{
    //variables
    protected boolean alive = true;
    //animations

    //constructor
    public Frog(GameManager mMainActivity)
    {
        super(mMainActivity);
        this.setWidth(71);
        this.setHeight(23);
        this.setImage("frog.png");
        this.columns = 2;
        this.rows = 4;
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
        if ((this.getBody().getPosition().dst(this.gameManager.getThePlayer().getBody().getPosition())*this.gameManager.getMainActivity().PIXEL_TO_METRE_RATIO) < (this.range) && (this.alive))
        {
            //check pos
            if (getCenterXPos() < this.gameManager.getThePlayer().getCenterXPos()) {
                moveRight();
            }
            else if (getCenterXPos() > this.gameManager.getThePlayer().getCenterXPos()) {
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
