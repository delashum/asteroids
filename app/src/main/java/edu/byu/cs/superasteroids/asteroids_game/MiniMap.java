package edu.byu.cs.superasteroids.asteroids_game;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.model.Asteroid;
import edu.byu.cs.superasteroids.model.Ship;

/**
 * Created by Jacob on 10/20/2016.
 */
public class MiniMap extends Positioned {

    private final int WIDTH = 130;
    private final int HEIGHT = 90;
    private final int PADDING = 20;

    private PointF position;
    private Rect bounds;
    private int levelwidth;
    private int levelHeight;
    private int viewWidth;
    private int viewHeight;

    private PointF shipPos;
    private ArrayList<Asteroid> asteroids;

    public MiniMap(int width,int height) {
        levelwidth = width;
        levelHeight = height;
        position = new PointF();
        bounds = new Rect();
    }

    public int getLevelwidth() {
        return levelwidth;
    }

    public void setLevelwidth(int levelwidth) {
        this.levelwidth = levelwidth;
    }

    public int getLevelHeight() {
        return levelHeight;
    }

    public void setLevelHeight(int levelHeight) {
        this.levelHeight = levelHeight;
    }

    public int getViewWidth() {
        return viewWidth;
    }

    public void setViewWidth(int viewWidth) {
        this.viewWidth = viewWidth;
    }

    public int getViewHeight() {
        return viewHeight;
    }

    public void setViewHeight(int viewHeight) {
        this.viewHeight = viewHeight;
    }

    public PointF worldToMiniMap(PointF point) {
        int tempX = (int) (WIDTH * point.x / levelwidth);
        int tempY = (int) (HEIGHT * point.y / levelHeight);
        tempX += position.x;
        tempY += position.y;
        PointF temp = new PointF(tempX,tempY);
        return temp;
    }

    public void update(PointF position_in, ArrayList<Asteroid> asteroids) {
        position.x = viewWidth - (WIDTH + PADDING);
        position.y = viewHeight - (HEIGHT + PADDING);
        bounds = new Rect((int)position.x, (int)position.y, (int)position.x + WIDTH, (int)position.y + HEIGHT);
        this.shipPos = position_in;
        this.asteroids = asteroids;
    }

    public void draw() {
        if (shipPos == null || asteroids == null) {
            return;
        }
        //draw border
        DrawingHelper.drawFilledRectangle(bounds, Color.BLACK,250);
        DrawingHelper.drawRectangle(bounds, Color.GRAY,250);

        DrawingHelper.drawPoint(worldToMiniMap(shipPos),5,Color.RED,250);
        for (Asteroid e : asteroids) {
            DrawingHelper.drawPoint(worldToMiniMap(e.getPosition()),3,Color.GRAY,250);
        }
    }

}
