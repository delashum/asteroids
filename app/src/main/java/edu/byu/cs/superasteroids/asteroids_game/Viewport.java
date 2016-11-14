package edu.byu.cs.superasteroids.asteroids_game;

import android.graphics.PointF;

/**
 * Created by Jacob on 11/8/2016.
 */
public class Viewport {
    private static int width;
    private static int height;

    private static int maxWidth;
    private static int maxHeight;

    private static PointF position;

    public static void setMaxWidth(int maxWidth) {
        Viewport.maxWidth = maxWidth;
    }

    public static void setMaxHeight(int maxHeight) {
        Viewport.maxHeight = maxHeight;
    }

    public static PointF worldToView(PointF point) {
        PointF tempP = new PointF();
        tempP.set(point.x - position.x, point.y - position.y);
        return tempP;
    }

    public static void setWidth(int width) {
        Viewport.width = width;
    }

    public static void setPosition(PointF position) {
        Viewport.position = position;
    }

    public static void setHeight(int height) {
        Viewport.height = height;

    }

    /**
     * Takes the PointF position of the ship in map context
     * @param point
     */
    public static void updatePosition(PointF point) {
        float tempX = point.x - width/2;
        float tempY = point.y - height/2;

        tempX = tempX < 0 ? 0 : tempX;
        tempY = tempY < 0 ? 0 : tempY;

        tempX = tempX > (maxWidth - width) ? (maxWidth - width) : tempX;
        tempY = tempY > (maxHeight - height) ? (maxHeight - height) : tempY;

        position.set(tempX,tempY);

    }
}
