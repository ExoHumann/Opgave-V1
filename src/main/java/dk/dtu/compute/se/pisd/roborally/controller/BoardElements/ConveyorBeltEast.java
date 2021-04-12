package dk.dtu.compute.se.pisd.roborally.controller.BoardElements;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

public class ConveyorBeltEast extends FieldAction {
    public GameController gamecontroller;

    private Heading heading = Heading.EAST;

    public Heading getHeading() {
        return heading;
    }

    //public void setHeading(Heading heading) { this.heading = heading;


    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
        // TODO needs to be implemented
        space.getPlayer().setHeading(heading);
        gameController.moveForward(space.getPlayer());

        return true;
    }
}
