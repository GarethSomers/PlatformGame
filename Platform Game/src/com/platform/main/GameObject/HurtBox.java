package com.platform.main.gameobject;

import com.platform.main.MainActivity;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.TiledTextureRegion;

public class HurtBox
{
    private boolean alive;
    private AnimatedSprite animatedSprite;
    private float height = 24.0F;
    private MainActivity mActivity;
    private TiledTextureRegion mTiledTextureRegion;
    private float width = 14.0F;
    private float xPos = 100.0F;
    private float yPos = 100.0F;

    public HurtBox(float paramFloat1, float paramFloat2, MainActivity paramMainActivity)
    {
        this.xPos = paramFloat1;
        this.yPos = (paramFloat2 - this.height);
        this.mActivity = paramMainActivity;
        this.alive = true;
        BuildableBitmapTextureAtlas localBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.mActivity.getEngine().getTextureManager(), 64, 24, TextureOptions.NEAREST);
        this.mTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(localBuildableBitmapTextureAtlas, paramMainActivity, "gfx/enemy.png", 4, 1);
        try
        {
            localBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder(0, 0, 0));
            localBuildableBitmapTextureAtlas.load();
            this.animatedSprite = new AnimatedSprite(this.xPos, this.yPos, this.mTiledTextureRegion, this.mActivity.getEngine().getVertexBufferObjectManager());
            long[] arrayOfLong = { 100L, 200L, 300L, 400L };
            this.animatedSprite.animate(arrayOfLong);
            return;
        }
        catch (ITextureAtlasBuilder.TextureAtlasBuilderException localTextureAtlasBuilderException)
        {
            for (;;)
            {
                localTextureAtlasBuilderException.printStackTrace();
            }
        }
    }

    public void displayBox()
    {
        this.mActivity.getScene().attachChild(this.animatedSprite);
    }

    public boolean getAlive()
    {
        return this.alive;
    }

    public float getEndPos()
    {
        return this.xPos + this.width;
    }

    public AnimatedSprite getSprite()
    {
        return this.animatedSprite;
    }

    public float getXPos()
    {
        return this.xPos;
    }

    public float getYPos()
    {
        return this.yPos;
    }

    public boolean isPlayerAboveHurtbox(float paramFloat1, float paramFloat2, float paramFloat3)
    {
        boolean bool1 = paramFloat1 < this.xPos - paramFloat3;
        boolean bool2 = false;
        if (bool1)
        {
            boolean bool3 = paramFloat1 < getEndPos();
            bool2 = false;
            if (bool3)
            {
                boolean bool4 = paramFloat2 < getYPos();
                bool2 = false;
                if (bool4) {
                    bool2 = true;
                }
            }
        }
        return bool2;
    }

    public void killEnemy()
    {
        setAlive(false);
    }

    public void setAlive(boolean paramBoolean)
    {
        this.alive = paramBoolean;
    }

    public void setXPos(float paramFloat)
    {
        this.xPos = paramFloat;
    }

    public void setYPos(float paramFloat)
    {
        this.yPos = paramFloat;
    }
}
