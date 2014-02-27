package com.platform.main;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.*;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.opengl.texture.region.TiledTextureRegion;

public abstract class Person
{
    public int CLIMBING_JUMP_HEIGHT;
    public int JUMP_HEIGHT;
    public float MAX_SPEED;
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
    public float PLAYER_ACCELERATION;
    public int PLAYER_HEIGHT;
    public int PLAYER_WIDTH;
    protected float acceleration = 0.2F;
    protected boolean alive = true;
    protected AnimatedSprite animatedSprite;
    protected Body body;
    protected long[] currentAnimation;
    protected Fixture feet;
    protected FixtureDef fixtureDef;
    protected float height;
    protected int jumpHeight = 6;
    protected boolean jumping = false;
    protected boolean jumpingAllowed = true;
    protected MainActivity mActivity;
    protected TiledTextureRegion mTiledTextureRegion;
    protected float maxSpeed = 3.0F;
    protected boolean moveLeft = false;
    protected boolean moveRight = false;
    protected float velocity_x = 0.0F;
    protected float width;

    public void addToPhysicsWorld()
    {
        this.mActivity.getPhysicsWorld().registerPhysicsConnector(new PhysicsConnector(this.animatedSprite, this.body, true, false));
    }

    public void createBody()
    {
        this.body = PhysicsFactory.createBoxBody(this.mActivity.getPhysicsWorld(), this.animatedSprite, BodyDef.BodyType.DynamicBody, this.fixtureDef);
        this.body.setFixedRotation(true);
        this.body.setLinearVelocity(0.0F, 0.0F);
        addToPhysicsWorld();
        PolygonShape localPolygonShape = new PolygonShape();
        localPolygonShape.setAsBox(0.1F, 0.1F, new Vector2(0.0F, 0.4F), 0.0F);
        FixtureDef localFixtureDef = PhysicsFactory.createFixtureDef(1.0F, 0.0F, 0.0F, true);
        localFixtureDef.isSensor = true;
        localFixtureDef.shape = localPolygonShape;
        this.feet = this.body.createFixture(localFixtureDef);
        localPolygonShape.dispose();
    }

    public void disableClimbing()
    {
        this.jumping = false;
        setAnimation("endclimbing");
    }

    public void enableClimbing()
    {
        this.jumpingAllowed = true;
        this.jumping = false;
        setAnimation("climbing");
    }

    public boolean getAlive()
    {
        return this.alive;
    }

    public float getBottomPos()
    {
        return getYPos() + this.height;
    }

    public float getCenterXPos()
    {
        return getXPos() + this.width / 2.0F;
    }

    public float getCenterYPos()
    {
        return getYPos() + this.height / 2.0F;
    }

    public float getEndPos()
    {
        return getXPos() + this.width;
    }

    public float getHeight()
    {
        return this.height;
    }

    public String getSpeed()
    {
        return String.valueOf(this.velocity_x);
    }

    public AnimatedSprite getSprite()
    {
        return this.animatedSprite;
    }

    public float getWidth()
    {
        return this.width;
    }

    public float getXPos()
    {
        return this.body.localPoint2.x;
    }

    public float getYPos()
    {
        return this.body.localPoint2.y;
    }

    public void handleXMovement()
    {
        if (this.moveLeft)
        {
            this.velocity_x -= this.acceleration;
            this.animatedSprite.setFlippedHorizontal(true);
            if (this.velocity_x < 0.0F) {
                setAnimation("walking");
            }
            if (this.velocity_x <= this.maxSpeed) {
                //break label184;
            }
            this.velocity_x = this.maxSpeed;
        }
        label184:
        while (this.velocity_x >= -this.maxSpeed)
        {
            if (this.moveRight)
            {
                this.velocity_x += this.acceleration;
                this.animatedSprite.setFlippedHorizontal(false);
                if (this.velocity_x <= 0.0F) {
                    break;
                }
                setAnimation("walking");
                break;
            }
            if (this.velocity_x > this.acceleration)
            {
                this.velocity_x -= this.acceleration;
                break;
            }
            if (this.velocity_x < -this.acceleration)
            {
                this.velocity_x += this.acceleration;
                break;
            }
            this.velocity_x = 0.0F;
            setAnimation("standing");
            break;
        }
        this.velocity_x = (-this.maxSpeed);
    }

    public void handleYMovement()
    {
        if (this.jumping)
        {
            this.body.setLinearVelocity(this.body.getLinearVelocity().x, -this.jumpHeight);
            setAnimation("jumping");
            this.jumping = false;
        }
    }

    public void kill()
    {
        this.alive = false;
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

    public void setAlive(boolean paramBoolean)
    {
        this.alive = paramBoolean;
    }

    public void setAnimation(String paramString)
    {
        if (paramString.equals("endclimbing"))
        {
            this.animatedSprite.animate(this.PERSON_WALKING, this.PERSON_WALKING_S, this.PERSON_WALKING_E, true);
            this.currentAnimation = this.PERSON_WALKING;
        }
        do
        {
            do
            {
                do
                {
                    //um?
                } while (this.currentAnimation == this.PERSON_CLIMBING);
                if ((paramString.equals("climbing")) && (this.currentAnimation != this.PERSON_CLIMBING))
                {
                    this.animatedSprite.animate(this.PERSON_CLIMBING, this.PERSON_CLIMBING_S, this.PERSON_CLIMBING_E, true);
                    this.currentAnimation = this.PERSON_CLIMBING;
                    return;
                }
            } while ((this.currentAnimation == this.PERSON_JUMPING) && (this.animatedSprite.isAnimationRunning()));
            if ((paramString.equals("jumping")) && (this.currentAnimation != this.PERSON_JUMPING))
            {
                this.animatedSprite.animate(this.PERSON_JUMPING, this.PERSON_JUMPING_S, this.PERSON_JUMPING_E, false);
                this.currentAnimation = this.PERSON_JUMPING;
                return;
            }
            if ((paramString.equals("walking")) && (this.currentAnimation != this.PERSON_WALKING))
            {
                this.animatedSprite.animate(this.PERSON_WALKING, this.PERSON_WALKING_S, this.PERSON_WALKING_E, true);
                this.currentAnimation = this.PERSON_WALKING;
                return;
            }
        } while ((!paramString.equals("standing")) || (this.currentAnimation == this.PERSON_STANDING));
        this.animatedSprite.animate(this.PERSON_STANDING, this.PERSON_STANDING_S, this.PERSON_STANDING_E, true);
        this.currentAnimation = this.PERSON_STANDING;
    }

    public void setPos(float paramFloat1, float paramFloat2)
    {
        this.body.getPosition().set(paramFloat1, paramFloat2);
        float f1 = this.animatedSprite.getWidth() / 2.0F;
        float f2 = this.animatedSprite.getHeight() / 2.0F;
        float f3 = this.body.getAngle();
        Vector2 localVector2 = Vector2Pool.obtain((paramFloat1 + f1) / 32.0F, (paramFloat2 + f2) / 32.0F);
        this.body.setTransform(localVector2, f3);
        Vector2Pool.recycle(localVector2);
    }

    public void setXPos(float paramFloat)
    {
        this.body.getPosition().set(paramFloat, this.body.getPosition().y);
    }

    public void setYPos(float paramFloat)
    {
        this.body.getPosition().set(this.body.getPosition().x, paramFloat);
    }
}
