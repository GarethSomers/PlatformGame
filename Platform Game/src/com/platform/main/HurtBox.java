package com.platform.main;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;
import android.util.FloatMath;
import android.util.Log;

public class HurtBox  {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================	
	private float xPos = 100;
	private float yPos = 100;
	private MainActivity mActivity;
	private TiledTextureRegion mTiledTextureRegion;
	private AnimatedSprite animatedSprite;
	private float height;
	private float width;
	//variables
	private boolean alive;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public HurtBox (float xPos, float yPos, MainActivity mActivity)
	{
		//save variables
		this.height = 24;
		this.width = 14;
		this.xPos = xPos;
		this.yPos = yPos - this.height;
		this.mActivity = mActivity;

		this.alive = true;
		
		 
		/*
		 * CREATE TEXTURE
		 */
		BuildableBitmapTextureAtlas mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.mActivity.getEngine().getTextureManager(), 64, 24, TextureOptions.NEAREST);
		mTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, mActivity, "gfx/enemy.png", 4, 1);
		/* Build and load the mBitmapTextureAtlas object */
		try 
		{
			mBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		}
		catch (TextureAtlasBuilderException e)
		{
			e.printStackTrace();
		}
		mBitmapTextureAtlas.load();
		
		animatedSprite = new AnimatedSprite(this.xPos, this.yPos, mTiledTextureRegion, this.mActivity.getEngine().getVertexBufferObjectManager());
		long frameDuration[] = {100, 200, 300, 400};
		animatedSprite.animate(frameDuration);
	}
	
	
	// ===========================================================
	// METHODS
	// ===========================================================
	public void displayBox()
	{
		this.mActivity.getScene().attachChild(animatedSprite);
	}

	public boolean isPlayerAboveHurtbox(float xPos, float yPos, float width)
	{
		boolean theReturn = false;
		if(xPos > this.xPos - width)
		{
			if(xPos < this.getEndPos())
			{
				if(yPos < this.getYPos())
				{
					theReturn = true;
				}
			}
		}
		return theReturn;
	}
	
	public void killEnemy()
	{
		this.setAlive(false);
	}
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	
	public float getXPos()
	{
		return this.xPos;
	}
	
	public float getYPos()
	{
		return this.yPos;
	}
	
	public void setXPos(float xPos)
	{
		this.xPos = xPos;
	}
	
	public void setYPos(float yPos)
	{
		this.yPos = yPos;
	}

	public AnimatedSprite getSprite()
	{
		return this.animatedSprite;
	}

	public float getEndPos()
	{
		return this.xPos + this.width;
	}

	public boolean getAlive() {
		return alive;
	}


	public void setAlive(boolean alive) {
		this.alive = alive;
	}






	
	
	
}
