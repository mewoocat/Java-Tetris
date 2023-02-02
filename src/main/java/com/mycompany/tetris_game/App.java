/*
        scene.setOnKeyPressed(e ->{
            //shape.setTranslateX(shape.getTranslateX() + 10);
            root.getChildren().removeIf(n -> {
                Block node = (Block) n;
                return node.active;
            });
        });
*/

package com.mycompany.tetris_game;

import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;


public class App extends Application {
    
    final int BORDER_EDGE = 20;
    final int GRID_WIDTH = 260;
    final int GRID_HEIGHT = 520;
    final int SCREEN_WIDTH = 360;
    final int SCREEN_Height = 560;
    
    public Pane root = new Pane();
    public Pane scorePane = new Pane();
    Scene scene = new Scene(root);
    Grid grid = new Grid(root, scorePane);
    
    @Override
    public void start(Stage stage) {
        
        root.setPrefSize(SCREEN_WIDTH, SCREEN_Height);
        Block border = new Block(BORDER_EDGE, BORDER_EDGE, 260, 520, 0, 0, Color.BLACK);
        border.setStroke(Color.BLACK);
        border.setFill(Color.TRANSPARENT);
        border.active = false;
        root.getChildren().add(border);
        
        /*
        Label scoreText = new Label("Score");
        scoreText.setLayoutX(305);
        scoreText.setLayoutY(40);
        root.getChildren().add(scoreText);
        
        this.scoreNumber.setLayoutX(305);
        this.scoreNumber.setLayoutY(55);
        */

        scene.setOnKeyPressed(e ->{
            switch (e.getCode()){
                case A:
                    grid.moveLeft();
                    break;
                case D:
                    grid.moveRight();
                    break;
                case S:
                    grid.moveDown();
                    break;
                case W:
                    grid.drop();
                    break;
                case SPACE:
                    grid.rotate();
                    break;
                default:
                    break;
            }
        });
        
        Timeline tl = new Timeline(
            new KeyFrame(
                Duration.millis(1000),
                event -> {
                    grid.update();
                }
            )
        );
        tl.setCycleCount(Animation.INDEFINITE);
        tl.play();  
        
        stage.setScene(scene);
        stage.show();
    } 
    
    public static void main(String[] args) {
        launch();
    }
}