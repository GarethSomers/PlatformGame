package com.platform.main;

import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
import com.platform.main.GameResources.Level.GameLevel;
import com.platform.main.GameResources.LevelObjects.AnimatedObjects.MoveableObjects.Player;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.debugdraw.DebugRenderer;
import org.andengine.extension.physics.box2d.PhysicsWorld;

/**
 * Created by Gareth Somers on 6/19/14.
 */
public class GameManager
        implements /*IOnSceneTouchListener,*/ IUpdateHandler
{
    private MainActivity mainActivity;
    private LevelManager levelManager;
    private MyContactListener contactListener;
    private DebugRenderer debug;
    private QuestManager questManager;
    private PhysicsWorld mPhysicsWorld;
    private MaterialManager materialManager;
    private Player thePlayer;
    private EventsManager eventsManager;
    private GameState gameManagerState = GameState.DECLARED;

    enum GameState
    {
        DECLARED, PLAYING, DEAD
    }

    public GameManager(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
        //nothing yet
        createMaterialManager();
        createQuestManager();
        createLevelManager();
        createPhysics();
        createScene();
        if(this.levelManager.getLevel() instanceof GameLevel)
        {
            completeLoadingScene();
        }
    }

    /*
    CREATE START UP STUFF
     */
    private void createScene()
    {
        this.levelManager.LoadLevel();
    }

    private void createCamera()
    {
        this.mainActivity.getCamera().setChaseEntity(this.thePlayer.getShape());
        this.mainActivity.getCamera().setBoundsEnabled(true);
        this.mainActivity.getCamera().setHUD(this.getLevelManager().getHUD());
    }

    private void createContactListener()
    {
        this.contactListener = new MyContactListener(this);
        getPhysicsWorld().setContactListener(this.contactListener);
    }

    private void createQuestManager()
    {
        this.questManager = new QuestManager(this);
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
        this.getMainActivity().getEngine().registerUpdateHandler(this.getPhysicsWorld());
    }

    private void createDebugDraw()
    {
        this.setDebug();
    }

    private void createPlayer()
    {
        this.thePlayer = new Player(this);
        this.thePlayer.createObject();
    }

    /*
    GETTERS
     */
    public LevelManager getLevelManager()
    {
        return this.levelManager;
    }
    public DebugRenderer getDebug()
    {
        return this.debug;
    }

    public QuestManager getQuestManager()
    {
        return this.questManager;
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

    public MainActivity getMainActivity()
    {
        return this.mainActivity;
    }
    /*
    SETTER
     */
    public void setDebug()
    {
        if (this.debug!= null) {
            this.debug.detachSelf();
        }
        this.debug = new DebugRenderer(this.getPhysicsWorld(),this.mainActivity.getVertexBufferObjectManager());
        this.debug.setZIndex(999);
        this.getScene().attachChild(this.debug);
    }

    public void setPhysicsWorld(PhysicsWorld paramPhysicsWorld)
    {
        this.mPhysicsWorld = paramPhysicsWorld;
    }

    /*
    METHODS
     */
    public void completeLoadingScene()
    {
        this.eventsManager = new EventsManager(this);
        this.getLevelManager().loadFirstLevel();
        this.getLevelManager().createHud();
        //this.getLevelManager().getHUD().setOnSceneTouchListener(mainActivity);
        createPlayer();
        createCamera();
        createContactListener();
        createDebugDraw();
        this.getScene().registerUpdateHandler(this);
        this.getScene().sortChildren();
        this.getLevelManager().getHUD().setTouchAreaBindingOnActionMoveEnabled(true);
        this.getLevelManager().getHUD().setTouchAreaBindingOnActionDownEnabled(true);
        this.gameManagerState = GameState.PLAYING;
    }

    public void closeGame()
    {
        //TODO delete camera
        this.contactListener = null;
        this.debug = null;
        this.levelManager = null;
        this.materialManager = null;
        this.mPhysicsWorld = null;
        this.thePlayer = null;
        this.getLevelManager().getLevel().destroy();
        System.gc();
        mainActivity.finish();
    }

    public EventsManager getEventsManager() {
        return eventsManager;
    }

    public void setEventsManager(EventsManager eventsManager) {
        this.eventsManager = eventsManager;
    }


    /*********************************************************************************************/
    /* GAME OVER */
    /*********************************************************************************************/
    public void gameOver() {
        if(this.gameManagerState == GameState.PLAYING)
        {
            this.gameManagerState = GameState.DEAD;
            this.getLevelManager().getHUD().setGameOver(true);
            this.getThePlayer().kill();
        }
    }

    /*********************************************************************************************/
    /* UPDATE */
    /*********************************************************************************************/
    public void onUpdate(float paramFloat)
    {
        if(this.gameManagerState == GameState.PLAYING || this.gameManagerState == GameState.DEAD)
        {
            if(this.getThePlayer() != null)
            {
                //this.speedText.setText(Integer.toString(this.thePlayer.getHealth()));
                this.getThePlayer().updatePosition();
            }

            if(this.getLevelManager().getLevel() != null)
            {
                this.getLevelManager().updateLevel();
            }
        }
    }

    @Override
    public void reset()
    {
        this.getLevelManager().getHUD().setGameOver(false);
        this.getThePlayer().reload(((GameLevel) this.getLevelManager().getLevel()).getWidth()/2,((GameLevel)this.getLevelManager().getLevel()).getHeight()/2);
        this.getEventsManager().resetTimer();
        this.getEventsManager().startEventsManager();
        this.getLevelManager().getScene().sortChildren();
        this.getThePlayer().updatePosition();
        this.gameManagerState = GameState.PLAYING;
    }

}
