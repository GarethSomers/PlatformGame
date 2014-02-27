package com.platform.main;

import java.util.ArrayList;
import java.util.Iterator;
import org.andengine.entity.scene.Scene;
import org.andengine.util.level.LevelLoader;

public class LevelManager
{
    private static final String TYPE_CLIPPING = "clipping";
    private static final String TYPE_DOORWAY = "doorway";
    private static final String TYPE_ENEMY = "enemy";
    private static final String TYPE_IGNORE = "ignore";
    private static final String TYPE_LADDER = "ladder";
    private static final String TYPE_MESSAGE = "message";
    private static final String TYPE_PLATFORM = "platform";
    private static final String TYPE_PLATFORM_POLYGON = "platform-polygon";
    private static final int TYPE_PLATFORM_POLYGON_TOTAL_INDEX = 8;
    private static final String TYPE_SETUP = "setup";
    private Level currentLevel;
    public int lastStartPosX = 0;
    public int lastStartPosY = 0;
    private LevelLoader levelLoader;
    private MainActivity mActivity;
    private String scheduledDestination;
    private int scheduledDestinationX;
    private int scheduledDestinationY;

    public LevelManager(MainActivity paramMainActivity)
    {
        this.mActivity = paramMainActivity;
    }

    public void LoadLevel()
    {
        LoadLevel("one", 50, 50);
    }

    /* Error */
    public void LoadLevel(String paramString, int paramInt1, int paramInt2)
    {
        // Byte code:
        //   0: aload_0
        //   1: iload_2
        //   2: putfield 53	com/platform/main/LevelManager:lastStartPosX	I
        //   5: aload_0
        //   6: iload_3
        //   7: putfield 55	com/platform/main/LevelManager:lastStartPosY	I
        //   10: iconst_0
        //   11: istore 4
        //   13: iconst_0
        //   14: istore 5
        //   16: new 67	java/util/ArrayList
        //   19: dup
        //   20: invokespecial 68	java/util/ArrayList:<init>	()V
        //   23: astore 6
        //   25: new 67	java/util/ArrayList
        //   28: dup
        //   29: invokespecial 68	java/util/ArrayList:<init>	()V
        //   32: astore 7
        //   34: new 67	java/util/ArrayList
        //   37: dup
        //   38: invokespecial 68	java/util/ArrayList:<init>	()V
        //   41: pop
        //   42: new 67	java/util/ArrayList
        //   45: dup
        //   46: invokespecial 68	java/util/ArrayList:<init>	()V
        //   49: pop
        //   50: new 67	java/util/ArrayList
        //   53: dup
        //   54: invokespecial 68	java/util/ArrayList:<init>	()V
        //   57: pop
        //   58: aconst_null
        //   59: astore 11
        //   61: aconst_null
        //   62: astore 12
        //   64: aload_0
        //   65: getfield 57	com/platform/main/LevelManager:mActivity	Lcom/platform/main/MainActivity;
        //   68: invokevirtual 74	com/platform/main/MainActivity:getPhysicsWorld	()Lorg/andengine/extension/physics/box2d/PhysicsWorld;
        //   71: ifnull +42 -> 113
        //   74: aload_0
        //   75: getfield 57	com/platform/main/LevelManager:mActivity	Lcom/platform/main/MainActivity;
        //   78: ldc 76
        //   80: invokevirtual 80	com/platform/main/MainActivity:log	(Ljava/lang/String;)V
        //   83: aload_0
        //   84: getfield 57	com/platform/main/LevelManager:mActivity	Lcom/platform/main/MainActivity;
        //   87: invokevirtual 74	com/platform/main/MainActivity:getPhysicsWorld	()Lorg/andengine/extension/physics/box2d/PhysicsWorld;
        //   90: invokevirtual 85	org/andengine/extension/physics/box2d/PhysicsWorld:clearPhysicsConnectors	()V
        //   93: aload_0
        //   94: getfield 57	com/platform/main/LevelManager:mActivity	Lcom/platform/main/MainActivity;
        //   97: invokevirtual 74	com/platform/main/MainActivity:getPhysicsWorld	()Lorg/andengine/extension/physics/box2d/PhysicsWorld;
        //   100: invokevirtual 88	org/andengine/extension/physics/box2d/PhysicsWorld:clearForces	()V
        //   103: aload_0
        //   104: getfield 57	com/platform/main/LevelManager:mActivity	Lcom/platform/main/MainActivity;
        //   107: invokevirtual 74	com/platform/main/MainActivity:getPhysicsWorld	()Lorg/andengine/extension/physics/box2d/PhysicsWorld;
        //   110: invokevirtual 91	org/andengine/extension/physics/box2d/PhysicsWorld:reset	()V
        //   113: aload_0
        //   114: getfield 93	com/platform/main/LevelManager:currentLevel	Lcom/platform/main/Level;
        //   117: ifnull +110 -> 227
        //   120: aload_0
        //   121: getfield 93	com/platform/main/LevelManager:currentLevel	Lcom/platform/main/Level;
        //   124: invokevirtual 99	com/platform/main/Level:getScene	()Lorg/andengine/entity/scene/Scene;
        //   127: ifnull +100 -> 227
        //   130: aload_0
        //   131: getfield 57	com/platform/main/LevelManager:mActivity	Lcom/platform/main/MainActivity;
        //   134: ldc 101
        //   136: invokevirtual 80	com/platform/main/MainActivity:log	(Ljava/lang/String;)V
        //   139: aload_0
        //   140: getfield 93	com/platform/main/LevelManager:currentLevel	Lcom/platform/main/Level;
        //   143: invokevirtual 99	com/platform/main/Level:getScene	()Lorg/andengine/entity/scene/Scene;
        //   146: invokevirtual 106	org/andengine/entity/scene/Scene:clearTouchAreas	()V
        //   149: aload_0
        //   150: getfield 93	com/platform/main/LevelManager:currentLevel	Lcom/platform/main/Level;
        //   153: invokevirtual 99	com/platform/main/Level:getScene	()Lorg/andengine/entity/scene/Scene;
        //   156: invokevirtual 109	org/andengine/entity/scene/Scene:clearEntityModifiers	()V
        //   159: aload_0
        //   160: getfield 93	com/platform/main/LevelManager:currentLevel	Lcom/platform/main/Level;
        //   163: invokevirtual 99	com/platform/main/Level:getScene	()Lorg/andengine/entity/scene/Scene;
        //   166: invokevirtual 112	org/andengine/entity/scene/Scene:clearUpdateHandlers	()V
        //   169: aload_0
        //   170: getfield 93	com/platform/main/LevelManager:currentLevel	Lcom/platform/main/Level;
        //   173: invokevirtual 99	com/platform/main/Level:getScene	()Lorg/andengine/entity/scene/Scene;
        //   176: invokevirtual 115	org/andengine/entity/scene/Scene:clearChildScene	()V
        //   179: aload_0
        //   180: getfield 93	com/platform/main/LevelManager:currentLevel	Lcom/platform/main/Level;
        //   183: invokevirtual 99	com/platform/main/Level:getScene	()Lorg/andengine/entity/scene/Scene;
        //   186: invokevirtual 118	org/andengine/entity/scene/Scene:detachChildren	()V
        //   189: aload_0
        //   190: getfield 93	com/platform/main/LevelManager:currentLevel	Lcom/platform/main/Level;
        //   193: invokevirtual 99	com/platform/main/Level:getScene	()Lorg/andengine/entity/scene/Scene;
        //   196: invokevirtual 119	org/andengine/entity/scene/Scene:reset	()V
        //   199: aload_0
        //   200: getfield 93	com/platform/main/LevelManager:currentLevel	Lcom/platform/main/Level;
        //   203: invokevirtual 123	com/platform/main/Level:getMusic	()Lorg/andengine/audio/music/Music;
        //   206: ifnull +21 -> 227
        //   209: aload_0
        //   210: getfield 93	com/platform/main/LevelManager:currentLevel	Lcom/platform/main/Level;
        //   213: invokevirtual 123	com/platform/main/Level:getMusic	()Lorg/andengine/audio/music/Music;
        //   216: invokevirtual 128	org/andengine/audio/music/Music:stop	()V
        //   219: aload_0
        //   220: getfield 93	com/platform/main/LevelManager:currentLevel	Lcom/platform/main/Level;
        //   223: aconst_null
        //   224: invokevirtual 132	com/platform/main/Level:setMusic	(Lorg/andengine/audio/music/Music;)V
        //   227: aload_0
        //   228: getfield 57	com/platform/main/LevelManager:mActivity	Lcom/platform/main/MainActivity;
        //   231: invokevirtual 136	com/platform/main/MainActivity:getAssets	()Landroid/content/res/AssetManager;
        //   234: new 138	java/lang/StringBuilder
        //   237: dup
        //   238: ldc 140
        //   240: invokespecial 142	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   243: aload_1
        //   244: invokevirtual 146	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   247: ldc 148
        //   249: invokevirtual 146	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   252: invokevirtual 152	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   255: invokevirtual 158	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
        //   258: astore 15
        //   260: new 160	java/io/BufferedReader
        //   263: dup
        //   264: new 162	java/io/InputStreamReader
        //   267: dup
        //   268: aload 15
        //   270: invokespecial 165	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
        //   273: invokespecial 168	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
        //   276: astore 16
        //   278: aconst_null
        //   279: astore 17
        //   281: aconst_null
        //   282: astore 18
        //   284: iconst_0
        //   285: istore 19
        //   287: iconst_0
        //   288: istore 20
        //   290: aload 16
        //   292: invokevirtual 171	java/io/BufferedReader:readLine	()Ljava/lang/String;
        //   295: astore 22
        //   297: aload 22
        //   299: ifnonnull +181 -> 480
        //   302: aload 15
        //   304: invokevirtual 176	java/io/InputStream:close	()V
        //   307: aload 17
        //   309: astore 12
        //   311: aload 18
        //   313: astore 11
        //   315: iload 19
        //   317: istore 5
        //   319: iload 20
        //   321: istore 4
        //   323: new 95	com/platform/main/Level
        //   326: dup
        //   327: iload 4
        //   329: iload 5
        //   331: aload 11
        //   333: aload 12
        //   335: aload 6
        //   337: aload 7
        //   339: aload_0
        //   340: getfield 57	com/platform/main/LevelManager:mActivity	Lcom/platform/main/MainActivity;
        //   343: invokespecial 179	com/platform/main/Level:<init>	(IILjava/lang/String;Ljava/lang/String;Ljava/util/ArrayList;Ljava/util/ArrayList;Lcom/platform/main/MainActivity;)V
        //   346: astore 14
        //   348: aload 14
        //   350: invokevirtual 99	com/platform/main/Level:getScene	()Lorg/andengine/entity/scene/Scene;
        //   353: iconst_1
        //   354: invokevirtual 183	org/andengine/entity/scene/Scene:setTouchAreaBindingOnActionDownEnabled	(Z)V
        //   357: aload 14
        //   359: invokevirtual 99	com/platform/main/Level:getScene	()Lorg/andengine/entity/scene/Scene;
        //   362: aload_0
        //   363: getfield 57	com/platform/main/LevelManager:mActivity	Lcom/platform/main/MainActivity;
        //   366: invokevirtual 187	org/andengine/entity/scene/Scene:registerUpdateHandler	(Lorg/andengine/engine/handler/IUpdateHandler;)V
        //   369: aload 14
        //   371: invokevirtual 99	com/platform/main/Level:getScene	()Lorg/andengine/entity/scene/Scene;
        //   374: aload_0
        //   375: getfield 57	com/platform/main/LevelManager:mActivity	Lcom/platform/main/MainActivity;
        //   378: invokevirtual 74	com/platform/main/MainActivity:getPhysicsWorld	()Lorg/andengine/extension/physics/box2d/PhysicsWorld;
        //   381: invokevirtual 187	org/andengine/entity/scene/Scene:registerUpdateHandler	(Lorg/andengine/engine/handler/IUpdateHandler;)V
        //   384: aload_0
        //   385: getfield 57	com/platform/main/LevelManager:mActivity	Lcom/platform/main/MainActivity;
        //   388: invokevirtual 191	com/platform/main/MainActivity:getEngine	()Lorg/andengine/engine/Engine;
        //   391: aload 14
        //   393: invokevirtual 99	com/platform/main/Level:getScene	()Lorg/andengine/entity/scene/Scene;
        //   396: invokevirtual 197	org/andengine/engine/Engine:setScene	(Lorg/andengine/entity/scene/Scene;)V
        //   399: aload_0
        //   400: aload 14
        //   402: putfield 93	com/platform/main/LevelManager:currentLevel	Lcom/platform/main/Level;
        //   405: aload_0
        //   406: getfield 57	com/platform/main/LevelManager:mActivity	Lcom/platform/main/MainActivity;
        //   409: invokevirtual 201	com/platform/main/MainActivity:getThePlayer	()Lcom/platform/main/Player;
        //   412: ifnull +21 -> 433
        //   415: aload_0
        //   416: getfield 57	com/platform/main/LevelManager:mActivity	Lcom/platform/main/MainActivity;
        //   419: invokevirtual 201	com/platform/main/MainActivity:getThePlayer	()Lcom/platform/main/Player;
        //   422: aload_0
        //   423: getfield 53	com/platform/main/LevelManager:lastStartPosX	I
        //   426: aload_0
        //   427: getfield 55	com/platform/main/LevelManager:lastStartPosY	I
        //   430: invokevirtual 207	com/platform/main/Player:reload	(II)V
        //   433: aload_0
        //   434: getfield 57	com/platform/main/LevelManager:mActivity	Lcom/platform/main/MainActivity;
        //   437: new 209	org/andengine/extension/debugdraw/DebugRenderer
        //   440: dup
        //   441: aload_0
        //   442: getfield 57	com/platform/main/LevelManager:mActivity	Lcom/platform/main/MainActivity;
        //   445: invokevirtual 74	com/platform/main/MainActivity:getPhysicsWorld	()Lorg/andengine/extension/physics/box2d/PhysicsWorld;
        //   448: aload_0
        //   449: getfield 57	com/platform/main/LevelManager:mActivity	Lcom/platform/main/MainActivity;
        //   452: invokevirtual 213	com/platform/main/MainActivity:getVertexBufferObjectManager	()Lorg/andengine/opengl/vbo/VertexBufferObjectManager;
        //   455: invokespecial 216	org/andengine/extension/debugdraw/DebugRenderer:<init>	(Lorg/andengine/extension/physics/box2d/PhysicsWorld;Lorg/andengine/opengl/vbo/VertexBufferObjectManager;)V
        //   458: invokevirtual 220	com/platform/main/MainActivity:setDebug	(Lorg/andengine/extension/debugdraw/DebugRenderer;)V
        //   461: aload 14
        //   463: invokevirtual 99	com/platform/main/Level:getScene	()Lorg/andengine/entity/scene/Scene;
        //   466: aload_0
        //   467: getfield 57	com/platform/main/LevelManager:mActivity	Lcom/platform/main/MainActivity;
        //   470: invokevirtual 224	com/platform/main/MainActivity:getDebug	()Lorg/andengine/extension/debugdraw/DebugRenderer;
        //   473: invokevirtual 228	org/andengine/entity/scene/Scene:attachChild	(Lorg/andengine/entity/IEntity;)V
        //   476: invokestatic 233	java/lang/System:gc	()V
        //   479: return
        //   480: aload 22
        //   482: ldc 235
        //   484: invokevirtual 241	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
        //   487: astore 23
        //   489: aload 23
        //   491: iconst_0
        //   492: aaload
        //   493: getstatic 247	java/util/Locale:UK	Ljava/util/Locale;
        //   496: invokevirtual 251	java/lang/String:toLowerCase	(Ljava/util/Locale;)Ljava/lang/String;
        //   499: ldc 35
        //   501: invokevirtual 255	java/lang/String:equals	(Ljava/lang/Object;)Z
        //   504: ifeq +65 -> 569
        //   507: aload 23
        //   509: iconst_3
        //   510: aaload
        //   511: invokestatic 261	java/lang/Integer:parseInt	(Ljava/lang/String;)I
        //   514: istore 28
        //   516: iload 28
        //   518: istore 4
        //   520: aload 23
        //   522: iconst_4
        //   523: aaload
        //   524: invokestatic 261	java/lang/Integer:parseInt	(Ljava/lang/String;)I
        //   527: istore 29
        //   529: iload 29
        //   531: istore 5
        //   533: aload 23
        //   535: iconst_5
        //   536: aaload
        //   537: pop
        //   538: aload 23
        //   540: iconst_1
        //   541: aaload
        //   542: astore 11
        //   544: aload 23
        //   546: iconst_2
        //   547: aaload
        //   548: astore 31
        //   550: aload 31
        //   552: astore 17
        //   554: aload 11
        //   556: astore 18
        //   558: iload 5
        //   560: istore 19
        //   562: iload 4
        //   564: istore 20
        //   566: goto -276 -> 290
        //   569: aload 23
        //   571: iconst_0
        //   572: aaload
        //   573: getstatic 247	java/util/Locale:UK	Ljava/util/Locale;
        //   576: invokevirtual 251	java/lang/String:toLowerCase	(Ljava/util/Locale;)Ljava/lang/String;
        //   579: ldc 11
        //   581: invokevirtual 255	java/lang/String:equals	(Ljava/lang/Object;)Z
        //   584: ifeq +148 -> 732
        //   587: aload 6
        //   589: new 263	com/platform/main/Doorway
        //   592: dup
        //   593: aload 23
        //   595: iconst_1
        //   596: aaload
        //   597: invokestatic 269	java/lang/Float:parseFloat	(Ljava/lang/String;)F
        //   600: aload 23
        //   602: iconst_2
        //   603: aaload
        //   604: invokestatic 269	java/lang/Float:parseFloat	(Ljava/lang/String;)F
        //   607: aload 23
        //   609: iconst_3
        //   610: aaload
        //   611: invokestatic 269	java/lang/Float:parseFloat	(Ljava/lang/String;)F
        //   614: aload 23
        //   616: iconst_4
        //   617: aaload
        //   618: invokestatic 269	java/lang/Float:parseFloat	(Ljava/lang/String;)F
        //   621: aload 23
        //   623: iconst_5
        //   624: aaload
        //   625: aload 23
        //   627: bipush 6
        //   629: aaload
        //   630: invokestatic 261	java/lang/Integer:parseInt	(Ljava/lang/String;)I
        //   633: aload 23
        //   635: bipush 7
        //   637: aaload
        //   638: invokestatic 261	java/lang/Integer:parseInt	(Ljava/lang/String;)I
        //   641: aload_0
        //   642: getfield 57	com/platform/main/LevelManager:mActivity	Lcom/platform/main/MainActivity;
        //   645: invokespecial 272	com/platform/main/Doorway:<init>	(FFFFLjava/lang/String;IILcom/platform/main/MainActivity;)V
        //   648: invokevirtual 275	java/util/ArrayList:add	(Ljava/lang/Object;)Z
        //   651: pop
        //   652: goto -362 -> 290
        //   655: astore 21
        //   657: aload 17
        //   659: astore 12
        //   661: aload 18
        //   663: astore 11
        //   665: iload 19
        //   667: istore 5
        //   669: iload 20
        //   671: istore 4
        //   673: aload 21
        //   675: invokevirtual 278	java/lang/Exception:printStackTrace	()V
        //   678: aload_0
        //   679: getfield 57	com/platform/main/LevelManager:mActivity	Lcom/platform/main/MainActivity;
        //   682: ldc_w 280
        //   685: invokevirtual 283	com/platform/main/MainActivity:gameToast	(Ljava/lang/String;)V
        //   688: goto -365 -> 323
        //   691: astore 13
        //   693: aload_0
        //   694: getfield 57	com/platform/main/LevelManager:mActivity	Lcom/platform/main/MainActivity;
        //   697: aload 13
        //   699: invokevirtual 284	java/lang/Exception:toString	()Ljava/lang/String;
        //   702: invokevirtual 80	com/platform/main/MainActivity:log	(Ljava/lang/String;)V
        //   705: aload_0
        //   706: getfield 57	com/platform/main/LevelManager:mActivity	Lcom/platform/main/MainActivity;
        //   709: new 138	java/lang/StringBuilder
        //   712: dup
        //   713: ldc_w 286
        //   716: invokespecial 142	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   719: aload_1
        //   720: invokevirtual 146	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   723: invokevirtual 152	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   726: invokevirtual 283	com/platform/main/MainActivity:gameToast	(Ljava/lang/String;)V
        //   729: goto -406 -> 323
        //   732: aload 23
        //   734: iconst_0
        //   735: aaload
        //   736: getstatic 247	java/util/Locale:UK	Ljava/util/Locale;
        //   739: invokevirtual 251	java/lang/String:toLowerCase	(Ljava/util/Locale;)Ljava/lang/String;
        //   742: ldc 8
        //   744: invokevirtual 255	java/lang/String:equals	(Ljava/lang/Object;)Z
        //   747: ifeq +51 -> 798
        //   750: aload 6
        //   752: new 288	com/platform/main/ClippingPlatform
        //   755: dup
        //   756: aload 23
        //   758: iconst_1
        //   759: aaload
        //   760: invokestatic 269	java/lang/Float:parseFloat	(Ljava/lang/String;)F
        //   763: aload 23
        //   765: iconst_2
        //   766: aaload
        //   767: invokestatic 269	java/lang/Float:parseFloat	(Ljava/lang/String;)F
        //   770: aload 23
        //   772: iconst_3
        //   773: aaload
        //   774: invokestatic 269	java/lang/Float:parseFloat	(Ljava/lang/String;)F
        //   777: aload 23
        //   779: iconst_4
        //   780: aaload
        //   781: invokestatic 269	java/lang/Float:parseFloat	(Ljava/lang/String;)F
        //   784: aload_0
        //   785: getfield 57	com/platform/main/LevelManager:mActivity	Lcom/platform/main/MainActivity;
        //   788: invokespecial 291	com/platform/main/ClippingPlatform:<init>	(FFFFLcom/platform/main/MainActivity;)V
        //   791: invokevirtual 275	java/util/ArrayList:add	(Ljava/lang/Object;)Z
        //   794: pop
        //   795: goto -505 -> 290
        //   798: aload 23
        //   800: iconst_0
        //   801: aaload
        //   802: getstatic 247	java/util/Locale:UK	Ljava/util/Locale;
        //   805: invokevirtual 251	java/lang/String:toLowerCase	(Ljava/util/Locale;)Ljava/lang/String;
        //   808: ldc 20
        //   810: invokevirtual 255	java/lang/String:equals	(Ljava/lang/Object;)Z
        //   813: ifeq +51 -> 864
        //   816: aload 6
        //   818: new 293	com/platform/main/Ladder
        //   821: dup
        //   822: aload 23
        //   824: iconst_1
        //   825: aaload
        //   826: invokestatic 269	java/lang/Float:parseFloat	(Ljava/lang/String;)F
        //   829: aload 23
        //   831: iconst_2
        //   832: aaload
        //   833: invokestatic 269	java/lang/Float:parseFloat	(Ljava/lang/String;)F
        //   836: aload 23
        //   838: iconst_3
        //   839: aaload
        //   840: invokestatic 269	java/lang/Float:parseFloat	(Ljava/lang/String;)F
        //   843: aload 23
        //   845: iconst_4
        //   846: aaload
        //   847: invokestatic 269	java/lang/Float:parseFloat	(Ljava/lang/String;)F
        //   850: aload_0
        //   851: getfield 57	com/platform/main/LevelManager:mActivity	Lcom/platform/main/MainActivity;
        //   854: invokespecial 294	com/platform/main/Ladder:<init>	(FFFFLcom/platform/main/MainActivity;)V
        //   857: invokevirtual 275	java/util/ArrayList:add	(Ljava/lang/Object;)Z
        //   860: pop
        //   861: goto -571 -> 290
        //   864: aload 23
        //   866: iconst_0
        //   867: aaload
        //   868: getstatic 247	java/util/Locale:UK	Ljava/util/Locale;
        //   871: invokevirtual 251	java/lang/String:toLowerCase	(Ljava/util/Locale;)Ljava/lang/String;
        //   874: ldc 14
        //   876: invokevirtual 255	java/lang/String:equals	(Ljava/lang/Object;)Z
        //   879: ifeq +41 -> 920
        //   882: aload 7
        //   884: new 296	com/platform/main/Enemy
        //   887: dup
        //   888: aload 23
        //   890: iconst_2
        //   891: aaload
        //   892: invokestatic 269	java/lang/Float:parseFloat	(Ljava/lang/String;)F
        //   895: aload 23
        //   897: iconst_3
        //   898: aaload
        //   899: invokestatic 269	java/lang/Float:parseFloat	(Ljava/lang/String;)F
        //   902: aload 23
        //   904: iconst_1
        //   905: aaload
        //   906: aload_0
        //   907: getfield 57	com/platform/main/LevelManager:mActivity	Lcom/platform/main/MainActivity;
        //   910: invokespecial 299	com/platform/main/Enemy:<init>	(FFLjava/lang/String;Lcom/platform/main/MainActivity;)V
        //   913: invokevirtual 275	java/util/ArrayList:add	(Ljava/lang/Object;)Z
        //   916: pop
        //   917: goto -627 -> 290
        //   920: aload 23
        //   922: iconst_0
        //   923: aaload
        //   924: getstatic 247	java/util/Locale:UK	Ljava/util/Locale;
        //   927: invokevirtual 251	java/lang/String:toLowerCase	(Ljava/util/Locale;)Ljava/lang/String;
        //   930: ldc 23
        //   932: invokevirtual 255	java/lang/String:equals	(Ljava/lang/Object;)Z
        //   935: ifne -645 -> 290
        //   938: aload 23
        //   940: iconst_0
        //   941: aaload
        //   942: getstatic 247	java/util/Locale:UK	Ljava/util/Locale;
        //   945: invokevirtual 251	java/lang/String:toLowerCase	(Ljava/util/Locale;)Ljava/lang/String;
        //   948: ldc 17
        //   950: invokevirtual 255	java/lang/String:equals	(Ljava/lang/Object;)Z
        //   953: ifne -663 -> 290
        //   956: aload_0
        //   957: getfield 57	com/platform/main/LevelManager:mActivity	Lcom/platform/main/MainActivity;
        //   960: new 138	java/lang/StringBuilder
        //   963: dup
        //   964: ldc_w 301
        //   967: invokespecial 142	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   970: aload 23
        //   972: iconst_0
        //   973: aaload
        //   974: invokevirtual 146	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   977: invokevirtual 152	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   980: invokevirtual 283	com/platform/main/MainActivity:gameToast	(Ljava/lang/String;)V
        //   983: goto -693 -> 290
        //   986: astore 21
        //   988: iconst_0
        //   989: istore 4
        //   991: iconst_0
        //   992: istore 5
        //   994: aconst_null
        //   995: astore 11
        //   997: aconst_null
        //   998: astore 12
        //   1000: goto -327 -> 673
        //   1003: astore 21
        //   1005: aload 17
        //   1007: astore 12
        //   1009: aload 18
        //   1011: astore 11
        //   1013: iload 19
        //   1015: istore 5
        //   1017: goto -344 -> 673
        //   1020: astore 21
        //   1022: aload 17
        //   1024: astore 12
        //   1026: aload 18
        //   1028: astore 11
        //   1030: goto -357 -> 673
        //   1033: astore 21
        //   1035: aload 17
        //   1037: astore 12
        //   1039: goto -366 -> 673
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	1042	0	this	LevelManager
        //   0	1042	1	paramString	String
        //   0	1042	2	paramInt1	int
        //   0	1042	3	paramInt2	int
        //   11	979	4	i	int
        //   14	1002	5	j	int
        //   23	794	6	localArrayList1	ArrayList
        //   32	851	7	localArrayList2	ArrayList
        //   59	970	11	localObject1	Object
        //   62	976	12	localObject2	Object
        //   691	7	13	localException1	java.lang.Exception
        //   346	116	14	localLevel	Level
        //   258	45	15	localInputStream	java.io.InputStream
        //   276	15	16	localBufferedReader	java.io.BufferedReader
        //   279	757	17	localObject3	Object
        //   282	745	18	localObject4	Object
        //   285	729	19	k	int
        //   288	382	20	m	int
        //   655	19	21	localException2	java.lang.Exception
        //   986	1	21	localException3	java.lang.Exception
        //   1003	1	21	localException4	java.lang.Exception
        //   1020	1	21	localException5	java.lang.Exception
        //   1033	1	21	localException6	java.lang.Exception
        //   295	186	22	str1	String
        //   487	484	23	arrayOfString	String[]
        //   514	3	28	n	int
        //   527	3	29	i1	int
        //   548	3	31	str2	String
        // Exception table:
        //   from	to	target	type
        //   290	297	655	java/lang/Exception
        //   302	307	655	java/lang/Exception
        //   480	516	655	java/lang/Exception
        //   569	652	655	java/lang/Exception
        //   732	795	655	java/lang/Exception
        //   798	861	655	java/lang/Exception
        //   864	917	655	java/lang/Exception
        //   920	983	655	java/lang/Exception
        //   227	260	691	java/lang/Exception
        //   673	688	691	java/lang/Exception
        //   260	278	986	java/lang/Exception
        //   520	529	1003	java/lang/Exception
        //   533	544	1020	java/lang/Exception
        //   544	550	1033	java/lang/Exception
    }

    public Level getLevel()
    {
        return this.currentLevel;
    }

    public Scene getScene()
    {
        return this.currentLevel.getScene();
    }

    public void scheduleLoadLevel(String paramString, int paramInt1, int paramInt2)
    {
        this.scheduledDestination = paramString;
        this.scheduledDestinationX = paramInt1;
        this.scheduledDestinationY = paramInt2;
    }

    public void updateLevel()
    {
        if (this.scheduledDestination != null)
        {
            LoadLevel(this.scheduledDestination, this.scheduledDestinationX, this.scheduledDestinationY);
            this.scheduledDestination = null;
            return;
        }
        for(Enemy e : getLevel().getEnemies())
        {
            e.updatePosition();
        }
        for(Enemy e : getLevel().getEnemiesNeedRemoving())
        {
            e.setActive(false);
        }
        getLevel().getEnemiesNeedRemoving().clear();
    }
}
