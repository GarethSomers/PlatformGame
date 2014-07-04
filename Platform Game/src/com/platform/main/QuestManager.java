package com.platform.main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Gareth Somers on 6/19/14.
 */
public class QuestManager {
    GameManager gameManager;
    ArrayList<Quest> quests;

    public QuestManager(GameManager gameManager)
    {
        //declare objects
        this.gameManager = gameManager;
        this.quests = new ArrayList<Quest>();

        //add test quest
        this.quests.add(new Quest(gameManager,1,true,"Hey There"));
    }

    public QuestManager(MainActivity mActivity, boolean lalal)
    {
        this.gameManager = gameManager;
        this.quests = new ArrayList<Quest>();

        try {
            /*
            Open input Stream
             */
            InputStream file = mActivity.getAssets().open("quest.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(file, "UTF-8"));
            String line;

            /*
            Load quests
             */
            while ((line = br.readLine()) != null) {
                String data[] = line.split(",");

                /*
                try and add the quest
                 */
                try
                {
                    this.quests.add(new Quest(this.gameManager,Integer.getInteger(data[0]),Boolean.valueOf(data[1]),data[2]));
                }
                catch(Exception e)
                {
                    mActivity.log("Could not add "+data[0]);
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e) {
            mActivity.log("Could not load level (quests)");
            System.exit(0);
        }
    }

    public void triggerAction(int questID)
    {
        for(Quest q : this.quests)
        {
            if(q.questID == questID)
            {
                q.triggerAction();
            }
        }
    }
}
