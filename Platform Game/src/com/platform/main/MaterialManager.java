package com.platform.main;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

public class MaterialManager
{
    public GameManager gameManager;
    public TextureRegion[] materialsList;

    public MaterialManager(GameManager paramMainActivity)
    {
        this.gameManager = paramMainActivity;
    }

    public ITextureRegion getTexture(String paramString, int pWidth, int pHeight)
    {
        return this.getTexture(paramString,pWidth,pHeight,TextureOptions.DEFAULT, pWidth, pHeight);
    }

    public ITextureRegion getTexture(String paramString, int pWidth, int pHeight, TextureOptions to, int repeatWidth, int repeatHeight)
    {
        TextureRegion localTextureRegion = null;
        try
        {
            BuildableBitmapTextureAtlas localBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.gameManager.getMainActivity().getEngine().getTextureManager(), pWidth, pHeight, to);
            localTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(localBuildableBitmapTextureAtlas, this.gameManager.getMainActivity().getAssets(), "gfx/" + paramString);
            localBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder(0, 0, 0));
            localBuildableBitmapTextureAtlas.load();
            localTextureRegion.setTextureWidth(repeatWidth);
            localTextureRegion.setTextureHeight(repeatHeight);
            return localTextureRegion;
        }
        catch (Exception e)
        {
            this.gameManager.getMainActivity().log("Failed to load " + paramString + " texture.");
            e.printStackTrace();
        }
        return localTextureRegion;
    }

    public TiledTextureRegion getTiledTexture(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
        TiledTextureRegion localTiledTextureRegion = null;
        try
        {
            BuildableBitmapTextureAtlas localBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.gameManager.getMainActivity().getEngine().getTextureManager(), paramInt1, paramInt2, TextureOptions.NEAREST);
            localTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(localBuildableBitmapTextureAtlas, this.gameManager.getMainActivity().getAssets(), "gfx/" + paramString, paramInt3, paramInt4);
            localBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder(0, 0, 0));
            localBuildableBitmapTextureAtlas.load();
            return localTiledTextureRegion;
        }
        catch (Exception e)
        {
            this.gameManager.getMainActivity().gameToast("Failed to load " + paramString + " tiled texture.");
            e.printStackTrace();
        }
        return localTiledTextureRegion;
    }
}
