package com.platform.main.GameResources;

import com.platform.main.GameManager;
import com.platform.main.GameResources.Level.GameLevel;
import com.platform.main.GameResources.Level.Level;
import com.platform.main.GameResources.LevelObjects.AnimatedObjects.MoveableObjects.Frog;
import com.platform.main.GameResources.LevelObjects.GameObject;
import com.platform.main.GameResources.LevelObjects.Interactions.Doorway;
import com.platform.main.GameResources.LevelObjects.Interactions.Ladder;
import com.platform.main.GameResources.LevelObjects.Platforms.ClippingPlatform;
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
            this.processBackgrounds(tempLevel, jObject, true);
            this.processBackgrounds(tempLevel, jObject, false);
            this.processPlatforms(tempLevel,jObject,"cloud");
            this.processPlatforms(tempLevel,jObject,"solid");
            this.processDoorways(tempLevel,jObject);
            this.processEnemies(tempLevel,jObject);
            processLadders(tempLevel, jObject);

        }
        return tempLevel;
    }

    /*********************************************************************************************/
    /* PROCESS PlATFORMS */
    /*********************************************************************************************/
    private void processPlatforms(GameLevel tempLevel,JSONObject jObject,String type)
    {
        JSONObject objectsList = null;
        try {
            objectsList = jObject.getJSONObject("platforms-"+type);
        } catch (JSONException e) {
            gameManager.getMainActivity().log("Failed parsing into platforms");
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
                //Create new ladder object
                GameObject newObject = null;
                if(type.equals("solid")) {
                    newObject = new SolidClippingPlatform(gameManager);
                } else if(type.equals("cloud")) {
                    newObject = new ClippingPlatform(gameManager);
                }
                else
                {
                    this.gameManager.getMainActivity().gameToast("Failed loading in platforms-"+type);
                    return;
                }

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
                        if(currentKey.equals("x")){
                            newObject.setX(Integer.parseInt(currentValue)); // X
                        }
                        else if(currentKey.equals("y")){
                            newObject.setY(Integer.parseInt(currentValue)); // Y
                        }
                        else  if(currentKey.equals("width")){
                            newObject.setWidth(Integer.parseInt(currentValue)); // WIDTH
                        }
                        else if(currentKey.equals("height"))
                        {
                            newObject.setHeight((Integer.parseInt(currentValue))); // HEIGHT
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
                tempLevel.addGameObject(newObject);
            }
        }
    }

    /*********************************************************************************************/
    /* PROCESS PlATFORMS */
    /*********************************************************************************************/
    private void processPolygons(GameLevel tempLevel,JSONObject jObject)
    {
        JSONObject objectsList = null;
        try {
            objectsList = jObject.getJSONObject("polygons");
        } catch (JSONException e) {
            gameManager.getMainActivity().log("Failed parsing into platforms");
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
                //Create new ladder object
                GameObject newObject = null;
                newObject = new ClippingPlatform(gameManager);

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
                        if(currentKey.equals("x")){
                            newObject.setX(Integer.parseInt(currentValue)); // X
                        }
                        else if(currentKey.equals("y")){
                            newObject.setY(Integer.parseInt(currentValue)); // Y
                        }
                        else  if(currentKey.equals("width")){
                            newObject.setWidth(Integer.parseInt(currentValue)); // WIDTH
                        }
                        else if(currentKey.equals("height"))
                        {
                            newObject.setHeight((Integer.parseInt(currentValue))); // HEIGHT
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
                tempLevel.addGameObject(newObject);
            }
        }
    }

    /*********************************************************************************************/
    /* PROCESS PlATFORMS */
    /*********************************************************************************************/
    private void processDoorways(GameLevel tempLevel,JSONObject jObject)
    {
        JSONObject objectsList = null;
        try {
            objectsList = jObject.getJSONObject("doorways");
        } catch (JSONException e) {
            gameManager.getMainActivity().log("Failed parsing into platforms");
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
                //Create new ladder object
                Doorway newObject = new Doorway(gameManager);

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
                        if(currentKey.equals("x")){
                            newObject.setX(Integer.parseInt(currentValue)); // X
                        }
                        else if(currentKey.equals("y")){
                            newObject.setY(Integer.parseInt(currentValue)); // Y
                        }
                        else  if(currentKey.equals("width")){
                            newObject.setWidth(Integer.parseInt(currentValue)); // WIDTH
                        }
                        else if(currentKey.equals("height"))
                        {
                            newObject.setHeight((Integer.parseInt(currentValue))); // HEIGHT
                        }
                        else if(currentKey.equals("dest"))
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
                tempLevel.addGameObject(newObject);
            }
        }
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
    /* PROCESS BACKGROUNDS */
    /*********************************************************************************************/
    private void processBackgrounds(GameLevel tempLevel,JSONObject jObject, boolean parallax)
    {
        JSONObject objectsList = null;
        try {
            if(parallax)
            {
                objectsList = jObject.getJSONObject("parallax");
            }
            else
            {
                objectsList = jObject.getJSONObject("backgrounds");
            }
        } catch (JSONException e) {
            gameManager.getMainActivity().log("Failed parsing into backgrounds");
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
                //Create new ladder object
                Background newObject = null;
                if(parallax)
                {
                    newObject = new ParallaxBackground(gameManager);
                }
                else
                {
                    newObject = new Background(gameManager);
                }

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
                        if(currentKey.equals("x")){
                            newObject.setX(Integer.parseInt(currentValue)); // X
                        }
                        else if(currentKey.equals("y")){
                            newObject.setY(Integer.parseInt(currentValue)); // Y
                        }
                        else  if(currentKey.equals("width")){
                            newObject.setWidth(Integer.parseInt(currentValue)); // WIDTH
                        }
                        else if(currentKey.equals("height"))
                        {
                            newObject.setHeight((Integer.parseInt(currentValue))); // HEIGHT
                        }
                        else if(currentKey.equals("image"))
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

                if(parallax)
                {
                    tempLevel.addParallaxBackground(newObject);
                }
                else
                {
                    tempLevel.addGameObject(newObject);
                }
            }
        }
    }

    private void processLadders(GameLevel tempLevel,JSONObject jObject)
    {
        JSONObject ladders = null;
        try {
           ladders = jObject.getJSONObject("ladders");
        } catch (JSONException e) {
            gameManager.getMainActivity().log("Failed parsing into ladders");
            e.printStackTrace();
            ladders = null;
        }

        /*
            WHILE i = 0 (each record is stored as an array [0], [1], etc
         */
        if(ladders != null)
        {
            /*
            Loop through each ladder
             */
            int i = 0;
            while(i >= 0)
            {
                //Create new ladder object
                Ladder newLadder = new Ladder(gameManager);
                try
                {
                    //GET THE CURRENT LADDER
                    JSONObject currentLadder = ladders.getJSONObject(String.valueOf(i));

                    //GET ASSOSIATED ARRAYS
                    JSONArray currentLadderNames = currentLadder.names();
                    JSONArray currentLadderValues = currentLadder.toJSONArray(currentLadderNames);

                    //GET THE ARRAY KEYS
                    String currentKey = "";
                    String currentValue = "";
                    for(int c = 0 ; c < currentLadderValues.length(); c++){
                        //SET THIS LOOPS VARIABLES
                        currentKey = currentLadderNames.getString(c);
                        currentValue = currentLadderValues.getString(c);

                        //CHECK WHAT WE ARE TRYING TO SET
                        if(currentKey.equals("questID")){
                            newLadder.setQuestID(Integer.parseInt(currentValue)); // QUEST
                        }
                        else if(currentKey.equals("x")){
                            newLadder.setX(Integer.parseInt(currentValue)); // X
                        }
                        else if(currentKey.equals("y")){
                            newLadder.setY(Integer.parseInt(currentValue)); // Y
                        }
                        else  if(currentKey.equals("width")){
                            newLadder.setWidth(Integer.parseInt(currentValue)); // WIDTH
                        }
                        else if(currentKey.equals("height"))
                        {
                            newLadder.setHeight((Integer.parseInt(currentValue))); // HEIGHT
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
                tempLevel.addGameObject(newLadder);
            }
        }
    }


    /*********************************************************************************************/
    /* PROCESS PlATFORMS */
    /*********************************************************************************************/
    private void processEnemies(GameLevel tempLevel,JSONObject jObject)
    {
        JSONObject objectsList = null;
        try {
            objectsList = jObject.getJSONObject("enemies");
        } catch (JSONException e) {
            gameManager.getMainActivity().log("Failed parsing into enemies");
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
                //Create new ladder object
                GameObject newObject = null;
                /*if(type.equals("frog")) {
                    newObject = new Frog(gameManager);
                } else if(type.equals("cloud")) {
                    newObject = new ClippingPlatform(gameManager);
                }
                else
                {
                    this.gameManager.getMainActivity().gameToast("Failed loading in platforms-"+type);
                    return;
                }*/

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
                        if(currentKey.equals("type"))
                        {
                            if(currentValue.equals("frog"))
                            {
                                newObject = new Frog(gameManager);
                            }
                        }
                        else if(currentKey.equals("x")){
                            newObject.setX(Integer.parseInt(currentValue)); // X
                        }
                        else if(currentKey.equals("y")){
                            newObject.setY(Integer.parseInt(currentValue)); // Y
                        }
                        else  if(currentKey.equals("width")){
                            newObject.setWidth(Integer.parseInt(currentValue)); // WIDTH
                        }
                        else if(currentKey.equals("height"))
                        {
                            newObject.setHeight((Integer.parseInt(currentValue))); // HEIGHT
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
                tempLevel.addGameObject(newObject);
            }
        }
    }
}