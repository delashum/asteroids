package edu.byu.cs.superasteroids.asteroids_game;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.model.bgObject;

/**
 * Created by Jacob on 11/7/2016.
 */
public class MainMap extends Positioned {
    private ArrayList<bgObject> backgroundObjects;
    private int width;
    private int height;

    private int background;

    public void setBackground(int background) {
        this.background = background;
        DrawingHelper.draw;
    }

    public int getBackground() {
        return background;
    }

    public MainMap() {

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
}
