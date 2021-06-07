package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

public class Checkpoint extends FieldAction  {

    public final int checkpoints;

    public Checkpoint(int checkpoints) {
        this.checkpoints = checkpoints;
    }

    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space){

        if (space.getPlayer() != null){
            space.getPlayer().checkpointsReached(checkpoints);
        }

        return true;
    }

}
