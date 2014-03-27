package com.platform.main;

import org.andengine.engine.Engine;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

public class MaterialManager
{
    public MainActivity mActivity;
    public TextureRegion[] materialsList;

    public MaterialManager(MainActivity paramMainActivity)
    {
        this.mActivity = paramMainActivity;
    }

    public ITextureRegion getMaterial(String paramString, int pWidth, int pHeight)
    {
        TextureRegion localTextureRegion = null;
        try
        {
            BuildableBitmapTextureAtlas localBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.mActivity.getEngine().getTextureManager(), pWidth, pHeight, TextureOptions.DEFAULT);
            localTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(localBuildableBitmapTextureAtlas, this.mActivity, "gfx/" + paramString);
            localBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder(0, 0, 0));
            localBuildableBitmapTextureAtlas.load();
            return localTextureRegion;
        }
        catch (Exception e)
        {
            this.mActivity.log("Failed to load " + paramString + " texture.");
            e.printStackTrace();
        }
        return localTextureRegion;
    }

    public TiledTextureRegion getTiledTexture(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
        TiledTextureRegion localTiledTextureRegion = null;
        try
        {
            BuildableBitmapTextureAtlas localBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.mActivity.getEngine().getTextureManager(), paramInt1, paramInt2, TextureOptions.NEAREST);
            localTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(localBuildableBitmapTextureAtlas, this.mActivity, "gfx/" + paramString, paramInt3, paramInt4);
            localBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder(0, 0, 0));
            localBuildableBitmapTextureAtlas.load();
            return localTiledTextureRegion;
        }
        catch (Exception e)
        {
            this.mActivity.gameToast("Failed to load " + paramString + " tiled texture.");
            e.printStackTrace();
        }
        return localTiledTextureRegion;
    }
}
