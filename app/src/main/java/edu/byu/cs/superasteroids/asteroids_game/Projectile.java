package edu.byu.cs.superasteroids.asteroids_game;

import android.graphics.PointF;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.asteroids_game.MovingObject;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.model.Cannon;

/**
 * Created by Jacob on 10/20/2016.
 */
public class Projectile extends MovingObject {

    private String image;
    private int imageWidth;
    private int imageHeight;
    private int sound;
    private int imageId;

    private double direction;
    private int speed;
    private PointF position;
    private float scale;

    private RectF bounds;

    public Projectile(Cannon cannon) {
        image = cannon.getAttackImage();
        imageWidth = cannon.getAttackImageWidth();
        imageHeight = cannon.getAttackImageHeight();
        bounds = new RectF();
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

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public PointF getPosition() {
        return position;
    }

    public void setPosition(PointF position) {
        this.position = position;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public int getSound() {
        return sound;
    }

    public RectF getBounds() {
        return bounds;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public void draw() {
        float rotation = Float.parseFloat(Double.toString(this.direction));
        PointF temp = Viewport.worldToView(position);
        DrawingHelper.drawImage(imageId,temp.x, temp.y, (float)GraphicsUtils.radiansToDegrees(rotation), scale,scale,250);
    }

    public void update(double time) {
        double temp = direction - GraphicsUtils.HALF_PI;
        position.x += Math.cos(temp)*speed*time;
        position.y += Math.sin(temp)*speed*time;
        bounds.set(position.x - imageWidth/2, position.y - imageHeight/2, position.x + imageWidth/2,position.y + imageWidth/2);
    }
}
