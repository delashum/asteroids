package edu.byu.cs.superasteroids.main_menu;

import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.main_menu.IMainMenuController;
import edu.byu.cs.superasteroids.model.AsteroidsGame;
import edu.byu.cs.superasteroids.model.Ship;
import edu.byu.cs.superasteroids.model.SingletonModel;

/**
 * Created by Jacob on 10/13/2016.
 */
public class MainMenuController implements IMainMenuController {

    private IMainMenuView view;
    private IView iView;

    public MainMenuController(IMainMenuView view) {
        this.view = view;
    }

    @Override
    public void onQuickPlayPressed() {
        Ship ship = new Ship();

        AsteroidsGame game = SingletonModel.getInstance().getGame();
        ship.setMainbody(game.getMainBodies().get(0));
        ship.setExtrapart(game.getExtraParts().get(0));
        ship.setCannon(game.getCannons().get(0));
        ship.setPowercore(game.getPowerCores().get(0));
        ship.setEngine(game.getEngines().get(0));

        SingletonModel.getInstance().setShip(ship);

        view.startGame();
    }

    @Override
    public IView getView() {
        return iView;
    }

    @Override
    public void setView(IView view) {
        this.iView = view;
    }
}
