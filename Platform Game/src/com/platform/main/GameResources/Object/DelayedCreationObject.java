package com.platform.main.GameResources.Object;

/**
 * Created by Gareth Somers on 7/1/14.
 */
public interface DelayedCreationObject {
    public void preCreateObject();
    public void createObject();
    public void afterCreateObject();
}
