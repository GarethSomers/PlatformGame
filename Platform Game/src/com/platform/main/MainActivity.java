package com.platform.main;

import java.util.ArrayList;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.controller.MultiTouch;
import org.andengine.input.touch.detector.HoldDetector;
import org.andengine.input.touch.detector.HoldDetector.IHoldDetectorListener;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.HorizontalAlign;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Debug;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.platform.main.Player;
/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga
 *
 * @author Nicolas Gramlich
 * @since 11:54:51 - 03.04.2010
 */
public class MainActivity extends SimpleBaseGameActivity implements IOnSceneTouchListener,IUpdateHandler 
{
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;

	// ===========================================================
	// Fields
	// ===========================================================

	private Font mFont;
	private Player thePlayer;
	private Scene scene;
	private ZoomCamera camera;
	
	//hud
	HUD hud;
	private Text speedText;
	private Text xText;
	private Text yText;
	private ArrayList<HurtBox> hurtBoxs;
	private ArrayList<Platform> platforms;
	private TextureRegion mSquareTextureRegion;
	
	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	/*
	 * CREATE THE ENGINE
	 */
	@Override
	public EngineOptions onCreateEngineOptions() {
		this.camera = new ZoomCamera(0, 0, getCameraWidth(), CAMERA_HEIGHT);
		this.camera.setZoomFactor(1.0f);
		 final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new FillResolutionPolicy(), getCamera());
		 engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		 return engineOptions;
	}
	/*
	 * END CREATE THE ENGINE
	 */
	
	
	
	/*
	 *  CREATE THE RESOURCES
	 */
	@Override
	public void onCreateResources()
	{
		this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 24);
		this.mFont.load();
		
		// LOAD GROUND TEXTURE
		BuildableBitmapTextureAtlas texture = new BuildableBitmapTextureAtlas(this.getEngine().getTextureManager(), 8, 8, TextureOptions.REPEATING_BILINEAR);
		// Create our texture region - nothing new here
		mSquareTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture, this, "gfx/ground.png");
		try {
			// Repeating textures should not have padding
			texture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
			texture.load();
		}
		catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}

	}
	/*
	 * END CREATE THE RESOURCES
	 */
	
	
	
	/*
	 * CREATE THE SCENE
	 */
	@Override
	public Scene onCreateScene()
	{
		//DEFAULTS
		this.getEngine().registerUpdateHandler(new FPSLogger());
		this.getCamera().setZoomFactor(2.0f);
		final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();
	    Looper.prepare();


        
		//SCENE
		scene = new Scene();
		scene.getBackground().setColor(0.09804f, 0.6274f, 0.8784f);
		this.scene.setTouchAreaBindingOnActionDownEnabled(true);
		
		//HUD
		speedText = new Text(20, 20, this.mFont, "0", 99999, vertexBufferObjectManager);

		hud = new HUD();
		hud.attachChild(speedText);
		hud.setOnSceneTouchListener(this);
		camera.setHUD(hud);
		
		//THE GROUND
		//mSquareTextureRegion.setTextureSize(1200,192); //stretches the texture
		//Sprite sprite = new Sprite(0, this.getCameraHeight() -192, 1200, 192, this.mSquareTextureRegion, this.getEngine().getVertexBufferObjectManager());
		//this.scene.attachChild(sprite);
		
		//THE PLATFORMS
		setPlatforms(new ArrayList<Platform>());
		getPlatforms().add(new Platform (100, this.getCameraHeight() -260, 200, 20, this)); 
		getPlatforms().add(new Platform (0, this.getCameraHeight() -200, 1200, 192, this)); 
		//platforms.add(new Platform (0, this.getCameraHeight() -192, 1200, 192, this)); 
		
		//THE PLAYER
		thePlayer = new Player(this.getCameraWidth()* 0.5f, this.getCameraHeight() -200, 100, this);
		this.scene.registerUpdateHandler(this);
		thePlayer.displayPlayer();
		
		//THE ENEMIES
		hurtBoxs = new ArrayList<HurtBox>();
		hurtBoxs.add(new HurtBox(150, this.getCameraHeight() -260, this));
		hurtBoxs.add(new HurtBox(50, this.getCameraHeight() -200, this));
		
		for (HurtBox h : hurtBoxs)
		{
			h.displayBox();
		}
		
		//THE BUTTONS 
		//this.registerButtons();
		
		return scene;
	}
	/*
	 * END CREATE THE SCENE
	 */
	
	
	
	/*
	 * UPDATE THE SCENE
	 */
	@Override
	public void onUpdate(float pSecondsElapsed)
	{
		if(this.thePlayer.checkDead(this.hurtBoxs) == true)
		{
			//player is dead
			this.speedText.setText("PLAYER IS DEAD. CLICK HERE TO RESET");
			this.thePlayer.killPlayer();
		}
		else
		{
			this.speedText.setText(this.thePlayer.getSpeed());
			this.thePlayer.updatePosition();
			this.camera.setCenter(thePlayer.getXPos()-(thePlayer.getSprite().getWidth()/2), thePlayer.getYPos());
		}

		
	}

	@Override
	public void reset()
	{
		thePlayer.setXPos(this.getCameraWidth()* 0.0f);
		thePlayer.setXPos(this.getCameraHeight() -200);
		thePlayer.setPlayerAlive(true);
		this.thePlayer.updatePosition();
	}
	/*
	 * END UPDATE THE SCENE
	 */
	
	
	
	/*
	 * TOUCH EVENTS
	 */
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent)
	{
		if(this.thePlayer.getPlayerAlive() == false)
		{
			if(pSceneTouchEvent.getX() < 50 & pSceneTouchEvent.getY() < this.getCameraWidth()/2)
			{
				this.reset();
			}
			return true;
		}
		this.thePlayer.moveDetect(pSceneTouchEvent.getX(), pSceneTouchEvent.getY(), pSceneTouchEvent.getAction());
		return true;
	}
	/*
	 * END TOUCH EVENTS
	 */

	// ===========================================================
	// Methods
	// ===========================================================
	public void registerButtons()
	{
		Rectangle left = new Rectangle(0, 0, getCameraWidth()/3, getCameraHeight(), this.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
            	getThePlayer().moveDetect(pSceneTouchEvent.getX(), pSceneTouchEvent.getY(), pSceneTouchEvent.getAction());
                return true;
            }
		};
		left.setColor(0, 0, 0, 0.3f);
		this.hud.attachChild(left);
		this.scene.registerTouchArea(left);
		
		Rectangle middle = new Rectangle(getCameraWidth()/3, 0, getCameraWidth()/3, getCameraHeight(), this.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
            	getThePlayer().moveDetect(pSceneTouchEvent.getX(), pSceneTouchEvent.getY(), pSceneTouchEvent.getAction());
                return true;
            }
		};
		middle.setColor(0, 0, 0, 0.7f);
		this.hud.attachChild(middle);
		this.scene.registerTouchArea(middle);
		
		Rectangle right = new Rectangle((getCameraWidth()*2)/3, 0, getCameraWidth()/3, getCameraHeight(), this.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
            	getThePlayer().moveDetect(pSceneTouchEvent.getX(), pSceneTouchEvent.getY(), pSceneTouchEvent.getAction());
               return true;
            }
		};
		right.setColor(0, 0, 0, 0.3f);
		this.hud.attachChild(right);
		this.scene.registerTouchArea(right);
	}
	public Scene getScene() 
	{
		return scene;
	}
	public int getCameraWidth() 
	{
		return CAMERA_WIDTH;
	}

	public int getCameraHeight()
	{
		return CAMERA_HEIGHT;
	}
	public ZoomCamera getCamera() {
		return camera;
	}

	public void setCamera(ZoomCamera camera) {
		this.camera = camera;
	}
	
	public Context getThis()
	{
		return this;
	}
	
    public void gameToast(final String msg)
    {
	    this.runOnUiThread(new Runnable() {
	        @Override
	        public void run() {
	           Toast.makeText(getThis(), msg, Toast.LENGTH_SHORT).show();
	        }
	    });
    }
    
    public Player getThePlayer()
    {
    	return this.thePlayer;
    }
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================



	public ArrayList<Platform> getPlatforms() {
		return platforms;
	}



	public void setPlatforms(ArrayList<Platform> platforms) {
		this.platforms = platforms;
	}

}
