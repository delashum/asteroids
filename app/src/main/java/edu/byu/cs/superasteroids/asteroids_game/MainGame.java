package edu.byu.cs.superasteroids.asteroids_game;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game.InputManager;
import edu.byu.cs.superasteroids.model.Asteroid;
import edu.byu.cs.superasteroids.model.Cannon;
import edu.byu.cs.superasteroids.model.bgAsteroid;
import edu.byu.cs.superasteroids.model.AsteroidsGame;
import edu.byu.cs.superasteroids.model.Level;
import edu.byu.cs.superasteroids.model.Ship;
import edu.byu.cs.superasteroids.model.SingletonModel;
import edu.byu.cs.superasteroids.model.bgObject;

/**
 * Created by Jacob on 11/7/2016.
 */
public class MainGame {

    private final int SPEED = 500;
    private final int ROTATION_SPEED = 5;

    private final int PROJECTILE_SPEED = 1000;
    private final float PROJECTILE_SCALE = 0.2f;

    private final int ASTEROID_SPEED = 100;
    private final float ASTEROID_SIZE = 0.8f;
    private final int SAFE_ZONE = 150;
    private final int ASTEROID_LIVES = 2;

    private MiniMap minimap;
    private Ship ship;
    private ArrayList<Projectile> projectiles;
    private ArrayList<Asteroid> asteroids;
    private AsteroidsGame game;
    private Level curLevel;
    private PointF curLocation;
    private double rotation;
    private int background;
    private int attack;
    private int attackSound;
    private boolean gameOver;
    private String alertMessage;
    private boolean contentDone;

    private Rect viewRect;
    private Rect mapRect;

    public MainGame() {
        game = SingletonModel.getInstance().getGame();
        ship = SingletonModel.getInstance().getShip();
        ship.setScale(0.2f);
        InitLevel(0);
        gameOver = false;
        contentDone = false;
    }

    public void contentLoaded() {
        loadStuff();
        contentDone = true;
    }

    private void loadStuff() {
        curLocation = new PointF(curLevel.getWidth()/2,curLevel.getHeight()/2);

        initAsteroids(curLevel.getLevelAsteroids());
        projectiles = new ArrayList<>();
        Viewport.setMaxWidth(curLevel.getWidth());
        Viewport.setMaxHeight(curLevel.getHeight());

        minimap = new MiniMap(curLevel.getWidth(),curLevel.getHeight());

        rotation = 0;
        ship.setRotation(rotation);
        ship.setPosition(curLocation);

        viewRect = new Rect();
        mapRect = new Rect();
    }

    private void InitLevel(int id) {
        goToLevel(id);
        if (curLevel == null) {
            winGame();
            return;
        }
        if (contentDone) {
            loadStuff();
        }
    }

    public void setBackground(int id) {
        background = id;
    }

    public int getBackground() {
        return background;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getAttackSound() {
        return attackSound;
    }

    public void setAttackSound(int attackSound) {
        this.attackSound = attackSound;
    }

    private void initAsteroids(ArrayList<bgAsteroid> tempAst) {
        asteroids = new ArrayList<>();
        Asteroid temp;
        Asteroid tempCopy;
        for (bgAsteroid e : tempAst) {
            temp = game.getAsteroid(e.getAsteroidId());
            for (int i = 0; i < e.getNumber(); i++) {
                tempCopy = copyAsteroid(temp);
                tempCopy.setSize(ASTEROID_SIZE);
                tempCopy.setSpeed((int)random(Math.round(ASTEROID_SPEED/2),Math.round(ASTEROID_SPEED*1.5f)));
                tempCopy.setDirection(random(0,6));
                tempCopy.setPosition(randomPoint());
                tempCopy.setTimesDestroyed(0);
                asteroids.add(tempCopy);
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
        try {
            curLevel = game.getLevels().get(level);
        } catch(Exception e) {
            curLevel = null;
        }
    }

    public void update(double time) {
        Viewport.setWidth(DrawingHelper.getGameViewWidth());
        Viewport.setHeight(DrawingHelper.getGameViewHeight());
        minimap.setViewWidth(DrawingHelper.getGameViewWidth());
        minimap.setViewHeight(DrawingHelper.getGameViewHeight());
        updatePoint(time);
        Viewport.updatePosition(curLocation);

        fireCannon();

        ship.setRotation(GraphicsUtils.radiansToDegrees(rotation));
        ship.update(curLocation,time);
        for (Asteroid e : asteroids) {
            e.update(time);
        }

        for (int i = projectiles.size() - 1; i >= 0; i--) {
            PointF tempPos = projectiles.get(i).getPosition();
            if (tempPos.x < 0 || tempPos.y < 0 || tempPos.x > Viewport.getMaxWidth() || tempPos.y > Viewport.getMaxHeight()) {
                projectiles.remove(i);
            } else {
                projectiles.get(i).update(time);
                if (checkCollisions(projectiles.get(i))) {
                    projectiles.remove(i);
                }
            }
        }

        checkCollisions();

        minimap.update(curLocation,asteroids);

        if (asteroids.size() < 1 && !gameOver) {
            InitLevel(curLevel.getNumber());
        }

    }

    private boolean checkCollisions(Projectile p) {
        for (int i = asteroids.size() - 1; i >= 0; i--) {
            if (p.getBounds().intersect(asteroids.get(i).getBounds())) {
                spawnAsteroids(asteroids.get(i));
                asteroids.remove(i);
                return true;
            }
        }
        return false;
    }

    private boolean checkCollisions() {
        for (int i = asteroids.size() - 1; i >= 0; i--) {
            if (ship.getBounds(curLocation).intersect(asteroids.get(i).getBounds()) && !ship.safeMode()) {
                spawnAsteroids(asteroids.get(i));
                asteroids.remove(i);
                if (ship.getHit()) {
                    loseGame();
                }
                return true;
            }
        }
        return false;
    }

    private void spawnAsteroids(Asteroid ast) {
        if (ast.getTimesDestroyed() >= ASTEROID_LIVES) {
            return;
        }
        Asteroid temp;
        int times = (ast.getType().equals("octeroid") ? 8 : 2);
        for (int i = 0; i < times; i++) {
            temp = copyAsteroid(ast);
            temp.setSize(ast.getSize()/2);
            temp.setSpeed((int)random(Math.round(ASTEROID_SPEED/2),Math.round(ASTEROID_SPEED*1.5f)));
            temp.setDirection(random(0,6));
            temp.setPosition(new PointF(ast.getPosition().x,ast.getPosition().y));
            temp.setTimesDestroyed(ast.getTimesDestroyed()+1);
            asteroids.add(temp);
        }
    }

    public void draw() {
        drawBackground();

        if (curLevel == null) {
            return;
        }

        for (bgObject e : curLevel.getLevelObjects()) {
            PointF tempP = Viewport.worldToView(strToPoint(e.getPosition()));

            DrawingHelper.drawImage(game.getObjectImage(e.getObjectId()),tempP.x,tempP.y,0,e.getScale(),e.getScale(),170);
        }

        ship.draw();
        for (Asteroid e : asteroids) {
            e.draw();
        }
        for (Projectile e : projectiles) {
            e.draw();
        }
        minimap.draw();
        message();
    }

    private PointF strToPoint(String str) {
        String[] arr = str.split(",");
        PointF temp =  new PointF();
        temp.x = Integer.parseInt(arr[0]);
        temp.y = Integer.parseInt(arr[1]);
        return temp;
    }

    private void fireCannon() {
        if (InputManager.firePressed && !gameOver) {
            Projectile temp = new Projectile(ship.getCannon());
            temp.setSpeed(PROJECTILE_SPEED);
            temp.setScale(PROJECTILE_SCALE);
            temp.setDirection(rotation);
            PointF rotatedEmit = GraphicsUtils.rotate(new PointF(ship.getEmitOffset(),0), rotation);
            temp.setPosition(new PointF(curLocation.x + rotatedEmit.x,curLocation.y + rotatedEmit.y));
            temp.setImageId(attack);
            temp.setSound(attackSound);
            projectiles.add(temp);
            ContentManager.getInstance().playSound(attackSound, 1,1);
        }
    }

    private void updatePoint(double time) {
        if (InputManager.movePoint == null || gameOver) {
            return;
        }

        //calculate angle of touch.

        if (rotation < 0) {
            rotation += Math.PI * 2;
        }
        rotation %= Math.PI * 2;

        PointF shipView = Viewport.worldToView(curLocation);

        double a = shipView.y;
        double b = GraphicsUtils.distance(shipView, InputManager.movePoint);
        double c = GraphicsUtils.distance(new PointF(shipView.x, 0), InputManager.movePoint);
        double angle = Math.acos((a * a + b * b - c * c) / (2 * a * b));

        if (InputManager.movePoint.x < shipView.x) {
            angle = Math.PI * 2 - angle;
        }

        boolean test = ((rotation - angle + Math.PI * 2) % (Math.PI * 2)) > Math.PI;
        if (Math.abs(rotation-angle) < ROTATION_SPEED * time) {

        } else if (test) {
            rotation += ROTATION_SPEED * time;
        } else {
            rotation -= ROTATION_SPEED * time;
        }


        PointF temp = new PointF(0,Float.parseFloat(Double.toString(SPEED*time)));
        temp = GraphicsUtils.rotate(temp,rotation);

        curLocation.set(curLocation.x - temp.x, curLocation.y - temp.y);

        if (curLocation.x < 0) {
            curLocation.x = 0;
        }
        if (curLocation.y < 0) {
            curLocation.y = 0;
        }
        if (curLocation.x > curLevel.getWidth()) {
            curLocation.x = curLevel.getWidth();
        }
        if (curLocation.y > curLevel.getHeight()) {
            curLocation.y = curLevel.getHeight();
        }


    }

    private void drawBackground() {
        mapRect = Viewport.getRectPosition();
        viewRect = Viewport.getRectSize();
        DrawingHelper.drawImage(background,mapRect,viewRect);
    }

    private double random(int lower, int upper) {
        return Math.random() * (upper-lower) + lower;
    }

    private PointF randomPoint() {
        double x = random(0,curLevel.getWidth());
        double y = random(0,curLevel.getHeight());
        while (Math.abs(x - curLocation.x) < SAFE_ZONE) {
            x = random(0,curLevel.getWidth());
        }
        while (Math.abs(y - curLocation.y) < SAFE_ZONE) {
            y = random(0,curLevel.getHeight());
        }
        return new PointF((float)x,(float)y);
    }

    private void loseGame() {
        gameOver = true;
        alertMessage = "You lost >:|";
    }

    private void winGame() {
        gameOver = true;
        alertMessage = "Swell Job! You won.";
    }


    private void message() {
        if (alertMessage == null) {
            return;
        }
        DrawingHelper.drawFilledRectangle(new Rect(0,0,Viewport.getWidth(),Viewport.getHeight()), Color.BLACK,120);
        DrawingHelper.drawCenteredText(alertMessage,50,Color.GRAY);
    }

}
