package edu.byu.cs.superasteroids.model;

/**
 * Created by Jacob on 10/20/2016.
 * Power Core Object that controls
 */
public class PowerCore extends ShipPart {

    private int cannonBoost;
    private int engineBoost;

    public int getCannonBoost() {
        return cannonBoost;
    }

    public void setCannonBoost(int cannonBoost) {
        this.cannonBoost = cannonBoost;
    }

    public int getEngineBoost() {
        return engineBoost;
    }

    public void setEngineBoost(int engineBoost) {
        this.engineBoost = engineBoost;
    }
}
