package com.platform.main.GameResources;

import com.platform.main.GameManager;
import com.platform.main.GameResources.Level.GameLevel;
import com.platform.main.GameResources.Level.Level;
import com.platform.main.GameResources.LevelObjects.AnimatedObjects.MoveableObjects.Enemy;
import com.platform.main.GameResources.LevelObjects.AnimatedObjects.MoveableObjects.Frog;
import com.platform.main.GameResources.LevelObjects.BodyObject;
import com.platform.main.GameResources.LevelObjects.GameObject;
import com.platform.main.GameResources.LevelObjects.Interactions.Collectable;
import com.platform.main.GameResources.LevelObjects.Interactions.Doorway;
import com.platform.main.GameResources.LevelObjects.Interactions.Ladder;
import com.platform.main.GameResources.LevelObjects.Interactions.Lemon;
import com.platform.main.GameResources.LevelObjects.Platforms.AbsoluteSolidClippingPlatform;
import com.platform.main.GameResources.LevelObjects.Platforms.ClippingPlatform;
import com.platform.main.GameResources.LevelObjects.Platforms.RectangularPlatform;
import com.platform.main.GameResources.LevelObjects.Platforms.SolidClippingPlatform;
import com.platform.main.GameResources.LevelObjects.StaticObject.Background;
import com.platform.main.GameResources.LevelObjects.StaticObject.ParallaxBackground;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
*   JsonLoader Used to load levels/content in json format
*
 */
public class JsonLoader {
    GameManager gameManager;
    public JsonLoader(GameManager gameManager)
    {
        this.gameManager = gameManager;
    }

    /*
    *   loadLevel used to load in levels
    *
    *   @param levelName the level string to load levels/<levelName>.json
    *   @returns the level object which has not been finalised
     */
    public Level loadLevel(String levelName)
    {
        InputStream content = null;
        GameLevel tempLevel = new GameLevel(gameManager);

        try {
            content = gameManager.getMainActivity().getAssets().open("levels/"+levelName+".json");
        } catch (IOException e) {
            gameManager.getMainActivity().log("Failed to load in the level : " + levelName);
            e.printStackTrace();
        }

        /*
            Use string builder to load in the content
         */
        BufferedReader bReader = new BufferedReader(new InputStreamReader(content));
        String line;
        StringBuilder builder = new StringBuilder();
        try {
            while ((line = bReader.readLine()) != null) {

                builder.append(line);
            }
        } catch (IOException e) {
            gameManager.getMainActivity().log("Failed loading in the level : " + levelName);
            e.printStackTrace();
        }
        String result = builder.toString();


        /*
            Create the String result into a JSON Object
         */
        JSONObject jObject = null;
        try {
            jObject = new JSONObject(result);
        } catch (JSONException e) {
            gameManager.getMainActivity().log("Failed parsing into json level : " + levelName);
            e.printStackTrace();
        }


        /*
            Process the JSON Object
         */
        if (jObject != null) {
            this.processSetup(tempLevel,jObject);
            this.processBatch("backgrounds", tempLevel, jObject);
            this.processBatch("parallax",tempLevel,jObject);
            this.processBatch("platforms-cloud",tempLevel,jObject);
            this.processBatch("platforms-absolute",tempLevel,jObject);
            this.processBatch("platforms-solid",tempLevel,jObject);
            this.processBatch("doorways",tempLevel,jObject);
            this.processBatch("enemies",tempLevel,jObject);
            this.processBatch("collectables",tempLevel,jObject);
            this.processBatch("ladders",tempLevel,jObject);
            /*this.processBackgrounds(tempLevel, jObject, true);
            this.processBackgrounds(tempLevel, jObject, false);
            this.processPlatforms(tempLevel,jObject,"cloud");
            this.processPlatforms(tempLevel,jObject,"solid");
            this.processDoorways(tempLevel,jObject);
            this.processEnemies(tempLevel,jObject);
            this.processCollectables(tempLevel,jObject);
            processLadders(tempLevel, jObject);*/

        }
        return tempLevel;
    }

    /*********************************************************************************************/
    /* PROCESS BACKGROUNDS */
    /*********************************************************************************************/
    private void processSetup(GameLevel tempLevel,JSONObject jObject)
    {
        JSONObject objectsList = null;
        try {
            objectsList = jObject.getJSONObject("setup");
        } catch (JSONException e) {
            gameManager.getMainActivity().log("Failed parsing into setup");
            e.printStackTrace();
            objectsList = null;
        }

        /*
            WHILE i = 0 (each record is stored as an array [0], [1], etc
         */
        if(objectsList != null)
        {
            /*
            Loop through each ladder
             */
            int i = 0;
            while(i >= 0)
            {
                try
                {
                    //GET THE CURRENT LADDER
                    JSONObject currentObject = objectsList.getJSONObject(String.valueOf(i));

                    //GET ASSOSIATED ARRAYS
                    JSONArray currentObjectNames = currentObject.names();
                    JSONArray currentObjectValues = currentObject.toJSONArray(currentObjectNames);

                    //GET THE ARRAY KEYS
                    String currentKey = "";
                    String currentValue = "";
                    for(int c = 0 ; c < currentObjectValues.length(); c++){
                        //SET THIS LOOPS VARIABLES
                        currentKey = currentObjectNames.getString(c);
                        currentValue = currentObjectValues.getString(c);

                        //CHECK WHAT WE ARE TRYING TO SET
                        if(currentKey.equals("width")){
                            tempLevel.setWidth(Integer.parseInt(currentValue)); // WIDTH
                        }
                        else if(currentKey.equals("height"))
                        {
                            tempLevel.setHeight((Integer.parseInt(currentValue))); // HEIGHT
                        }
                    }
                    i++;
                }
                catch (Exception e)
                {
                    /*
                        This happens when [i] is not found. There may be more entries if for some reason it is not consistent : [1],[3],[6].
                        Break out of the loop
                     */
                    break;
                }
            }
        }
    }


    /*********************************************************************************************/
    /* PROCESS COLLECTABLES */
    /*********************************************************************************************/
    private void processBatch(String whatToProcess, GameLevel tempLevel, JSONObject jObject)
    {
        JSONObject objectsList = null;
        try {
            objectsList = jObject.getJSONObject(whatToProcess);
        } catch (JSONException e) {
            gameManager.getMainActivity().log("Failed parsing into "+whatToProcess);
            e.printStackTrace();
            objectsList = null;
        }

        /*
            WHILE i = 0 (each record is stored as an array [0], [1], etc
         */
        if(objectsList != null)
        {
            /*********************************************************************************************/
            /* Loop through each ladder */
            /*********************************************************************************************/
            JSONArray theNames = objectsList.names();

            //if the names dont exist or are empty
            if(theNames == null)
            {
               return;
            }

            for (int i = 0; i < theNames.length(); i++)
            {
                String currentParentKey;
                try {
                    currentParentKey = theNames.getString(i);
                } catch (JSONException e) {
                    this.gameManager.getMainActivity().log("Could not load one of the "+whatToProcess);
                    e.printStackTrace();
                    continue;
                }
                /*********************************************************************************************/
                /* CHECK WAHT OBJECT TO USE */
                /*********************************************************************************************/
                GameObject newObject = null;
                try {
                    if(whatToProcess.equals("parallax"))
                    {
                        newObject = new ParallaxBackground(gameManager);
                    }
                    else if(whatToProcess.equals("backgrounds"))
                    {
                        newObject = new Background(gameManager);
                    }
                    else if(whatToProcess.equals("platforms-cloud"))
                    {
                        newObject = new ClippingPlatform(gameManager);
                    }
                    else if(whatToProcess.equals("platforms-solid"))
                    {
                        newObject = new SolidClippingPlatform(gameManager);
                    }
                    else if(whatToProcess.equals("platforms-absolute"))
                    {
                        newObject = new AbsoluteSolidClippingPlatform(gameManager);
                    }
                    else if(whatToProcess.equals("doorways"))
                    {
                        newObject = new Doorway(gameManager);
                    }
                    else if(whatToProcess.equals("ladders"))
                    {
                        newObject = new Ladder(gameManager);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                /*********************************************************************************************/
                /* STEP THROUGH EACH VALUE */
                /*********************************************************************************************/
                try
                {
                    //GET THE CURRENT OBJECT
                    JSONObject currentObject = objectsList.getJSONObject(currentParentKey);

                    //GET ASSOSIATED ARRAYS
                    JSONArray currentObjectNames = currentObject.names();
                    JSONArray currentObjectValues = currentObject.toJSONArray(currentObjectNames);

                    //GET THE ARRAY KEYS
                    String currentKey = "";
                    String currentValue = "";
                    for(int c = 0 ; c < currentObjectValues.length(); c++){
                        //SET THIS LOOPS VARIABLES
                        currentKey = currentObjectNames.getString(c);
                        currentValue = currentObjectValues.getString(c);
                        boolean specificValue = true;

                        if(whatToProcess.equals("parallax"))
                        {
                            specificValue = this.checkParallax(((Background)newObject), currentKey, currentValue);
                        }
                        else if(whatToProcess.equals("backgrounds"))
                        {
                            specificValue = this.checkBackgrounds(((Background)newObject), currentKey, currentValue);
                        }
                        else if(whatToProcess.equals("platforms-cloud"))
                        {
                            specificValue = this.checkPlatformClouds(((RectangularPlatform)newObject), currentKey, currentValue);
                        }
                        else if(whatToProcess.equals("platforms-solid"))
                        {
                            specificValue = this.checkPlatformSolids(((RectangularPlatform)newObject), currentKey, currentValue);
                        }
                        else if(whatToProcess.equals("platforms-absolute"))
                        {
                            specificValue = this.checkAbsolutePlatformSolids(((RectangularPlatform)newObject), currentKey, currentValue);
                        }
                        else if(whatToProcess.equals("doorways"))
                        {
                            specificValue = this.checkDoorways(((Doorway)newObject), currentKey, currentValue);
                        }
                        else if(whatToProcess.equals("ladders"))
                        {
                            specificValue = this.checkLadders(((Ladder)newObject), currentKey, currentValue);
                        }
                        else if(whatToProcess.equals("collectables"))
                        {
                            if(currentKey.equals("type"))
                            {
                                if(currentValue.equals("lemon"))
                                {
                                    newObject = new Lemon(gameManager);
                                }
                            }
                            else
                            {
                                specificValue = this.checkCollectables(((Collectable)newObject), currentKey, currentValue);
                            }
                        }
                        else if(whatToProcess.equals("enemies"))
                        {
                            if(currentKey.equals("type"))
                            {
                                if(currentValue.equals("frog"))
                                {
                                    newObject = new Frog(gameManager);
                                }
                            }
                            else
                            {
                                specificValue = this.checkEnemies(((Enemy)newObject), currentKey, currentValue);
                            }
                        }

                        if(specificValue == false)
                        {
                            this.checkDefaults(newObject,currentKey,currentValue);
                        }
                    }
                }
                catch (Exception e)
                {
                    continue;
                }
                tempLevel.addGameObject(newObject);
            }
        }
    }

    private boolean checkEnemies(Enemy newObject, String currentKey, String currentValue) {
        if(currentKey.equals("polygonShape"))
        {
            try {
                ArrayList<Float> list = new ArrayList<Float>();
                JSONArray jsonArray = new JSONArray(currentValue);
                if (jsonArray != null) {
                    int len = jsonArray.length();
                    for (int i=0;i<len;i++){
                        list.add(Float.parseFloat(jsonArray.get(i).toString()));
                    }
                }
                newObject.setPolygon(list);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            return false;
        }
        return true;
    }

    private boolean checkLadders(Ladder newObject, String currentKey, String currentValue) {
        return false;
    }

    private boolean checkDoorways(Doorway newObject, String currentKey, String currentValue) {
        if(currentKey.equals("dest"))
        {
            newObject.setDestination(currentValue); //DESTINATION
        }
        else if(currentKey.equals("destX"))
        {
            newObject.setDestinationX(Integer.parseInt(currentValue)); //DESTINATION X
        }
        else if(currentKey.equals("destY"))
        {
            newObject.setDestinationY(Integer.parseInt(currentValue)); //DESTINATION Y
        }
        else
        {
            return false;
        }
        return true;
    }

    private boolean checkAbsolutePlatformSolids( RectangularPlatform newObject, String currentKey, String currentValue) {
        return false;
    }

    private boolean checkPlatformSolids( RectangularPlatform newObject, String currentKey, String currentValue) {
        return false;
    }

    private boolean checkPlatformClouds(RectangularPlatform newObject, String currentKey, String currentValue) {
        return false;
    }

    private boolean checkBackgrounds(Background newObject, String currentKey, String currentValue) {
        if(currentKey.equals("image"))
        {
            newObject.setImage(currentValue); // IMAGE
        }
        else if(currentKey.equals("zindex"))
        {
            newObject.setZIndex(Integer.parseInt(currentValue)); // Z INDEX
        }
        else if(currentKey.equals("parallax-offset") && newObject instanceof ParallaxBackground)
        {
            ((ParallaxBackground)newObject).setParallaxOffset(Integer.parseInt(currentValue)); // Z INDEX
        }
        else if(currentKey.equals("parallax-speed") && newObject instanceof ParallaxBackground)
        {
            ((ParallaxBackground)newObject).setParallaxSpeed(Float.parseFloat(currentValue)); // Z INDEX
        }
        else
        {
            return false;
        }
        return true;
    }

    private boolean checkParallax(Background newObject, String currentKey, String currentValue) {
        if(currentKey.equals("image"))
        {
            newObject.setImage(currentValue); // IMAGE
        }
        else if(currentKey.equals("zindex"))
        {
            newObject.setZIndex(Integer.parseInt(currentValue)); // Z INDEX
        }
        else if(currentKey.equals("parallax-offset") && newObject instanceof ParallaxBackground)
        {
            ((ParallaxBackground)newObject).setParallaxOffset(Integer.parseInt(currentValue)); // Z INDEX
        }
        else if(currentKey.equals("parallax-speed") && newObject instanceof ParallaxBackground)
        {
            ((ParallaxBackground)newObject).setParallaxSpeed(Float.parseFloat(currentValue)); // Z INDEX
        }
        else
        {
            return false;
        }
        return true;
    }

    private boolean checkCollectables(Collectable newObject, String currentKey, String currentValue) {
        return false;
    }

    public void checkDefaults(GameObject b, String currentKey, String currentValue)
    {
        if(currentKey.equals("x")){
            b.setX(Integer.parseInt(currentValue)); // X
        }
        else if(currentKey.equals("y")){
            b.setY(Integer.parseInt(currentValue)); // Y
        }
        else  if(currentKey.equals("width")){
            b.setWidth(Integer.parseInt(currentValue)); // WIDTH
        }
        else if(currentKey.equals("height"))
        {
            b.setHeight((Integer.parseInt(currentValue))); // HEIGHT
        }
    }
}