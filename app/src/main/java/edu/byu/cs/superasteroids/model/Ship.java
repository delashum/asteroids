package edu.byu.cs.superasteroids.model;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.asteroids_game.MovingObject;
import edu.byu.cs.superasteroids.asteroids_game.Viewport;
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
    private double rotation;
    private float scale;
    private PointF position;
    private int hitPoints;
    private double safeMode;

    private final double SAFE_MODE_DURATION = 5;

    public Ship() {
        rotation = 0;
        scale = 1;
        position = new PointF(0,0);
        hitPoints = 5;
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

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public void update(PointF p,double time) {
        if (safeMode > 0) {
            safeMode -= time;
        }
        position = p;
    }

    public void draw() {
        float rotation = Float.parseFloat(Double.toString(this.rotation));
        PointF mainT = Viewport.worldToView(position);
        Point main = new Point((int) mainT.x, (int) mainT.y);
        int opacity = 250;
        if (safeMode > 0) {
            opacity = (int)(200 * (safeMode%1)) + 50;
        }
        PointF temp;
        if (mainbody != null) {
            DrawingHelper.drawImage(mainbody.getId(), main.x, main.y, rotation, scale, scale, opacity);
            if (cannon != null) {
                temp = getOffset(mainbody.getCannonAttach(), cannon.getAttachPoint(), mainbody, cannon, scale);
                DrawingHelper.drawImage(cannon.getId(), main.x + temp.x, main.y + temp.y, rotation, scale, scale, opacity);
            }
            if (extrapart != null) {
                temp = getOffset(mainbody.getExtraAttach(), extrapart.getAttachPoint(), mainbody, extrapart, scale);
                DrawingHelper.drawImage(extrapart.getId(), main.x + temp.x, main.y + temp.y, rotation, scale, scale, opacity);
            }
            if (engine != null) {
                temp = getOffset(mainbody.getEngineAttach(), engine.getAttachPoint(), mainbody, engine, scale);
                DrawingHelper.drawImage(engine.getId(), main.x + temp.x, main.y + temp.y, rotation, scale, scale, opacity);
            }
        }
    }

    private PointF getOffset(String mainAttachPoint,String otherAttachPoint,MainBody body, ShipPart part, float scale) {
        PointF mainP = strToPoint(mainAttachPoint);
        PointF otherP = strToPoint(otherAttachPoint);

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

    public float getEmitOffset() {
        PointF cannonAttach = strToPoint(mainbody.getCannonAttach());
        PointF emitPoint = strToPoint(cannon.getEmitPoint());
        PointF wingAttach = strToPoint(cannon.getAttachPoint());
        return ((cannonAttach.x - mainbody.getImageWidth()/2) + (emitPoint.x - wingAttach.x)) * scale;
    }

    public RectF getBounds(PointF location) {
        RectF temp = new RectF();
        float left = location.x - (mainbody.getImageWidth()/2 + extrapart.getImageWidth()) * scale;
        float top = location.y - (mainbody.getImageHeight()/2) * scale;
        float right = location.x + (mainbody.getImageWidth()/2 + cannon.getImageWidth()) * scale;
        float bottom = location.y + (mainbody.getImageHeight()/2 + engine.getImageHeight()) * scale;
        temp.set(left,top,right,bottom);
        return temp;
    }

    public boolean getHit() {
        hitPoints--;
        safeMode = SAFE_MODE_DURATION;
        return hitPoints < 1;
    }

    public boolean safeMode() {
        return safeMode > 0;
    }
}
