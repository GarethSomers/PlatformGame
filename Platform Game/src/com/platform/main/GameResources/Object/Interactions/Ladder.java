package com.platform.main.GameResources.Object.Interactions;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.platform.main.GameManager;
import com.platform.main.GameResources.Object.Platforms.RectangularPlatform;
import com.platform.main.GameResources.Object.QuestTriggeringObject;

public class Ladder extends RectangularPlatform implements QuestTriggeringObject
{
    int questID = 0;
    public Ladder(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, GameManager paramMainActivity)
    {
        super(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramMainActivity);
        ((Fixture)this.body.getFixtureList().get(0)).setSensor(true);
        this.body.setUserData(this);
    }

    public void setQuestID(int questID)
    {
        this.questID = questID;
    }

    @Override
    public void triggerAction() {
        gameManager.getQuestManager().triggerAction(questID);
    }
}
