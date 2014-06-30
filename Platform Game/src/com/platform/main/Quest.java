package com.platform.main;

/**
 * Created by Gareth Somers on 6/19/14.
 */
public class Quest
{
    GameManager gameManager;
    int questID;
    boolean repeat;
    boolean triggeredYet = false;
    String objective;
    public Quest(GameManager gameManager,int questID, boolean repeat, String objective)
    {
        this.gameManager = gameManager;
        this.questID = questID;
        this.repeat = repeat;
        this.objective = objective;
    }

    public void triggerAction()
    {
        if(triggeredYet == false || repeat == true)
        {
            this.gameManager.getMainActivity().gameToast(objective);
        }
    }
}
