package dk.dtu.compute.se.pisd.roborally.controller.BoardElements;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

public class ConveyorBeltSouth extends FieldAction {
    public GameController gamecontroller;

    private Heading heading = Heading.SOUTH;

    public Heading getHeading() {
        return heading;
    }

    //public void setHeading(Heading heading) { this.heading = heading;


    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
        // TODO needs to be implemented

        gameController.moveForward(space.getPlayer(), heading);

        return true;
    }

}


