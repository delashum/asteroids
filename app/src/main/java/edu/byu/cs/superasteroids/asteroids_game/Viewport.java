package edu.byu.cs.superasteroids.asteroids_game;

import android.graphics.PointF;
import android.graphics.Rect;

/**
 * Created by Jacob on 11/8/2016.
 */
public class Viewport {
    private static int width;
    private static int height;

    private static int maxWidth;
    private static int maxHeight;

    private static PointF position;
    private static Rect rectPosition;
    private static Rect rectSize;

    public static void setMaxWidth(int maxWidth) {
        Viewport.maxWidth = maxWidth;
    }

    public static void setMaxHeight(int maxHeight) {
        Viewport.maxHeight = maxHeight;
    }

    public static PointF worldToView(PointF point) {
        PointF tempP = new PointF();
        if (position != null) {
            tempP.set(point.x - position.x, point.y - position.y);
            return tempP;
        }
        return point;
    }

    public static void setWidth(int width) {
        Viewport.width = width;
    }

    /**
     *
     * @param position ships position
     */
    public static void setPosition(PointF position) {
        updatePosition(position);
    }

    public static Rect getRectPosition() {
        return rectPosition;
    }

    public static Rect getRectSize() {
        return rectSize;
    }

    public static void setHeight(int height) {
        Viewport.height = height;

    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static int getMaxWidth() {
        return maxWidth;
    }

    public static int getMaxHeight() {
        return maxHeight;
    }

    /**
     * Takes the PointF position of the ship in map context
     * @param point
     */
    public static void updatePosition(PointF point) {
        float tempX = point.x;
        float tempY = point.y;

        tempX -= width/2;
        tempY -= height/2;

        tempX = tempX < 0 ? 0 : tempX;
        tempY = tempY < 0 ? 0 : tempY;

        tempX = tempX + width > (maxWidth) ? (maxWidth - width) : tempX;
        tempY = tempY + height > (maxHeight) ? (maxHeight - height) : tempY;

        if (position == null) {
            position = new PointF();
        }

        position.set(tempX,tempY);

        updateRect();

    }

    private static void updateRect() {
        rectPosition = new Rect();
        rectPosition.set(Math.round(position.x),Math.round(position.y),Math.round(position.x + width), Math.round(position.y + height));
        rectSize = new Rect();
        rectSize.set(0,0,width,height);
    }
}
