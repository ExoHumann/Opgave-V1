/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
import org.jetbrains.annotations.NotNull;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class GameController {

    final public Board board;

    public GameController(@NotNull Board board) {
        this.board = board;
    }

    /**
     * This is just some dummy controller operation to make a simple move to see something
     * happening on the board. This method should eventually be deleted!
     *
     * @param space the space to which the current player should move
     */
    public void moveCurrentPlayerToSpace(@NotNull Space space) {
        // TODO Assignment V1: method should be implemented by the students:
        //   - the current player should be moved to the given space
        //     (if it is free()
        //   - and the current player should be set to the player
        //     following the current player
        //   - the counter of moves in the game should be increased by one
        //     if the player is moved

        Player p = board.getCurrentPlayer();

        if (space.getPlayer() == null) {
            p.setSpace(space);
            board.getNextPlayer();
        }

    }

    // XXX: V2
    public void startProgrammingPhase() {
        board.setPhase(Phase.PROGRAMMING);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);

        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            if (player != null) {
                for (int j = 0; j < Player.NO_REGISTERS; j++) {
                    CommandCardField field = player.getProgramField(j);
                    field.setCard(null);
                    field.setVisible(true);
                }
                for (int j = 0; j < Player.NO_CARDS; j++) {
                    CommandCardField field = player.getCardField(j);
                    field.setCard(generateRandomCommandCard());
                    field.setVisible(true);
                }
            }
        }
    }

    // XXX: V2
    private CommandCard generateRandomCommandCard() {
        Command[] commands = Command.values();
        int random = (int) (Math.random() * commands.length);
        return new CommandCard(commands[random]);
    }

    // XXX: V2
    public void finishProgrammingPhase() {
        makeProgramFieldsInvisible();
        makeProgramFieldsVisible(0);
        board.setPhase(Phase.ACTIVATION);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);
    }

    // XXX: V2
    private void makeProgramFieldsVisible(int register) {
        if (register >= 0 && register < Player.NO_REGISTERS) {
            for (int i = 0; i < board.getPlayersNumber(); i++) {
                Player player = board.getPlayer(i);
                CommandCardField field = player.getProgramField(register);
                field.setVisible(true);
            }
        }
    }

    // XXX: V2
    private void makeProgramFieldsInvisible() {
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            for (int j = 0; j < Player.NO_REGISTERS; j++) {
                CommandCardField field = player.getProgramField(j);
                field.setVisible(false);
            }
        }
    }

    // XXX: V2
    public void executePrograms() {
        board.setStepMode(false);
        continuePrograms();
    }

    // XXX: V2
    public void executeStep() {
        board.setStepMode(true);
        continuePrograms();
    }

    // XXX: V2
    private void continuePrograms() {
        do {
            executeNextStep();
        } while (board.getPhase() == Phase.ACTIVATION && !board.isStepMode());
    }

    // XXX: V2
    private void executeNextStep() {
        Player currentPlayer = board.getCurrentPlayer();

        if (board.getPhase() == Phase.ACTIVATION && currentPlayer != null) {
            int step = board.getStep();
            if (step >= 0 && step < Player.NO_REGISTERS) {
                CommandCard card = currentPlayer.getProgramField(step).getCard();
                if (card != null) {
                    Command command = card.command;

                    if (command.isInteractive()) {
                        board.setPhase(Phase.PLAYER_INTERACTION);
                        return;
                    }

                    executeCommand(currentPlayer, command);
                }
                int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
                if (nextPlayerNumber < board.getPlayersNumber()) {
                    board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
                } else {
                    for (Player player : this.board.getPlayers()) {
                        for (FieldAction action : player.getSpace().getActions()) {

                            action.doAction(this, player.getSpace());
                        }
                    }

                    step++;



                    if (step < Player.NO_REGISTERS) {
                        makeProgramFieldsVisible(step);
                        board.setStep(step);
                        board.setCurrentPlayer(board.getPlayer(0));
                    } else {
                        startProgrammingPhase();
                    }
                }
            } else {
                // this should not happen
                assert false;
            }
        } else {
            // this should not happen
            assert false;
        }
    }

    // XXX: V2
    private void executeCommand(@NotNull Player player, Command command) {
        if (player != null && player.board == board && command != null) {
            // XXX This is a very simplistic way of dealing with some basic cards and
            //     their execution. This should eventually be done in a more elegant way
            //     (this concerns the way cards are modelled as well as the way they are executed).

            switch (command) {

                case FORWARD:
                case MOVE1:
                    this.moveForward(player, player.getHeading());
                    break;
                case FAST_FORWARD:
                case MOVE2:
                    this.fastForward(player, player.getHeading());
                    break;
                case MOVE3:
                    this.fastForward(player, player.getHeading());
                    this.moveForward(player, player.getHeading());
                    break;
                case POWER_UP:
                    break;
                case RIGHT:
                    this.turnRight(player);
                    break;
                case LEFT:
                    this.turnLeft(player);
                    break;
                case U_TURN:
                    this.uTurn(player);
                    break;
                case OPTION_LEFT_RIGHT:
                default:
                    // DO NOTHING (for now)
            }
        }
    }

    public void executeCommandOptionAndContinue(Command option) {
        Player currentPlayer = board.getCurrentPlayer();

        if (board.getPhase() == Phase.PLAYER_INTERACTION && currentPlayer != null) {
            int step = board.getStep();

            if (step >= 0 && step < Player.NO_REGISTERS) {

                executeCommand(currentPlayer, option);
                board.setPhase(Phase.ACTIVATION);

            }

            int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
            if (nextPlayerNumber < board.getPlayersNumber()) {
                board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));

            } else {
                step++;


                if (step < Player.NO_REGISTERS) {
                    makeProgramFieldsVisible(step);
                    board.setStep(step);
                    board.setCurrentPlayer(board.getPlayer(0));
                } else {
                    startProgrammingPhase();
                }
            }
        }
    }

    // TODO Assignment V2

    /**
     * @param player
     */

    /*
    public void moveForward(@NotNull Player player) {
        Space source = player.getSpace();
        Heading heading = player.getHeading();
        if (source != null && player.board == source.board) {
            Space target = board.getNeighbour(source, heading, 1);
            if (target != null && target.getPlayer() == null) {
                player.setSpace(target); }
            else if (target!=null && target.getPlayer() !=null) {
                moveToSpace(player, target, heading);
                }
            }        }
*/


    // TODO Assignment V2
 //   public void fastForward(@NotNull Player player, int moveAmount) {
 //       Space source = player.getSpace();
 //       if (source != null && player.board == source.board) {
 //           Space target = board.getNeighbour(source, player.getHeading(), moveAmount);
 //           if (source.getWalls().contains(player.getHeading())){
 //               return;}
 //           if (target.getWalls().contains(player.getHeading().facing())){return;}
 //           if (target != null && target.getPlayer() == null) {
 //               player.setSpace(target);
 //              // boardActionFields(target);
 //           }
 //       }
 //
 //   }
    public void fastForward(@NotNull Player player, Heading heading) {
        moveForward(player,heading);
    moveForward(player, heading);}

    // TODO Assignment V2
    public void turnRight(@NotNull Player player) {
        Heading h = player.getHeading();
        player.setHeading(h.next());
    }

    // TODO Assignment V2
    public void turnLeft(@NotNull Player player) {
        Heading h = player.getHeading();
        player.setHeading(h.prev());
    }

    // TODO Assignment V2
    public void uTurn(@NotNull Player player) {
        turnRight(player);
        turnRight(player);
    }

    public boolean moveCards(@NotNull CommandCardField source, @NotNull CommandCardField target) {
        CommandCard sourceCard = source.getCard();
        CommandCard targetCard = target.getCard();
        if (sourceCard != null && targetCard == null) {
            target.setCard(sourceCard);
            source.setCard(null);
            return true;
        } else {
            return false;
        }
    }
    // ConveyorPush();{
    //     moveToSpace(board.getCurrentPlayer(),); }

    /**
     * @param player
     * @param space
     * @param heading
     * @author Ekkart Kindler, ekki@dtu.dk
     */
    void moveToSpace(@NotNull Player player, @NotNull Space space, @NotNull Heading heading) throws ImpossibleMoveException {
        assert board.getNeighbour(player.getSpace(), heading, 1) == space; // make sure the move to here is possible in principle
        Player other = space.getPlayer();
        if (other != null) {
            Space target = board.getNeighbour(space, heading, 1);

           // if (target.getWalls().contains(player.getHeading().facing())){throw new ImpossibleMoveException(player, space, heading);}
            if (target != null) {
                // XXX Note that there might be additional problems with
                //     infinite recursion here (in some special cases)!
                //     We will come back to that!
                moveToSpace(other, target, heading);

                // Note that we do NOT embed the above statement in a try catch block, since
                // the thrown exception is supposed to be passed on to the caller

                assert target.getPlayer() == null : target; // make sure target is free now
            } else {
                throw new ImpossibleMoveException(player, space, heading);
            }
        }
        player.setSpace(space);
        //boardActionFields(space);

    }

 /*   public void moveToSpace(
            @NotNull Player player,
            @NotNull Space space,
            @NotNull Heading heading) {
        Player other=space.getPlayer();
        if (other!=null){
            Space target= board.getNeighbour(space,heading,1);
            if(target!=null) {
    moveToSpace(other,target,heading);
            }
        } player.setSpace(space);
    }
*/


    public void moveForward(@NotNull Player player, Heading heading) {
        if (player.board == board) {
            Space space = player.getSpace();
            //heading = player.getHeading();
            Space target = board.getNeighbour(space, heading, 1);

            if (target != null && !player.getSpace().getWalls().contains(player.getHeading())
                &&!target.getWalls().contains(player.getHeading().facing()))
        {
                try {
                    moveToSpace(player, target, heading);
                } catch (ImpossibleMoveException e) {
                    }


                    // we don't do anything here  for now; we just catch the
                    // exception so that we do no pass it on to the caller
                    // (which would be very bad style).

            }
        }
    }

    public void boardActionFields(Space space) {
        for (Player player :this.board.getPlayers()) {
            for (FieldAction fa : space.getActions()) {

                fa.doAction(this, player.getSpace());


            }

        }
        if (space.x==0 && space.y==0){
            space.getNormalBelt();
        }
    }

    class ImpossibleMoveException extends Exception {

        private Player player;
        private Space space;
        private Heading heading;

        public ImpossibleMoveException(Player player, Space space, Heading heading) {
            super("Move impossible");
            this.player = player;
            this.space = space;
            this.heading = heading;
        }
    }


    /**
     * A method called when no corresponding controller operation is implemented yet. This
     * should eventually be removed.
     */
    public void notImplemented() {
        // XXX just for now to indicate that the actual method is not yet implemented
        assert false;
    }

}
