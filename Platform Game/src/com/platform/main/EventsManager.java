package com.platform.main;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

/**
 * Created by Gareth Somers on 7/22/14.
 *
 * EventsManager this class is used to set timers for events to happen ingame
 */
public class EventsManager implements ITimerCallback {
    GameManager gameManager;
    TimerHandler timeLeftTimer;
    int timeLeft = 100;

    public EventsManager(GameManager gameManager)
    {
        this.gameManager = gameManager;
    }

    public void startEventsManager()
    {
        if(this.timeLeftTimer != null)
        {
            this.timeLeftTimer.reset();
            this.timeLeft = 100;
        }
        else
        {
            this.timeLeftTimer = new TimerHandler(0.3f,true,this);
            this.gameManager.getMainActivity().getEngine().registerUpdateHandler(this.timeLeftTimer);
        }
    }

    @Override
    public void onTimePassed(TimerHandler pTimerHandler) {
        if(this.timeLeft == 0)
        {
            //this.gameManager.gameOver();
        }
        else if(gameManager.getLevelManager().currentState == LevelManager.LevelState.Playing)
        {
            this.timeLeft -= 1;
            this.gameManager.getLevelManager().getHUD().updateTimeLeft(this.timeLeft);
        }
    }

    public void addTime()
    {
        this.timeLeft += 1;
    }

    public void addTime(int extraTime)
    {
        this.timeLeft += extraTime;
    }

}
