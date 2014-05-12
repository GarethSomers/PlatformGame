package com.platform.main.GameResources.Level.Menu;

import com.platform.main.MainActivity;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Gareth Somers on 5/12/14.
 */
public class MenuButton implements ButtonSprite.OnClickListener
{
    public MainActivity mActivity;
    public MenuButton(MainActivity mActivity)
    {

        this.mActivity = mActivity;
    }

    @Override
    public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        this.mActivity.log("clicked a button");
    }
}
