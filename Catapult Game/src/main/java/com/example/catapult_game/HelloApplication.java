package com.example.catapult_game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class HelloApplication extends Application  {
    // Application Variables
    static int applicationX = 1000;
    static int applicationY = 480;

    // Mouse Variables
    double mouseX;
    double mouseY;
    static boolean mouseDown = false;

    // Ball Variables
    double circleXDirection;
    double circleYDirection;
    double circleX;
    double circleY;
    double gravity = 0.01;
    Color ballColor = Color.RED;

    boolean temp = true;

    // Catapult Variables
    double catapultX;
    double catapultY;
    Color catapultColor = Color.BLACK;

    public void start(Stage stage) {
        Pane root = new Pane();

        // Creates Catapult
        Rectangle rectangle = getRectabgle();
        rectangle.setFill(catapultColor);
        root.getChildren().add(rectangle);

        // Creates new Circle
        Circle circle = getCircle();
        circle.setFill(ballColor);
        root.getChildren().add(circle);

        // Moves circle to mouse
        root.setOnMouseMoved(event -> {
             mouseX = event.getX();
             mouseY = event.getY();

            // Make sure ball does not move away from bottom left
            if(((double) applicationX / 6) > mouseX && ((double) (3 * applicationY) / 4) < mouseY && !mouseDown)
            {
                circle.setCenterX(mouseX);
                circle.setCenterY(mouseY);

                circleX = circle.getCenterX();
                circleY = circle.getCenterY();
            }

            if (mouseDown)
            {
                if(temp)
                {
                    circleXDirection = (catapultX - circleX) / 50;
                    circleYDirection = (catapultY - circleY) / 50;
                    temp = false;
                }

                System.out.println(circleXDirection);
                System.out.println(circleYDirection);

                circleX += circleXDirection;
                circleY += circleYDirection;

                circleYDirection += gravity;

                circle.setCenterX(circleX);
                circle.setCenterY(circleY);
            }
        });

        // Settings for Application
        Scene scene = new Scene(root, applicationX, applicationY);
        stage.setTitle("Circle Follow Cursor");
        stage.setScene(scene);
        stage.show();
    }

    // Creates Catapult
    private Rectangle getRectabgle() {
        Rectangle rectangle = new Rectangle((double) applicationX / 6, (double) (3 * applicationY) / 4, 10, 50);
        rectangle.setFill(Color.BLACK);

        catapultX = rectangle.getX();
        catapultY = rectangle.getY();

        return rectangle;
    }

    // Creates Circle and gets mouse clicks
    private static Circle getCircle() {
        // Creates Circle
        Circle circle = new Circle(0, 0, 10);

        // On Left Click Press
        circle.setOnMousePressed(event -> {if (event.getButton() == MouseButton.PRIMARY) mouseDown = true;});

        // On Left Click Release
        // circle.setOnMouseReleased(event -> {if (event.getButton() == MouseButton.PRIMARY) mouseDown = false;});

        return circle;
    }


    // Launches the application
    public static void main(String[] args) { launch(); }
}