package com.platform.main.GameResources.Object.Interactions;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.platform.main.GameManager;
import com.platform.main.GameResources.Object.Platforms.RectangularPlatform;
import com.platform.main.GameResources.Object.QuestTriggeringObject;

public class Ladder extends RectangularPlatform implements QuestTriggeringObject
{
    int questID = 0;

    /*
    * Ladder Used by JSONLoader. It sets the properties afterwards.
     */
    public Ladder(GameManager gameManager)
    {
        super(gameManager);
    }

    public void setQuestID(int questID)
    {
        this.questID = questID;
    }

    @Override
    public void triggerAction() {
        gameManager.getQuestManager().triggerAction(questID);
    }

    @Override
    public void preCreateObject() {
        this.updatePosition = false;
    }

    @Override
    public void afterCreateObject() {
        this.getShape().setAlpha(0);
        ((Fixture)this.body.getFixtureList().get(0)).setSensor(true);
        this.getBody().setUserData(this);
    }
}
