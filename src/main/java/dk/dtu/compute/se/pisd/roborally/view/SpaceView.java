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
import dk.dtu.compute.se.pisd.roborally.controller.*;
import dk.dtu.compute.se.pisd.roborally.model.Direction;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

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

    private void updateCheckpoint() {
        Checkpoint checkpoint = space.getChekpoint();
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

    }

    @Override
    public void updateView(Subject subject) {
        if (subject == this.space) {
            this.getChildren().clear();
            updateHole();
            updateBelt();
            updateFastConveyor();
            updateGear();
            updateCheckpoint();
        }
        updatePlayer();
    }


    private void updateBelt() {
        ConveyorBelt belt = space.getConveyorBelt();
        if (belt != null) {

            Polygon fig = new Polygon(0.0, 0.0,
                    60.0, 0.0,
                    30.0, 60.0);

            fig.setFill(Color.BLUE);

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
                fig.setRotate((90));
            } else fig.setRotate(270);

            this.getChildren().add(fig);
        }
    }

    public void updateHole(){
        Hole hole = space.getHole();
        if (hole != null) {
            Rectangle fig = new Rectangle(0.0,0.0,75.0 ,75.0 );

            fig.setFill(Color.BLACK);
            this.getChildren().add(fig);
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
