package com.platform.main;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import com.badlogic.gdx.math.Vector2;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.AudioOptions;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.TouchOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.debugdraw.DebugRenderer;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public class MainActivity
        extends SimpleBaseGameActivity
        implements IOnSceneTouchListener, IUpdateHandler
{
    private static final int CAMERA_HEIGHT = 480;
    private static final int CAMERA_WIDTH = 720;
    private ZoomCamera camera;
    private MyContactListener contactListener;
    private DebugRenderer debug;
    HUD hud;
    private LevelManager levelManager;
    private Font mFont;
    private PhysicsWorld mPhysicsWorld;
    private TextureRegion mSquareTextureRegion;
    private MaterialManager materialManager;
    private Text speedText;
    private Player thePlayer;
    private Text xText;
    private Text yText;

    private void createCamera()
    {
        this.camera.setChaseEntity(this.thePlayer.getSprite());
        this.camera.setBoundsEnabled(true);
        this.camera.setHUD(this.hud);
    }

    private void createContactListener()
    {
        this.contactListener = new MyContactListener(this);
        getPhysicsWorld().setContactListener(this.contactListener);
    }

    private void createEngineDefaults()
    {
        getEngine().registerUpdateHandler(new FPSLogger());
        getCamera().setZoomFactor(2.0F);
        getVertexBufferObjectManager();
        Looper.prepare();
    }

    private void createHud()
    {
        this.speedText = new Text(20.0F, 20.0F, this.mFont, "0", 99999, getVertexBufferObjectManager());
        this.hud = new HUD();
        this.hud.attachChild(this.speedText);
        this.hud.setOnSceneTouchListener(this);
    }

    private void createLevelManager()
    {
        this.levelManager = new LevelManager(this);
    }

    private void createMaterialManager()
    {
        this.materialManager = new MaterialManager(this);
    }

    private void createPhysics()
    {
        setPhysicsWorld(new PhysicsWorld(new Vector2(0.0F, 9.80665F), false));
        this.debug = new DebugRenderer(this.mPhysicsWorld, getVertexBufferObjectManager());
        setDebug(this.debug);
    }

    private void createPlayer()
    {
        this.thePlayer = new Player(50.0F, 50.0F, 100, this);
        this.thePlayer.setXPos(getLevelManager().lastStartPosX);
        this.thePlayer.setYPos(getLevelManager().lastStartPosY);
    }

    private void createScene()
    {
        this.levelManager.LoadLevel();
    }

    public void closeGame()
    {
        this.camera = null;
        this.contactListener = null;
        setDebug(null);
        this.levelManager = null;
        this.materialManager = null;
        this.mEngine = null;
        this.mFont = null;
        this.mPhysicsWorld = null;
        this.mRenderSurfaceView = null;
        this.mSquareTextureRegion = null;
        this.speedText = null;
        this.thePlayer = null;
        this.xText = null;
        this.yText = null;
        System.gc();
        finish();
    }

    public void gameToast(final String paramString)
    {
        runOnUiThread(new Runnable()
        {
            public void run()
            {
                Toast.makeText(MainActivity.this.getThis(), paramString, 0).show();
            }
        });
    }

    public ZoomCamera getCamera()
    {
        return this.camera;
    }

    public int getCameraHeight()
    {
        return 480;
    }

    public int getCameraWidth()
    {
        return 720;
    }

    public DebugRenderer getDebug()
    {
        return this.debug;
    }

    public LevelManager getLevelManager()
    {
        return this.levelManager;
    }

    public MaterialManager getMaterialManager()
    {
        return this.materialManager;
    }

    public PhysicsWorld getPhysicsWorld()
    {
        return this.mPhysicsWorld;
    }

    public Scene getScene()
    {
        return getLevelManager().getScene();
    }

    public Player getThePlayer()
    {
        return this.thePlayer;
    }

    public Context getThis()
    {
        return this;
    }

    public void log(String paramString)
    {
        Log.i("Game Debug Message", paramString);
    }

    public EngineOptions onCreateEngineOptions()
    {
        System.gc();
        this.camera = new ZoomCamera(0.0F, 0.0F, getCameraWidth(), getCameraHeight());
        this.camera.setZoomFactor(1.0F);
        EngineOptions localEngineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new FillResolutionPolicy(), getCamera());
        localEngineOptions.getTouchOptions().setNeedsMultiTouch(true);
        localEngineOptions.getAudioOptions().setNeedsMusic(true);
        localEngineOptions.getAudioOptions().setNeedsSound(true);
        return localEngineOptions;
    }

    public void onCreateResources()
    {
        this.mFont = FontFactory.create(getFontManager(), getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, 1), 24.0F);
        this.mFont.load();
        BuildableBitmapTextureAtlas localBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(getEngine().getTextureManager(), 8, 8, TextureOptions.REPEATING_BILINEAR);
        this.mSquareTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(localBuildableBitmapTextureAtlas, this, "gfx/ground.png");
        try
        {
            localBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder(0, 0, 0));
            localBuildableBitmapTextureAtlas.load();
            return;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Scene onCreateScene()
    {
        createEngineDefaults();
        createMaterialManager();
        createLevelManager();
        createPhysics();
        createScene();
        createHud();
        createPlayer();
        createCamera();
        createContactListener();
        return getScene();
    }

    public boolean onSceneTouchEvent(Scene paramScene, TouchEvent paramTouchEvent)
    {
        //TODO fix the close and reset buttons
        /*int i;
        if (paramTouchEvent.getX() < 50.0F)
        {
            i = 1;
            if (paramTouchEvent.getY() >= 50.0F) {
                //break label43;
            }
        }
        label43:
        for (int j = 1;; j = 0)
        {
            if ((i & j) == 0) {
                break label43;
            }
            reset();
            return true;
            i = 0;
            break;
        }
        label49:
        if (paramTouchEvent.getX() > -50 + getCameraWidth()) {}
        for (int k = 1;; k = 0)
        {
            boolean bool = paramTouchEvent.getY() < 50.0F;
            int m = 0;
            if (bool) {
                m = 1;
            }
            if ((k & m) == 0) {
                break;
            }
            closeGame();
            return true;
        }*/
        this.thePlayer.moveDetect(paramTouchEvent.getX(), paramTouchEvent.getY(), paramTouchEvent);
        return true;
    }

    public void onUpdate(float paramFloat)
    {
        this.speedText.setText(this.thePlayer.getSpeed());
        this.thePlayer.updatePosition();
        this.levelManager.updateLevel();
    }

    public void reset()
    {
        this.thePlayer.setPos(this.levelManager.lastStartPosX, this.levelManager.lastStartPosY);
        this.thePlayer.setAlive(true);
        this.thePlayer.jumpingAllowed = true;
        this.thePlayer.jumping = false;
        this.thePlayer.updatePosition();
    }

    public void setCamera(ZoomCamera paramZoomCamera)
    {
        this.camera = paramZoomCamera;
    }

    public void setDebug(DebugRenderer paramDebugRenderer)
    {
        if (this.debug != null) {
            this.debug.detachSelf();
        }
        this.debug = paramDebugRenderer;
    }

    public void setPhysicsWorld(PhysicsWorld paramPhysicsWorld)
    {
        this.mPhysicsWorld = paramPhysicsWorld;
    }
}
