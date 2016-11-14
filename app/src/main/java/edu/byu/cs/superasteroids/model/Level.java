package edu.byu.cs.superasteroids.model;

import java.util.ArrayList;

/**
 * Created by Jacob on 10/20/2016.
 */
public class Level {

    private int number;
    private String title;
    private String hint;
    private int width;
    private int height;
    private String music;

    private ArrayList<bgObject> levelObjects;
    private ArrayList<bgAsteroid> levelAsteroids;

    public Level() {
        levelAsteroids = new ArrayList<>();
        levelObjects = new ArrayList<>();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public ArrayList<bgObject> getLevelObjects() {
        return levelObjects;
    }

    public void addLevelObject(bgObject obj) {
        levelObjects.add(obj);
    }

    public ArrayList<bgAsteroid> getLevelAsteroids() {
        return levelAsteroids;
    }

    public void addLevelAsteroid(bgAsteroid obj) {
        levelAsteroids.add(obj);
    }
}
