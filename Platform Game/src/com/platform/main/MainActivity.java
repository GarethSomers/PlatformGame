package com.platform.main;

import android.content.Context;
import android.graphics.Typeface;
import android.hardware.SensorManager;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;
import com.badlogic.gdx.math.Vector2;
import com.platform.main.GameResources.Level.GameLevel;
import com.platform.main.GameResources.Object.Players.Player;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.debugdraw.DebugRenderer;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public class MainActivity
        extends SimpleBaseGameActivity
        implements IOnSceneTouchListener, IUpdateHandler
{
    private static int CAMERA_HEIGHT = 480;
    private static int CAMERA_WIDTH = 720;
    public static final float PIXEL_TO_METRE_RATIO = 50.00f;
    private ZoomCamera camera;
    private MyContactListener contactListener;
    private DebugRenderer debug;
    private LevelManager levelManager;
    private PhysicsWorld mPhysicsWorld;
    private MaterialManager materialManager;
    private Player thePlayer;
    public float zoomFactor = 3f;

    private void createCamera()
    {
        this.camera.setChaseEntity(this.thePlayer.getShape());
        this.camera.setBoundsEnabled(true);
        this.camera.setHUD(this.getLevelManager().getHUD());
    }

    private void createContactListener()
    {
        this.contactListener = new MyContactListener(this);
        getPhysicsWorld().setContactListener(this.contactListener);
    }

    private void createEngineDefaults()
    {
        getEngine().registerUpdateHandler(new FPSLogger());
        getCamera().setZoomFactor(zoomFactor);
        getVertexBufferObjectManager();
        Looper.prepare();
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
        setPhysicsWorld(new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH), false));
        this.getEngine().registerUpdateHandler(this.getPhysicsWorld());
    }

    private void createDebugDraw()
    {
        this.setDebug();
    }

    private void createPlayer()
    {
        this.thePlayer = new Player(10, 303, 100, this);
        this.thePlayer.setX(getLevelManager().lastStartPosX);
        this.thePlayer.setY(getLevelManager().lastStartPosY);
    }

    private void createScene()
    {
        this.levelManager.LoadLevel();
    }

    public void closeGame()
    {
        this.camera = null;
        this.contactListener = null;
        this.debug = null;
        this.levelManager = null;
        this.materialManager = null;
        this.mEngine = null;
        this.mPhysicsWorld = null;
        this.mRenderSurfaceView = null;
        this.thePlayer = null;
        this.getLevelManager().getLevel().destroy();
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
        return CAMERA_HEIGHT;
    }

    public int getCameraWidth()
    {
        return CAMERA_WIDTH;
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
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.CAMERA_WIDTH = displayMetrics.widthPixels;
        this.CAMERA_HEIGHT = displayMetrics.heightPixels;

        System.gc();
        this.camera = new ZoomCamera(0.0F, 0.0F, getCameraWidth(), getCameraHeight());
        EngineOptions localEngineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), getCamera());
        localEngineOptions.getTouchOptions().setNeedsMultiTouch(true);
        localEngineOptions.getAudioOptions().setNeedsMusic(true);
        localEngineOptions.getAudioOptions().setNeedsSound(true);
        return localEngineOptions;
    }

    public void onCreateResources()
    {

    }

    public Scene onCreateScene()
    {
        createEngineDefaults();
        createMaterialManager();
        createLevelManager();
        createPhysics();
        createScene();
        if(this.levelManager.getLevel() instanceof GameLevel)
        {
           completeLoadingScene();
        }
        return getScene();
    }

    public void completeLoadingScene()
    {
        this.getLevelManager().loadFirstLevel();
        this.getLevelManager().createHud();
        this.getLevelManager().getHUD().setOnSceneTouchListener(this);
        createPlayer();
        createCamera();
        createContactListener();
        createDebugDraw();
        this.getScene().registerUpdateHandler(this);
    }

    public boolean onSceneTouchEvent(Scene paramScene, TouchEvent paramTouchEvent)
    {
        //TODO fix the close and reset buttons
        if ((paramTouchEvent.getX() < 50.0F) & (paramTouchEvent.getY() < 50.0F))
        {
            //if left side
            this.reset();
        }
        else if ((paramTouchEvent.getX() > (this.getCameraWidth() - 50.0F)) & (paramTouchEvent.getY() < 50.0F))
        {
            this.closeGame();
        }
        this.thePlayer.moveDetect(paramTouchEvent.getX(), paramTouchEvent.getY(), paramTouchEvent);
        return true;
    }

    public void onUpdate(float paramFloat)
    {
        if(this.thePlayer != null)
        {
            //this.speedText.setText(Integer.toString(this.thePlayer.getHealth()));
            this.thePlayer.updatePosition();
        }

        if(this.getLevelManager().getLevel() != null)
        {
            this.levelManager.updateLevel();
        }
        //}
        //catch(Exception e)
        //{
            //e.printStackTrace();
        //}
    }

    public void reset()
    {
        this.thePlayer.setPos(this.levelManager.lastStartPosX, this.levelManager.lastStartPosY);
        this.thePlayer.setAlive(true);
        this.thePlayer.enableJumping();
        this.thePlayer.updatePosition();
    }

    public void setDebug()
    {
        if (this.debug != null) {
            this.debug.detachSelf();
        }
        this.debug = new DebugRenderer(this.getPhysicsWorld(),this.getVertexBufferObjectManager());
        //this.getScene().attachChild(this.debug);
    }

    public void setPhysicsWorld(PhysicsWorld paramPhysicsWorld)
    {
        this.mPhysicsWorld = paramPhysicsWorld;
    }
}

