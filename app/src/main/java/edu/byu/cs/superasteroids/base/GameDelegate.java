package edu.byu.cs.superasteroids.base;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.superasteroids.asteroids_game.MainGame;
import edu.byu.cs.superasteroids.content.AudioManagement;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.importer.IGameDataImporter;
import edu.byu.cs.superasteroids.model.Asteroid;
import edu.byu.cs.superasteroids.model.AsteroidsGame;
import edu.byu.cs.superasteroids.model.Cannon;
import edu.byu.cs.superasteroids.model.Engine;
import edu.byu.cs.superasteroids.model.ExtraPart;
import edu.byu.cs.superasteroids.model.MainBody;
import edu.byu.cs.superasteroids.model.PowerCore;
import edu.byu.cs.superasteroids.model.Ship;
import edu.byu.cs.superasteroids.model.SingletonModel;
import edu.byu.cs.superasteroids.ship_builder.IShipBuildingView;

/**
 * Created by Jacob on 10/17/2016.
 */
public class GameDelegate implements IGameDelegate {

    private MainGame game;
    private AsteroidsGame model;
    private Ship ship;

    public GameDelegate() {
        game = new MainGame();
        model = SingletonModel.getInstance().getGame();
        ship = SingletonModel.getInstance().getShip();
    }

    @Override
    public void update(double elapsedTime) {
        game.update(elapsedTime);
    }

    @Override
    public void loadContent(ContentManager content) {
        //load ship images
        ship.getCannon().setId(content.loadImage(ship.getCannon().getImage()));
        ship.getEngine().setId(content.loadImage(ship.getEngine().getImage()));
        ship.getMainbody().setId(content.loadImage(ship.getMainbody().getImage()));
        ship.getExtrapart().setId(content.loadImage(ship.getExtrapart().getImage()));

        //load map objects
        for (Asteroid e : model.getAsteroids()) {
            e.setId(content.loadImage(e.getImage()));
        }

        for (String e : model.getObjects()) {
            model.addObjectImage(content.loadImage(e));
        }

        game.setBackground(content.loadImage("images/space.bmp"));

        game.setAttack(content.loadImage(ship.getCannon().getAttackImage()));
        try {
            game.setAttackSound(content.loadSound(ship.getCannon().getAttackSound()));
        } catch(Exception e) {

        }

        game.contentLoaded();

    }

    @Override
    public void unloadContent(ContentManager content) {
        content.unloadImage(ship.getCannon().getId());
        content.unloadImage(ship.getEngine().getId());
        content.unloadImage(ship.getMainbody().getId());
        content.unloadImage(ship.getExtrapart().getId());

        for (int i : model.getAsteroidImage()) {
            content.unloadImage(i);
        }

        for (int i : model.getObjectImage()) {
            content.unloadImage(i);
        }

        content.unloadImage(game.getBackground());
    }

    @Override
    public void draw() {
        game.draw();
    }
}
