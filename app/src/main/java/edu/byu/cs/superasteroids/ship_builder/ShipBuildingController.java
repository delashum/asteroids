package edu.byu.cs.superasteroids.ship_builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.database.DAO;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.model.AsteroidsGame;
import edu.byu.cs.superasteroids.model.Cannon;
import edu.byu.cs.superasteroids.model.Engine;
import edu.byu.cs.superasteroids.model.ExtraPart;
import edu.byu.cs.superasteroids.model.MainBody;
import edu.byu.cs.superasteroids.model.PowerCore;
import edu.byu.cs.superasteroids.model.Ship;
import edu.byu.cs.superasteroids.model.SingletonModel;

/**
 * Created by Jacob on 10/13/2016.
 */
public class ShipBuildingController implements IShipBuildingController {

    private IShipBuildingView view;
    private AsteroidsGame game;
    private IShipBuildingView.PartSelectionView curView;
    private HashMap<IShipBuildingView.PartSelectionView,String> descriptionMap;
    private Ship myShip;

    public ShipBuildingController(IShipBuildingView view) {
        this.view = view;
        SingletonModel model = SingletonModel.getInstance();
        game = model.getGame();
        myShip = new Ship();

        descriptionMap = new HashMap<>();
        descriptionMap.put(IShipBuildingView.PartSelectionView.MAIN_BODY, "Main Body");
        descriptionMap.put(IShipBuildingView.PartSelectionView.EXTRA_PART, "Extra Part");
        descriptionMap.put(IShipBuildingView.PartSelectionView.CANNON, "Cannon");
        descriptionMap.put(IShipBuildingView.PartSelectionView.ENGINE, "Engine");
        descriptionMap.put(IShipBuildingView.PartSelectionView.POWER_CORE, "Power Core");
    }

    @Override
    public void loadContent(ContentManager content) {
        List<Integer> list = new ArrayList<>();
        for(MainBody e : game.getMainBodies()) {
            e.setId(content.loadImage(e.getImage()));
            list.add(e.getId());
        }
        view.setPartViewImageList(IShipBuildingView.PartSelectionView.MAIN_BODY, list);

        list = new ArrayList<>();
        for(Cannon e : game.getCannons()) {
            e.setId(content.loadImage(e.getImage()));
            list.add(e.getId());
        }
        view.setPartViewImageList(IShipBuildingView.PartSelectionView.CANNON, list);

        list = new ArrayList<>();
        for(ExtraPart e : game.getExtraParts()) {
            e.setId(content.loadImage(e.getImage()));
            list.add(e.getId());
        }
        view.setPartViewImageList(IShipBuildingView.PartSelectionView.EXTRA_PART, list);

        list = new ArrayList<>();
        for(Engine e : game.getEngines()) {
            e.setId(content.loadImage(e.getImage()));
            list.add(e.getId());
        }
        view.setPartViewImageList(IShipBuildingView.PartSelectionView.ENGINE, list);

        list = new ArrayList<>();
        for(PowerCore e : game.getPowerCores()) {
            e.setId(content.loadImage(e.getImage()));
            list.add(e.getId());
        }
        view.setPartViewImageList(IShipBuildingView.PartSelectionView.POWER_CORE, list);
    }

    @Override
    public void unloadContent(ContentManager content) {
        for(MainBody e : game.getMainBodies()) {
            content.unloadImage(e.getId());
            e.setId(0);
        }

        for(Cannon e : game.getCannons()) {
            content.unloadImage(e.getId());
            e.setId(0);
        }

        for(ExtraPart e : game.getExtraParts()) {
            content.unloadImage(e.getId());
            e.setId(0);
        }

        for(Engine e : game.getEngines()) {
            content.unloadImage(e.getId());
            e.setId(0);
        }

        for(PowerCore e : game.getPowerCores()) {
            content.unloadImage(e.getId());
            e.setId(0);
        }
    }

    private IShipBuildingView.PartSelectionView CalcView(IShipBuildingView.ViewDirection dir) {
//        dir = switchDirection(dir);
        IShipBuildingView.PartSelectionView tempView = null;
        switch(curView) {
            case MAIN_BODY:
                tempView = dir == IShipBuildingView.ViewDirection.LEFT ? IShipBuildingView.PartSelectionView.EXTRA_PART : (dir == IShipBuildingView.ViewDirection.RIGHT ? IShipBuildingView.PartSelectionView.CANNON : null);
                break;
            case CANNON:
                tempView = dir == IShipBuildingView.ViewDirection.LEFT ? IShipBuildingView.PartSelectionView.MAIN_BODY : (dir == IShipBuildingView.ViewDirection.RIGHT ? IShipBuildingView.PartSelectionView.ENGINE : null);
                break;
            case ENGINE:
                tempView = dir == IShipBuildingView.ViewDirection.LEFT ? IShipBuildingView.PartSelectionView.CANNON : (dir == IShipBuildingView.ViewDirection.RIGHT ? IShipBuildingView.PartSelectionView.POWER_CORE : null);
                break;
            case POWER_CORE:
                tempView = dir == IShipBuildingView.ViewDirection.LEFT ? IShipBuildingView.PartSelectionView.ENGINE : (dir == IShipBuildingView.ViewDirection.RIGHT ? IShipBuildingView.PartSelectionView.EXTRA_PART : null);
                break;
            case EXTRA_PART:
                tempView = dir == IShipBuildingView.ViewDirection.LEFT ? IShipBuildingView.PartSelectionView.POWER_CORE : (dir == IShipBuildingView.ViewDirection.RIGHT ? IShipBuildingView.PartSelectionView.MAIN_BODY : null);
                break;
        }
        return tempView;
    }

    @Override
    public void onViewLoaded(IShipBuildingView.PartSelectionView partView) {
        curView = partView;
        view.setArrow(partView, IShipBuildingView.ViewDirection.UP, false, "");
        view.setArrow(partView, IShipBuildingView.ViewDirection.DOWN, false, "");
        view.setArrow(partView, IShipBuildingView.ViewDirection.LEFT, true, descriptionMap.get(CalcView(IShipBuildingView.ViewDirection.LEFT)));
        view.setArrow(partView, IShipBuildingView.ViewDirection.RIGHT, true, descriptionMap.get(CalcView(IShipBuildingView.ViewDirection.RIGHT)));
    }

    @Override
    public void update(double elapsedTime) {
        myShip.setRotation(myShip.getRotation() + 0.5f);
    }

    public void draw() {
        myShip.draw();
    }

    @Override
    public void onSlideView(IShipBuildingView.ViewDirection direction) {
        direction = switchDirection(direction);
        view.animateToView(CalcView(direction),direction);
    }

    private IShipBuildingView.ViewDirection switchDirection(IShipBuildingView.ViewDirection dir) {
        switch (dir) {
            case UP: return IShipBuildingView.ViewDirection.DOWN;
            case DOWN: return IShipBuildingView.ViewDirection.UP;
            case LEFT: return IShipBuildingView.ViewDirection.RIGHT;
            case RIGHT: return IShipBuildingView.ViewDirection.LEFT;
        }
        return null;
    }

    @Override
    public void onPartSelected(int index) {
        switch(curView) {
            case MAIN_BODY: myShip.setMainbody(game.getMainBodies().get(index)); break;
            case CANNON: myShip.setCannon(game.getCannons().get(index)); break;
            case ENGINE: myShip.setEngine(game.getEngines().get(index)); break;
            case POWER_CORE: myShip.setPowercore(game.getPowerCores().get(index)); break;
            case EXTRA_PART: myShip.setExtrapart(game.getExtraParts().get(index)); break;
        }

        view.setStartGameButton(myShip.full());
    }

    @Override
    public void onStartGamePressed() {
        SingletonModel model = SingletonModel.getInstance();
        model.setShip(myShip);
        view.startGame();
    }

    @Override
    public void onResume() {

    }

    @Override
    public IView getView() {
        return view;
    }

    @Override
    public void setView(IView view) {

    }
}
