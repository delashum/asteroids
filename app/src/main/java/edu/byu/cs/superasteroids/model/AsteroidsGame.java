package edu.byu.cs.superasteroids.model;

import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * Created by Jacob on 10/20/2016.
 */
public class AsteroidsGame {

    private ArrayList<String> objects;
    private ArrayList<Integer> objectImage;
    private ArrayList<Asteroid> asteroids;
    private ArrayList<Integer> asteroidImage;
    private ArrayList<Level> levels;

    private ArrayList<MainBody> mainBodies;
    private ArrayList<Cannon> cannons;
    private ArrayList<ExtraPart> extraParts;
    private ArrayList<Engine> engines;
    private ArrayList<PowerCore> powerCores;
    private AsteroidsGame asteroidsGame;


    public AsteroidsGame() {
        objects = new ArrayList<>();
        asteroids = new ArrayList<>();
        levels = new ArrayList<>();
        mainBodies = new ArrayList<>();
        cannons = new ArrayList<>();
        extraParts = new ArrayList<>();
        engines = new ArrayList<>();
        powerCores = new ArrayList<>();
        objectImage = new ArrayList<>();
        asteroidImage = new ArrayList<>();
    }

    public void addLevelObj(bgObject obj) {
        for(Level lev : levels) {
            if (obj.getLevelId() == lev.getNumber()) {
                lev.addLevelObject(obj);
            }
        }
    }

    public void addLevelAsteroid(bgAsteroid ast) {
        for(Level lev : levels) {
            if (ast.getLevelId() == lev.getNumber()) {
                lev.addLevelAsteroid(ast);
            }
        }
    }

    public Asteroid getAsteroid(int id) {
        return asteroids.get(id);
    }

    public int getObjectImage(int id) {
        return objectImage.get(id);
    }

    public int getAsteroidImage(int id) {
        return asteroidImage.get(id);
    }

    public void addObjectImage(int id) {
        objectImage.add(id);
    }

    public void addAsteroidImage(int id) {
        asteroidImage.add(id);
    }

    public ArrayList<Integer> getObjectImage() {
        return objectImage;
    }

    public ArrayList<Integer> getAsteroidImage() {
        return asteroidImage;
    }

    public AsteroidsGame getGame() {
        return asteroidsGame;
    }

    public ArrayList<String> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<String> objects) {
        this.objects = objects;
    }

    public ArrayList<Asteroid> getAsteroids() {
        return asteroids;
    }

    public void setAsteroids(ArrayList<Asteroid> asteroids) {
        this.asteroids = asteroids;
    }

    public ArrayList<Level> getLevels() {
        return levels;
    }

    public void setLevels(ArrayList<Level> levels) {
        this.levels = levels;
    }

    public ArrayList<MainBody> getMainBodies() {
        return mainBodies;
    }

    public void setMainBodies(ArrayList<MainBody> mainBodies) {
        this.mainBodies = mainBodies;
    }

    public ArrayList<Cannon> getCannons() {
        return cannons;
    }

    public void setCannons(ArrayList<Cannon> cannons) {
        this.cannons = cannons;
    }

    public ArrayList<ExtraPart> getExtraParts() {
        return extraParts;
    }

    public void setExtraParts(ArrayList<ExtraPart> extraParts) {
        this.extraParts = extraParts;
    }

    public ArrayList<Engine> getEngines() {
        return engines;
    }

    public void setEngines(ArrayList<Engine> engines) {
        this.engines = engines;
    }

    public ArrayList<PowerCore> getPowerCores() {
        return powerCores;
    }

    public void setPowerCores(ArrayList<PowerCore> powerCores) {
        this.powerCores = powerCores;
    }
}
