package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

public class Hole extends FieldAction {
    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {

        gameController.hole(space.getPlayer());

        return true;
    }
}
