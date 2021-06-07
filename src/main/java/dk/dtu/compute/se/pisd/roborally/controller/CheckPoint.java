package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

public class CheckPoint extends FieldAction {
    private int number;

public CheckPoint (int number){
this.number= number;
}

    @Override
   public boolean doAction(@NotNull GameController gameController, @NotNull Space space){
Player player = space.getPlayer();
player.setCheckpoint_nr(number);


   return true;
    }
}
