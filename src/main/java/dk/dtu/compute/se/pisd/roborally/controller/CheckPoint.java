package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

public class CheckPoint extends FieldAction {
    public int number;

    @Override
   public boolean doAction(@NotNull GameController gameController, @NotNull Space space){
space.getPlayer();

   return true;
    }
}
