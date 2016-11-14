package edu.byu.cs.superasteroids.model;

import edu.byu.cs.superasteroids.database.dbHelper;

/**
 * Created by Jacob on 11/1/2016.
 */
public class SingletonModel {
    private AsteroidsGame game;
    private static SingletonModel me;
    private dbHelper db;
    private Ship ship;

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    private SingletonModel() {
        game = new AsteroidsGame();

    }

    public static SingletonModel getInstance() {
        if (me == null) {
            me = new SingletonModel();
        }
        return me;
    }

    public void setDB(dbHelper db) {
        this.db = db;
    }

    public dbHelper getDB() {
        return db;
    }

    public void setGame(AsteroidsGame in) {
        game = in;
    }

    public AsteroidsGame getGame() {
        return game;
    }

    public void resetGame() {
        game = new AsteroidsGame();
    }
}
