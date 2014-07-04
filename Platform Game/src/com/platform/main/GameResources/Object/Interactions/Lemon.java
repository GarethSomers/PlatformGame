package com.platform.main.GameResources.Object.Interactions;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.platform.main.GameManager;
import com.platform.main.GameResources.Object.AnimatedGameObject;

public class Lemon extends AnimatedGameObject
{
    protected boolean alive = true;
    public Lemon(GameManager gameManager)
    {
        super(gameManager);

        ((Fixture)this.body.getFixtureList().get(0)).setSensor(true);
    }

    public void collect()
    {
        if(this.alive == true)
        {
            this.getShape().setVisible(false);
            this.alive = false;
            this.gameManager.getThePlayer().addHealth(5);
        }
    }

    @Override
    public void setAnimation(String paramString) {
        this.setWidth(16);
        this.setWidth(16);
        this.setImage("lemon.png");
        this.bodyType = BodyDef.BodyType.StaticBody;
        this.body.setUserData(this);
        this.fixtureDef.isSensor = true;
    }

    @Override
    public void preCreateObject() {
        this.mTiledTextureRegion = this.gameManager.getMaterialManager().getTiledTexture(this.image, ((int)this.width)*this.columns, ((int)this.height)*this.rows, this.columns, this.rows);
        this.bodyType = BodyDef.BodyType.StaticBody;
    }

    @Override
    public void afterCreateObject() {
        this.getBody().setAwake(false);
        this.getBody().setUserData(this);
        this.getBody().setFixedRotation(true);
    }
}
