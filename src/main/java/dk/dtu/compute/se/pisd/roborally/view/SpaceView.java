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
package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.CheckPoint;
import dk.dtu.compute.se.pisd.roborally.controller.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.controller.FastConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.controller.Gear;
import dk.dtu.compute.se.pisd.roborally.model.Direction;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;


/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class SpaceView extends StackPane implements ViewObserver {

    final public static int SPACE_HEIGHT = 75; // 60; // 75;
    final public static int SPACE_WIDTH = 75;  // 60; // 75;

    public final Space space;


    public SpaceView(@NotNull Space space) {
        this.space = space;

        // XXX the following styling should better be done with styles
        this.setPrefWidth(SPACE_WIDTH);
        this.setMinWidth(SPACE_WIDTH);
        this.setMaxWidth(SPACE_WIDTH);

        this.setPrefHeight(SPACE_HEIGHT);
        this.setMinHeight(SPACE_HEIGHT);
        this.setMaxHeight(SPACE_HEIGHT);

updateBelt();
        this.setStyle("-fx-background-color: grey;" + "-fx-border-color: yellow");

        if (space.x == 1 && space.y == 1) {
            this.setStyle("-fx-background-color: black;" + "-fx-border-color: yellow");
        }
        if (space.x == 1 && space.y == 6) {
            setStyle("-fx-background-color: black;" + "-fx-border-color: yellow");
        }
        if (space.x == 6 && space.y == 1) {
            this.setStyle("-fx-background-color: black;" + "-fx-border-color: yellow");
        }
        if (space.x == 6 && space.y == 6) {
            this.setStyle("-fx-background-color: black;" + "-fx-border-color: yellow");
        }


        // updatePlayer();

        // This space view should listen to changes of the space
        space.attach(this);
        update(space);
    }

    private void updatePlayer() {

        Player player = space.getPlayer();
        if (player != null) {
            Circle circle = new Circle(40, 0, 15);
            Polygon arrow = new Polygon(0.0, 0.0,
                    20.0, 40.0,
                    40.0, 0.0);
            try {
                arrow.setFill(Color.valueOf(player.getColor()));
                circle.setFill(Color.valueOf(player.getColor()));
            } catch (Exception e) {
                arrow.setFill(Color.MEDIUMPURPLE);
                circle.setFill(Color.MEDIUMPURPLE);
            }

            arrow.setRotate((90 * player.getHeading().ordinal()) % 360);
            this.getChildren().add(arrow);
            this.getChildren().add(circle);
        }
    }

    @Override
    public void updateView(Subject subject) {
        if (subject == this.space) {
            this.getChildren().clear();
            updateBelt();
            updateBelt();
            updateFastConveyor();
            updateGear();
           // updateCheckpoint();
        }
        updateWalls();
            updatePlayer();

        }

private void updateWalls(){
        Space space = this.space;

       if (!space.getWalls().isEmpty())

         for (Heading wall : space.getWalls()) {

             Pane pane = new Pane();
             Rectangle rectangle = new Rectangle(0.0, 0.0, SPACE_WIDTH, SPACE_HEIGHT);
             rectangle.setFill(Color.TRANSPARENT);
             pane.getChildren().add(rectangle);

         switch (wall) {
             case SOUTH:
                 Line line = new Line(2, SPACE_HEIGHT - 2, SPACE_WIDTH - 2, SPACE_HEIGHT - 2);
                 line.setStroke(Color.RED);
                 line.setStrokeWidth(5);
                 pane.getChildren().add(line);
                 this.getChildren().add(pane);
                 break;

             case NORTH:
                 Line line2 = new Line(2, 2,SPACE_WIDTH-2,2);
                 line2.setStroke(Color.RED);
                 line2.setStrokeWidth(5);
                 pane.getChildren().add(line2);
                 this.getChildren().add(pane);
                 break;

             case WEST:
                 Line line3 = new Line(2, SPACE_HEIGHT-2,2,2);
                 line3.setStroke(Color.RED);
                 line3.setStrokeWidth(5);
                 pane.getChildren().add(line3);
                 this.getChildren().add(pane);
                 break;

             case EAST:
                 Line line4 = new Line(SPACE_WIDTH-2,SPACE_HEIGHT-2, SPACE_WIDTH-2,  - 2);
                line4.setStroke(Color.RED);
                line4.setStrokeWidth(5);
                pane.getChildren().add(line4);
                this.getChildren().add(pane);
                break;


       }
   }
}
    public void updateFastConveyor(){
        FastConveyorBelt belt = space.getFastConveyor();
        if (belt != null) {

            Polygon fig = new Polygon(0.0, 0.0,
                    60.0, 0.0,
                    30.0, 60.0);

            fig.setFill(Color.GREENYELLOW);

            fig.setRotate((90 * belt.getHeading().ordinal()) % 360);
            this.getChildren().add(fig);
        }
    }
    private void updateGear() {
        Gear gear = space.getGear();
        if (gear != null) {
            Polygon fig = new Polygon(0.0, 0.0,
                    60.0, 0.0,
                    30.0, 60.0);

            fig.setFill(Color.RED);

            if (gear.getDirection() == Direction.LEFT){
                fig.setRotate((270));
            } else fig.setRotate(90);

            this.getChildren().add(fig);
        }
    }
  /*  private void updateCheckpoint() {
        CheckPoint checkpoint = space.getCheckpoint();
        if (checkpoint != null) {
            Text text = new Text();
            int t = checkpoint.checkpoints;
            text.setText(Integer.toString(t));
            text.setX(25);
            text.setY(25);
            text.setFill(Color.YELLOWGREEN);
            text.setStroke(Color.YELLOWGREEN);
            text.setStrokeWidth(2);
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            Rectangle fig = new Rectangle(0.0,0.0,75.0 ,75.0 );

            fig.setFill(Color.BLUEVIOLET);
            this.getChildren().add(fig);
            this.getChildren().add(text);

        }

    }*/







    private void updateBelt(){
        ConveyorBelt belt = space.getNormalBelt();
        if (belt != null) {

            Polygon fig = new Polygon(0.0, 0.0,
                    60.0, 0.0,
                    30.0, 60.0);

            fig.setFill(Color.BLUE);

            fig.setRotate((90*belt.getHeading().ordinal())%360);
            this.getChildren().add(fig);
        }

   }


    /**
     * @author Ekkart Kindler, ekki@dtu.dk
     */
    public void setWall() {
        Pane pane = new Pane();
        Rectangle rectangle = new Rectangle(0.0, 0.0, SPACE_WIDTH, SPACE_HEIGHT);
        rectangle.setFill(Color.TRANSPARENT);
        pane.getChildren().add(rectangle);

        Line line = new Line(2, SPACE_HEIGHT - 2, SPACE_WIDTH - 2, SPACE_HEIGHT - 2);
        line.setStroke(Color.RED);
        line.setStrokeWidth(5);
        pane.getChildren().add(line);
        this.getChildren().add(pane);
    }

}
