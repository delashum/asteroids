package edu.byu.cs.superasteroids.model;

/**
 * Created by Jacob on 10/20/2016.
 */
public class MainBody extends ShipPart {

    private String cannonAttach;
    private String engineAttach;
    private String extraAttach;

    public String getCannonAttach() {
        return cannonAttach;
    }

    public void setCannonAttach(String cannonAttach) {
        this.cannonAttach = cannonAttach;
    }

    public String getEngineAttach() {
        return engineAttach;
    }

    public void setEngineAttach(String engineAttach) {
        this.engineAttach = engineAttach;
    }

    public String getExtraAttach() {
        return extraAttach;
    }

    public void setExtraAttach(String extraAttach) {
        this.extraAttach = extraAttach;
    }
}
