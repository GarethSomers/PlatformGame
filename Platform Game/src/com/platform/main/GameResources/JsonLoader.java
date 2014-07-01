package com.platform.main.GameResources;

import com.platform.main.GameManager;
import com.platform.main.GameResources.Level.GameLevel;
import com.platform.main.GameResources.Level.Level;
import com.platform.main.GameResources.Object.Interactions.Ladder;

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
        Level tempLevel = new GameLevel(gameManager);

        try {
            content = gameManager.getMainActivity().getAssets().open("levels/"+levelName+".json");
        } catch (IOException e) {
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
            e.printStackTrace();
        }


        /*
            Process the JSON Object
         */
        if (jObject != null) {
            processLadders(tempLevel, jObject);
            /*
                GET THE LADDERS
             */
        }
        return tempLevel;
    }

    private void processLadders(Level tempLevel,JSONObject jObject)
    {
        JSONObject ladders = null;
        try {
           ladders = jObject.getJSONObject("ladders");
        } catch (JSONException e) {
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
            }
        }
    }

}