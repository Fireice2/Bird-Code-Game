package com.example.catapult_game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class HelloApplication extends Application {
    int applicationX = 640;
    int applicationY = 480;

    double mouseX;
    double mouseY;

    Color ballColor = Color.RED;

    public void start(Stage stage) {
        Pane root = new Pane();
        Circle circle = new Circle(10);
        circle.setFill(ballColor);
        root.getChildren().add(circle);

        root.setOnMouseMoved(event -> {
             mouseX = event.getX();
             mouseY = event.getY();
            if(((double) applicationX / 4) > mouseX && ((double) (3 * applicationY) / 4) < mouseY)
            {
                circle.setCenterX(mouseX);
                circle.setCenterY(mouseY);
            }
        });

        Scene scene = new Scene(root, applicationX, applicationY);

        stage.setTitle("Circle Follow Cursor");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(); }
}