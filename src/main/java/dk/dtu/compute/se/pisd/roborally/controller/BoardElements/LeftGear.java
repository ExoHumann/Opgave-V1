package dk.dtu.compute.se.pisd.roborally.controller.BoardElements;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class LeftGear extends FieldAction {


    @Override
    public boolean doAction(GameController gameController, Space space) {
        gameController.turnLeft(space.getPlayer());
        return true;
    }
}
