package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.RoboRally;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AppControllerTest {
    private GameController gameController;
    RoboRally roborally= new RoboRally();
    AppController appController = new AppController(roborally);

    @Test
    void exitGame() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Assertions.assertEquals(alert.getTitle(), "Exit RoboRally?");
        appController.exit();


        alert.setContentText("Are you sure you want to exit RoboRally?");
    }
}
