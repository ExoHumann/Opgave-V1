package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameControllerTest {

    private final int TEST_WIDTH = 8;
    private final int TEST_HEIGHT = 8;

    private GameController gameController;

    @BeforeEach
    void setUp() {
        Board board = new Board(TEST_WIDTH, TEST_HEIGHT);
        gameController = new GameController(board);
        for (int i = 0; i < 6; i++) {
            Player player = new Player(board, null,"Player " + i);
            board.addPlayer(player);
            player.setSpace(board.getSpace(i, i));
            player.setHeading(Heading.values()[i % Heading.values().length]);
        }
        board.setCurrentPlayer(board.getPlayer(0));
    }

    @AfterEach
    void tearDown() {
        gameController = null;
    }

    @Test
    void moveCurrentPlayerToSpace() {
        Board board = gameController.board;
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        gameController.moveCurrentPlayerToSpace(board.getSpace(0, 4));

        Assertions.assertEquals(player1, board.getSpace(0, 4).getPlayer(), "Player " + player1.getName() + " should beSpace (0,4)!");
        Assertions.assertNull(board.getSpace(0, 0).getPlayer(), "Space (0,0) should be empty!");
        Assertions.assertEquals(player2, board.getCurrentPlayer(), "Current player should be " + player2.getName() +"!");
    }

    @Test
    void moveForward() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        gameController.moveForward(current, current.getHeading());

        Assertions.assertEquals(current, board.getSpace(0, 1).getPlayer(), "Player " + current.getName() + " should beSpace (0,1)!");
        Assertions.assertEquals(Heading.SOUTH, current.getHeading(), "Player 0 should be heading SOUTH!");
        Assertions.assertNull(board.getSpace(0, 0).getPlayer(), "Space (0,0) should be empty!");
    }

    @Test
    void Right(){
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        gameController.turnRight(current);
        Assertions.assertEquals(Heading.WEST, current.getHeading(), "Player 0 should be heading WEST!");
    }

    @Test
    void uTurn(){
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();


        gameController.uTurn(current);
        Assertions.assertEquals(Heading.NORTH, current.getHeading(), "Player 0 should be heading NORTH");
    }

    @Test
    void conveyorAction(){
        ConveyorBelt cb= new ConveyorBelt();
        cb.setHeading(Heading.WEST);
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();
        Space space = current.getSpace();
        cb.doAction(gameController, space);
        Space newSpace= current.getSpace();

        Assertions.assertEquals(Heading.WEST, cb.getHeading());
        Assertions.assertEquals(newSpace, board.getNeighbour(space, Heading.WEST, 1));

    }
    @Test
    void fastConveyorAction(){
        FastConveyorBelt cb= new FastConveyorBelt();
        cb.setHeading(Heading.WEST);
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();
        Space space = current.getSpace();
        cb.doAction(gameController, space);
        Space newSpace= current.getSpace();

        Assertions.assertEquals(Heading.WEST, cb.getHeading());
        Assertions.assertEquals(newSpace, board.getNeighbour(space, Heading.WEST, 2));}
    @Test
    void checkPointAction() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();
        Space space = current.getSpace();
        int cp = current.getCheckpoints();
        Checkpoint checkp= new Checkpoint(1);

        space.getActions().add(checkp);

        space.getChekpoint().doAction(gameController, space);

        int updated= current.getCheckpoints();

        Assertions.assertEquals(updated, cp + 1);


    }
    @Test
    void gearAction(){
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();
        Space space = current.getSpace();

        Gear lg = new Gear();
        lg.setDirection(Direction.LEFT);
        Gear rg = new Gear();
        rg.setDirection(Direction.RIGHT);

        Assertions.assertEquals(lg.getDirection(), Direction.LEFT);

        lg.doAction(gameController,space);
        Assertions.assertEquals(Heading.EAST, current.getHeading(), "The heading should be east");

        rg.doAction(gameController,space);
        Assertions.assertEquals(Heading.SOUTH, current.getHeading(), "Heading should now be south once again");

    }
    @Test
    void startProgrammingPhase(){
        gameController.startProgrammingPhase();
        Assertions.assertEquals(gameController.board.getPhase(), Phase.PROGRAMMING, "Phase should be programming");
    }
    @Test
    void finishProgrammingPhase(){
        gameController.finishProgrammingPhase();
        Assertions.assertEquals(gameController.board.getPhase(), Phase.ACTIVATION, "Phase should be Activation");

    }


}
