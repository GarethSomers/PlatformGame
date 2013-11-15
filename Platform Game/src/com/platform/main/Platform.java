package com.platform.main;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;
import android.util.FloatMath;
import android.util.Log;

public class Platform  {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================	
	private float xPos = 100;
	private float yPos = 100;
	private MainActivity mActivity;
	private TextureRegion mTiledTextureRegion;
	private Sprite theSprite;
	private float width;
	private float height;
	
	//variables
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public Platform (float xPos, float yPos, float width, float height, MainActivity mActivity)
	{
		//save variables
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		this.mActivity = mActivity;
		
		/*
		 * CREATE TEXTURE
		 */
		
		// LOAD GROUND TEXTURE
		BuildableBitmapTextureAtlas texture = new BuildableBitmapTextureAtlas(mActivity.getEngine().getTextureManager(), 8, 8, TextureOptions.REPEATING_BILINEAR);
		// Create our texture region - nothing new here
		mTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture, mActivity, "gfx/ground.png");
		try {
			// Repeating textures should not have padding
			texture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
			texture.load();
		}
		catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
		
		mTiledTextureRegion.setTextureSize(width,height); //stretches the texture
		theSprite = new Sprite(xPos, yPos, width, height, this.mTiledTextureRegion, mActivity.getEngine().getVertexBufferObjectManager());
		this.mActivity.getScene().attachChild(theSprite);
	}
	
	
	// ===========================================================
	// METHODS
	// ===========================================================
	
	public boolean isPlayerAbovePlatform(float xPos, float yPos, float width)
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

	public Sprite getSprite()
	{
		return this.theSprite;
	}
	public float getEndPos()
	{
		return this.xPos + this.width;
	}
	

}
