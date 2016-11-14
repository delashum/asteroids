package edu.byu.cs.superasteroids.model;

/**
 * Created by Jacob on 10/20/2016.
 */
public class Engine extends ShipPart {

    private int baseSpeed;
    private int baseTurnRate;
    private String attachPoint;

    public int getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(int baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    public int getBaseTurnRate() {
        return baseTurnRate;
    }

    public void setBaseTurnRate(int baseTurnRate) {
        this.baseTurnRate = baseTurnRate;
    }

    public String getAttachPoint() {
        return attachPoint;
    }

    public void setAttachPoint(String attachPoint) {
        this.attachPoint = attachPoint;
    }
}
