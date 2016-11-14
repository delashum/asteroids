package edu.byu.cs.superasteroids.asteroids_game;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.model.Asteroid;
import edu.byu.cs.superasteroids.model.bgAsteroid;
import edu.byu.cs.superasteroids.model.AsteroidsGame;
import edu.byu.cs.superasteroids.model.Level;
import edu.byu.cs.superasteroids.model.Ship;
import edu.byu.cs.superasteroids.model.SingletonModel;

/**
 * Created by Jacob on 11/7/2016.
 */
public class MainGame {

    private MainMap map;
    private MiniMap minimap;
    private Ship ship;
    private ArrayList<Projectile> projectiles;
    private ArrayList<Asteroid> asteroids;
    private AsteroidsGame game;
    private Level curLevel;

    public MainGame() {
        game = SingletonModel.getInstance().getGame();
        ship = SingletonModel.getInstance().getShip();
        map = new MainMap();
        minimap = new MiniMap();

        goToLevel(0);
        initAsteroids(curLevel.getLevelAsteroids());
        Viewport.setMaxWidth(curLevel.getWidth());
        Viewport.setMaxHeight(curLevel.getHeight());
    }

    public void setBackground(int id) {
        map.setBackground(id);
    }

    public int getBackground() {
        return map.getBackground();
    }

    private void initAsteroids(ArrayList<bgAsteroid> tempAst) {
        asteroids = new ArrayList<>();
        Asteroid temp;
        for (bgAsteroid e : tempAst) {
            temp = game.getAsteroid(e.getAsteroidId());
            for (int i = 0; i < e.getNumber(); i++) {
                asteroids.add(copyAsteroid(temp));
            }
        }

    }
    private Asteroid copyAsteroid(Asteroid in) {
        Asteroid temp = new Asteroid();

        temp.setId(in.getId());
        temp.setName(in.getName());
        temp.setImage(in.getImage());
        temp.setImageWidth(in.getImageWidth());
        temp.setImageHeight(in.getImageHeight());
        temp.setType(in.getType());

        return temp;
    }

    private void goToLevel(int level) {
        curLevel = game.getLevels().get(level);
    }

    public void update(double time) {

    }

    public void draw() {

    }

}
