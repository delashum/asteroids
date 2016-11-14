package edu.byu.cs.superasteroids.model;

import edu.byu.cs.superasteroids.asteroids_game.MovingObject;

/**
 * Created by Jacob on 10/20/2016.
 */
public class bgAsteroid extends MovingObject {

    private int number;
    private int asteroidId;
    private int levelId;

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getAsteroidId() {
        return asteroidId;
    }

    public void setAsteroidId(int asteroidId) {
        this.asteroidId = asteroidId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
