package com.platform.main;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;

public class Enemy
        extends Person
{
    private Fixture mFeet;
    private float range;

    public Enemy(float paramFloat1, float paramFloat2, String paramString, MainActivity paramMainActivity)
    {
        this.mActivity = paramMainActivity;
        this.height = 24.0F;
        this.width = 16.0F;
        this.range = 60.0F;
        this.acceleration = 0.1F;
        this.maxSpeed = 2.0F;
        this.mTiledTextureRegion = paramMainActivity.getMaterialManager().getTiledTexture("Enemy", 64, 120, 4, 5);
        this.animatedSprite = new AnimatedSprite(paramFloat1, paramFloat2, this.mTiledTextureRegion, this.mActivity.getEngine().getVertexBufferObjectManager());
        this.animatedSprite.animate(this.PERSON_STANDING, this.PERSON_STANDING_S, this.PERSON_STANDING_E, true);
        this.fixtureDef = PhysicsFactory.createFixtureDef(2.0F, 0.0F, 1.0F);
        createBody();
        this.body.setUserData(this);
    }

    public void displayBox()
    {
        this.mActivity.getScene().attachChild(this.animatedSprite);
    }

    public boolean isPlayerAboveEnemy(float paramFloat1, float paramFloat2, float paramFloat3)
    {
        boolean bool1 = paramFloat1 < getEndPos();
        boolean bool2 = false;
        if (bool1)
        {
            boolean bool3 = paramFloat2 < getYPos();
            bool2 = false;
            if (bool3) {
                bool2 = true;
            }
        }
        return bool2;
    }

    public void kill()
    {
        this.animatedSprite.animate(this.PERSON_DYING, this.PERSON_DYING_S, this.PERSON_DYING_E, false);
        this.animatedSprite.clearUpdateHandlers();
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
