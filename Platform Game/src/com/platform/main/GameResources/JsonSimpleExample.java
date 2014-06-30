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

public class JsonSimpleExample {
    GameManager gameManager;
    public JsonSimpleExample(GameManager gameManager) {
        InputStream content = null;
        Level tempLevel = new GameLevel(gameManager);

        try {
            content = gameManager.getMainActivity().getAssets().open("levels/oneJson.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // (1)
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
        // (2)
        JSONObject jObject = null;
        try {
            jObject = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        /*
        Add to level
         */

        if (jObject != null) {
            try {
                JSONObject ladders = null;
                ladders = jObject.getJSONObject("ladders");
                int i = 0;
                //FOR EACH LADDER UNTIL IT FAILS
                while(i >= 0)
                {
                    Ladder newLadder = new Ladder(0,0,10,10,gameManager);
                    try
                    {
                        //GET THE CURRENT LADDER
                        JSONObject currentLadder = ladders.getJSONObject(String.valueOf(i));
                        //GET ASSOSIATED ARRAYS
                        JSONArray currentLadderNames = currentLadder.names();
                        JSONArray currentLadderValues = currentLadder.toJSONArray(currentLadderNames);

                        //GET THE ARRAY KEYS
                        for(int c = 0 ; c < currentLadderValues.length(); c++){
                            //CHECK WHAT WE ARE TRYING TO SET
                            if(currentLadderNames.getString(c).equals("questID")){
                                newLadder.setQuestID(Integer.parseInt(currentLadderValues.getString(c)));
                            }
                            else if(currentLadderNames.getString(c).equals("x")){
                                newLadder.setX(Integer.parseInt(currentLadderValues.getString(c)));
                            }
                            else if(currentLadderNames.getString(c).equals("y")){
                                newLadder.setY(Integer.parseInt(currentLadderValues.getString(c)));
                            }
                            gameManager.getMainActivity().log("name "+currentLadderNames.getString(c));
                            try
                            {
                                gameManager.getMainActivity().log("value "+currentLadderValues.getString(c));
                            }
                            catch(Exception e)
                            {
                                gameManager.getMainActivity().log("failed to get value?");
                            }
                        }
                        i++;
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        i = -1;
                    }
                    gameManager.getMainActivity().gameToast(String.valueOf(newLadder.getX()));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}