package edu.byu.cs.superasteroids.model;

import edu.byu.cs.superasteroids.asteroids_game.Positioned;

/**
 * Created by Jacob on 10/20/2016.
 */
public class bgObject extends Positioned {

    private String position;
    private int objectId;
    private float scale;
    private int levelId;

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
