package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Direction;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

public class Gear extends FieldAction {

    private Direction direction;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {

        if (direction == Direction.RIGHT) {
            gameController.turnRight(space.getPlayer());
        } else if (direction == Direction.LEFT){
            gameController.turnLeft(space.getPlayer());
        }

        return true;
    }

}
