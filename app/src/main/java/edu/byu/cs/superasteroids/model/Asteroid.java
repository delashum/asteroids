package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.asteroids_game.Viewport;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Jacob on 10/20/2016.
 */
public class Asteroid {

    private int id;
    private String name;
    private String image;
    private int imageWidth;
    private int imageHeight;
    private String type;

    private int timesDestroyed;

    private RectF bounds;

    private int imageId;

    private PointF position;
    private int speed;
    private double direction;
    private float size;

    public Asteroid() {
        bounds = new RectF();
    }

    public int getTimesDestroyed() {
        return timesDestroyed;
    }

    public void setTimesDestroyed(int timesDestroyed) {
        this.timesDestroyed = timesDestroyed;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PointF getPosition() {
        return position;
    }

    public void setPosition(PointF position) {
        this.position = position;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public RectF getBounds() {
        return bounds;
    }

    public void update(double time) {
        position.x += Math.cos(direction)*speed*time;
        position.y += Math.sin(direction)*speed*time;

        bounds.set((position.x - imageWidth*size/2), (position.y - imageHeight*size/2), (position.x + imageWidth*size/2), (position.y + imageHeight*size/2));
        direction = GraphicsUtils.ricochetObject(position, bounds,direction,Viewport.getMaxWidth(),Viewport.getMaxHeight()).getNewAngleRadians();

        if (type.equals("growing")) {
            size += 0.002;
        }
    }

    public void draw() {
        PointF temp = Viewport.worldToView(position);
        DrawingHelper.drawImage(id,temp.x, temp.y, 0, size,size,250);
    }
}
