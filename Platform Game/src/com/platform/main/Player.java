package com.platform.main;

import java.util.ArrayList;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.TiledTextureRegion;

public class Player {
	// ===========================================================
	// Constants
	// ===========================================================
	// animations
	final long[] PLAYER_STANDING;
	final long[] PLAYER_WALKING;
	final long[] PLAYER_JUMPING;
	final long[] PLAYER_KILLING;
	
	// ===========================================================
	// Fields
	// ===========================================================
	private float xPos = 100;
	private float yPos = 100;
	private MainActivity mActivity;
	private TiledTextureRegion mTiledTextureRegion;
	private AnimatedSprite animatedSprite;

	// movement
	float velocity_x = 0;
	float velocity_y = 0;
	private float speed = 0;
	private float acceleration = 0.3f;
	private boolean moveLeft = false; // used to check if moving left
	private boolean moveRight = false; // used to check if moving right
	private boolean moving;
	private float maxSpeed = 3f;
	private long[] currentAnimation;

	// gravity
	private float gravity = 0.5f; // used for gravity
	private float ground; // used for the base location of the ground
	private boolean aboutToJump = false; // used to signal jumping
	private boolean jumping = false; // used to signal jumpedw
	private float jumpHeight = 10;

	// variables
	private int health;
	private boolean playerAlive = true;
	private float height;
	private float width;

	// ===========================================================
	// Constructors
	// ===========================================================
	public Player(float xPos, float yPos, int health, MainActivity mActivity) {
		// save variables
		this.health = health;
		this.xPos = xPos;
		this.yPos = yPos;
		this.ground = yPos;
		this.mActivity = mActivity;
		this.height = 24;
		this.width = 14;

		/*
		 * CREATE TEXTURE
		 */
		BuildableBitmapTextureAtlas mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(
				this.mActivity.getEngine().getTextureManager(), 68, 96,
				TextureOptions.NEAREST);
		mTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(mBitmapTextureAtlas, mActivity,
						"gfx/player.png", 4, 4);
		/* Build and load the mBitmapTextureAtlas object */
		try
		{
			mBitmapTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 0));
		}
		catch (TextureAtlasBuilderException e)
		{
			e.printStackTrace();
		}
		mBitmapTextureAtlas.load();

		// create animations
		PLAYER_STANDING = new long[]
		{ 100, 200, 300, 400 };
		PLAYER_WALKING = new long[]
		{ 75, 150, 225, 300 };
		PLAYER_JUMPING = new long[]
		{ 100, 200, 600, 700 };
		PLAYER_KILLING = new long[]
		{ 100, 200, 300, 400 };
	}

	// ===========================================================
	// METHODS
	// ===========================================================
	public void displayPlayer() {
		animatedSprite = new AnimatedSprite(this.xPos, this.yPos,
				mTiledTextureRegion, this.mActivity.getEngine()
						.getVertexBufferObjectManager());
		animatedSprite.animate(PLAYER_STANDING, 8, 11, true);
		currentAnimation = PLAYER_STANDING;
		this.mActivity.getScene().attachChild(animatedSprite);
	}

	public void updatePosition() {
		// Ensure Player is alive
		if (this.getPlayerAlive() == false)
		{
			return;
		}
		/*
		 * CHECK IF GROUND HAS CHANGED
		 */
		this.ground = this.mActivity.getCameraHeight();
		for (Platform p : this.mActivity.getPlatforms())
		{
			// check if platform above the current platform
			// check if the platform is under the player
			if (p.getYPos() - this.height < this.ground
					& p.isPlayerAbovePlatform(this.getXPos(), this.getYPos()
							- this.height, this.width) == true)
			{
				this.ground = p.getYPos() - this.height;
			}
		}

		/*
		 * HANDLE Y MOVEMENT
		 */
		// check if jumping and not already jumped
		if ((this.jumping == false) & (this.aboutToJump == true))
		{
			velocity_y = -this.jumpHeight;
			this.jumping = true;
			animatedSprite.animate(PLAYER_JUMPING, 4, 7, false);
			currentAnimation = PLAYER_JUMPING;
		}
		// check if jumping, move player by velocity_y
		if (this.jumping == true)
		{
			// check if above ground and players velocity is upwards
			if ((this.yPos + this.velocity_y < this.ground)
					|| (this.velocity_y < 0))
			{
				// slow down players upwards velocity
				this.yPos = this.yPos + this.velocity_y;
				this.velocity_y = this.velocity_y + gravity;
			}
			else
			{
				// player hit the ground
				this.yPos = this.ground;
				this.jumping = false;
				animatedSprite.animate(PLAYER_STANDING, 8, 11, true);
				currentAnimation = PLAYER_STANDING;
			}
			this.aboutToJump = false;
		}
		else
			if (this.yPos + this.velocity_y < this.ground)
			{
				// Player walked off a platform
				this.yPos = this.yPos + this.velocity_y;
				this.velocity_y = this.velocity_y + gravity;
			}
			else
			{
				// not falling, on the ground
				this.yPos = this.ground;
			}

		/*
		 * HANDLE X MOVEMENT
		 */
		if (this.moveLeft == true)
		{
			// move left
			this.speed -= this.acceleration;
			// animate the sprite
			this.animatedSprite.setFlippedHorizontal(true);
			if ((this.jumping == false) & (this.speed < 0) & (this.currentAnimation != PLAYER_WALKING))
				animatedSprite.animate(PLAYER_WALKING, 0, 3, true);
				currentAnimation = PLAYER_WALKING;
		}
		else if (this.moveRight == true)
		{
			// move right
			this.speed += this.acceleration;
			// animate the sprite
			this.animatedSprite.setFlippedHorizontal(false);
			if ((this.jumping == false) & (this.speed > 0) & (this.currentAnimation != PLAYER_WALKING))
				animatedSprite.animate(PLAYER_WALKING, 0, 3, true);
				currentAnimation = PLAYER_WALKING;
		}
		else
		{
			// slow him down!
			if (this.speed > this.acceleration)
			{
				// players is moving right. slow left
				this.speed -= this.acceleration;
			}
			else if (this.speed < -this.acceleration)
			{
				// player is moving left. slow right
				this.speed += this.acceleration;
			}
			else
			{
				// speed is 0
				this.speed = 0;
				if (this.jumping == false)
					animatedSprite.animate(PLAYER_STANDING, 8, 11, true);
					currentAnimation = PLAYER_STANDING;
			}
		}

		// update position based on speed
		if (this.speed > this.maxSpeed)
		{
			this.speed = this.maxSpeed;
		}
		else
			if (this.speed < -this.maxSpeed)
			{
				this.speed = -this.maxSpeed;
			}
		this.xPos += this.speed;

		// update the sprite position
		this.animatedSprite.setPosition(this.xPos, this.yPos);
	}

	private float getHeight() {
		// TODO Auto-generated method stub
		return this.height;
	}

	public void moveDetect(float theX, float theY, int action) {
		if (this.getPlayerAlive() == false)
		{
			return;
		}

		if (theX < (this.mActivity.getCameraWidth() / 3)
				& (action == TouchEvent.ACTION_DOWN || action == TouchEvent.ACTION_MOVE))
		{
			// left
			this.moveLeft();
		}
		else
			if (theX > (this.mActivity.getCameraWidth() * 2) / 3
					& (action == TouchEvent.ACTION_DOWN || action == TouchEvent.ACTION_MOVE))
			{
				// right
				this.moveRight();
			}
			else
				if ((theX > (this.mActivity.getCameraWidth() / 3))
						& (theX < (this.mActivity.getCameraWidth() * 2) / 3)
						& (action == TouchEvent.ACTION_DOWN || action == TouchEvent.ACTION_MOVE))
				{
					// middle
					this.jump();
				}
				else
					if (action == TouchEvent.ACTION_UP)
					{
						moveStop();
					}

	}

	public void moveLeft() {
		this.moveLeft = true;
		this.moveRight = false;
	}

	public void moveRight() {
		this.moveLeft = false;
		this.moveRight = true;
	}

	public void moveStop() {
		this.moveLeft = false;
		this.moveRight = false;
	}

	public void jump() {
		this.aboutToJump = true;
	}

	public boolean checkDead(ArrayList<HurtBox> hurtBoxs) {
		for (HurtBox h : hurtBoxs)
		{
			//loop through all enemies
			if (h.getAlive() == true)
			{
				//check if enemy is alive
				if (this.animatedSprite.collidesWith(h.getSprite()))
				{
					//if the player has collided with them check if they are above them
					if (h.isPlayerAboveHurtbox(this.xPos, this.yPos, this.width))
					{
						//if the player is above kill the enemy
						velocity_y = -this.jumpHeight;
						this.jumping = true;
						animatedSprite.animate(PLAYER_KILLING, 12, 15, false);
						currentAnimation = PLAYER_KILLING;
						h.killEnemy();
						//play animation
						return false;
					}
					return true;
				}
			}
		}
		return false;
	}

	public void killPlayer() {
		this.setPlayerAlive(false);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public float getXPos() {
		return this.xPos;
	}

	public float getYPos() {
		return this.yPos;
	}

	public void setXPos(float xPos) {
		this.xPos = xPos;
	}

	public void setYPos(float yPos) {
		this.yPos = yPos;
	}

	public String getSpeed() {
		// TODO Auto-generated method stub
		return String.valueOf(this.speed);
	}

	public AnimatedSprite getSprite() {
		return this.animatedSprite;
	}

	public boolean getPlayerAlive() {
		return playerAlive;
	}

	public void setPlayerAlive(boolean playerAlive) {
		this.playerAlive = playerAlive;
	}

	private float getCenterYPos() {
		// TODO Auto-generated method stub
		return this.xPos + 7;
	}

	private float getCenterXPos() {
		// TODO Auto-generated method stub
		return this.xPos + 12;
	}

}
