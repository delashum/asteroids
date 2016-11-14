package edu.byu.cs.superasteroids.model;

import android.graphics.Point;
import android.graphics.PointF;

import edu.byu.cs.superasteroids.asteroids_game.MovingObject;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Jacob on 10/20/2016.
 */
public class Ship extends MovingObject {

    private Cannon cannon;
    private ExtraPart extrapart;
    private Engine engine;
    private PowerCore powercore;
    private MainBody mainbody;
    private float rotation;

    public Ship() {
        rotation = 0;
    }


    public Cannon getCannon() {
        return cannon;
    }

    public void setCannon(Cannon cannon) {
        this.cannon = cannon;
    }

    public ExtraPart getExtrapart() {
        return extrapart;
    }

    public void setExtrapart(ExtraPart extrapart) {
        this.extrapart = extrapart;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public PowerCore getPowercore() {
        return powercore;
    }

    public void setPowercore(PowerCore powercore) {
        this.powercore = powercore;
    }

    public MainBody getMainbody() {
        return mainbody;
    }

    public void setMainbody(MainBody mainbody) {
        this.mainbody = mainbody;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void draw() {
        float scale = 0.6f;
        Point main = new Point();
        main.x = DrawingHelper.getGameViewWidth() / 2;
        main.y = DrawingHelper.getGameViewHeight() / 2;
        PointF temp;
        if (mainbody != null) {
            DrawingHelper.drawImage(mainbody.getId(),main.x, main.y, rotation, scale,scale,250);
            if (cannon != null) {
                temp = getOffset(mainbody.getCannonAttach(),cannon.getAttachPoint(),mainbody,cannon,scale);
                DrawingHelper.drawImage(cannon.getId(),main.x+temp.x,main.y+temp.y, rotation, scale,scale,250);
            }
            if (extrapart != null) {
                temp = getOffset(mainbody.getExtraAttach(),extrapart.getAttachPoint(),mainbody,extrapart,scale);
                DrawingHelper.drawImage(extrapart.getId(),main.x+temp.x,main.y+temp.y, rotation, scale,scale,250);
            }
            if (engine != null) {
                temp = getOffset(mainbody.getEngineAttach(),engine.getAttachPoint(),mainbody,engine,scale);
                DrawingHelper.drawImage(engine.getId(),main.x+temp.x,main.y+temp.y, rotation, scale,scale,250);
            }
        }
    }

    private PointF getOffset(String mainAttachPoint,String otherAttachPoint,MainBody body, ShipPart part, float scale) {
        PointF mainP = strToPoint(mainAttachPoint);
        PointF otherP = strToPoint(otherAttachPoint);

        //need to factor in scale..

        float offsetX = mainP.x - body.getImageWidth()/2 - otherP.x + part.getImageWidth()/2;
        float offsetY = mainP.y - body.getImageHeight()/2 - otherP.y + part.getImageHeight()/2;

        PointF offsetP = new PointF();

        offsetP.set(offsetX,offsetY);
        offsetP = GraphicsUtils.scale(offsetP,scale);

        offsetP = GraphicsUtils.rotate(offsetP,GraphicsUtils.degreesToRadians(rotation));


        return offsetP;
    }

    private PointF strToPoint(String str) {
        String[] arr = str.split(",");
        PointF temp =  new PointF();
        temp.x = Integer.parseInt(arr[0]);
        temp.y = Integer.parseInt(arr[1]);
        return temp;
    }

    public boolean full() {
        return cannon != null && mainbody != null && extrapart != null && engine != null && powercore != null;
    }
}
