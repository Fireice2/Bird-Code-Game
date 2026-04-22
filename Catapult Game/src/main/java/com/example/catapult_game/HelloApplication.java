package com.example.catapult_game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.Random;

/*
Zachary Finger
Cameron Stone

P.1

Version 2
*/

public class HelloApplication extends Application {

    // Brick Structure class
    static class Brick {
        Rectangle rect;
        double velocityY = 0;
        boolean active = true;

        Brick(double x, double y, double w, double h, Color color) {
            rect = new Rectangle(x, y, w, h);
            rect.setFill(color);
        }
    }
    final ArrayList<Brick> bricks = new ArrayList<>();
    double brickHeight = 25, brickWidth = 50, brickGap = 10;

    // Window Settings
    final String Title = "Catapult Game";
    static final int applicationX = 1000, applicationY = 480;

    // Win & Lose Variables
    final String winString = "You Win!", loseString = "You Lose!";
    static boolean win = false, lose = false;
    final Color winBoxColor = Color.GREEN, loseBoxColor = Color.RED;
    double winAmount, winBoxX, winBoxY, winBoxWidth = 500, winBoxHeight = 240;

    // Score Variables
    final String scoreString = "Score: ", ballsString = "Balls: ";
    double score = 0, givenScore = 5, currentBalls = 0;

    // Mouse Variables
    double mouseX, mouseY;
    static boolean mouseDown = false;

    // Catapult Variables
    double catapultY, catapultX, catapultHeight = 75, catapultWidth = 10;

    // Ball Variables
    static double circleX, circleY, circleXDirection, circleYDirection, circleRadius = 10, circleOpacity = 1, gravity = 0.25;
    static boolean temp = true, hit = false;

    // Level Variables
    int[][] currentLevel;
    final int[][] level1 = {
            {0,0,0,1,0,0},
             {0,0,1,1,0,0},
            {0,0,1,1,1,0},
             {0,1,1,1,1,0},
            {0,1,1,0,1,1},
             {1,1,0,0,1,1}
    };
    final int[][] level2 = {
            {0,1,1,1,1,1},
             {1,1,1,1,1,1},
            {0,1,1,0,1,1},
             {1,1,0,0,1,1},
            {0,1,0,0,0,1},
             {1,0,0,0,0,1}
    };
    final int[][] level3 = {
            {0,1,0,1,0,1},
             {1,0,1,1,0,1},
            {0,1,0,1,0,1},
             {1,0,1,1,0,1},
            {0,1,0,1,0,1},
             {1,0,1,1,0,1}
    };
    final int[][] level4 = {
            {0,0,1,1,1,1},
             {0,1,1,0,1,1},
            {0,0,1,0,0,1},
             {0,1,0,0,0,1},
            {0,0,0,0,0,0},
             {0,1,0,0,0,1}
    };
    final int[][] level5 = {
            {1,0,0,0,0,0,0,0,0},
             {1,0,0,0,0,0,0,0,0},
            {1,0,1,0,0,0,0,0,0},
             {1,0,1,0,0,0,0,0,0},
            {1,0,1,0,1,0,0,0,0},
             {1,0,1,0,1,0,0,0,0},
            {1,0,1,0,1,0,1,0,0},
             {1,0,1,0,1,0,1,0,0},
            {1,0,1,0,1,0,1,0,1},
             {1,0,1,0,1,0,1,0,1}
    };
    final int[][] level6 = {
            {0,0,0,0,1,1,1,0,0},
             {0,0,0,0,1,1,0,0,0},
            {0,0,0,0,1,1,1,0,0},
             {0,0,0,0,1,1,0,0,0},
            {0,0,0,0,1,1,1,0,0},
             {0,0,0,0,1,1,0,0,0},
            {0,0,0,0,1,1,1,0,0},
             {0,0,0,0,1,1,0,0,0},
            {0,0,0,0,1,1,1,0,0},
             {0,0,0,0,1,1,0,0,0},
            {0,0,0,0,1,1,1,0,0},
             {0,0,0,0,1,1,0,0,0},
            {0,0,0,0,1,1,1,0,0},
             {0,0,0,0,1,1,0,0,0},
            {0,0,0,0,1,1,1,0,0},
             {0,0,0,0,1,1,0,0,0},
            {0,0,0,0,1,1,1,0,0},
             {0,0,0,0,1,1,0,0,0},
            {0,0,0,0,1,1,1,0,0}
    };

    @Override
    public void start(Stage stage) {
        // Variables for future use
        Pane root = new Pane();
        winBoxX = (applicationX - winBoxWidth) / 2;
        winBoxY = (applicationY - winBoxHeight) / 2;

        // UI Setup
        TextField scoreText = createTextField(0,0,120,40, scoreString + score);
        TextField ballText = createTextField(120,0,120,40, ballsString + currentBalls);
        root.getChildren().addAll(scoreText, ballText);

        // Catapult Setup
        Rectangle catapult = getCatapult();

        Rotate rotate = new Rotate();
        rotate.setPivotX(catapult.getWidth()/2);
        rotate.setPivotY(catapult.getHeight());
        catapult.getTransforms().add(rotate);

        root.getChildren().add(catapult);

        // Ball Setup
        Circle circle = getCircle();
        root.getChildren().add(circle);

        // Win & Lose box Setup
        Rectangle winBox = createRectangle(winBoxX, winBoxY, winBoxWidth, winBoxHeight, winBoxColor);
        TextField winText = createTextField(winBoxX, winBoxY, winBoxWidth, winBoxHeight, "");
        winText.setVisible(false);
        winText.setStyle("-fx-font-size:30px;-fx-alignment:center;-fx-background-color:transparent;");
        winText.setEditable(false);

        // Next Button Setup
        Button nextButton = new Button("Next Level");
        nextButton.setLayoutX(winBoxX + 20);
        nextButton.setLayoutY(winBoxY + winBoxHeight - 60);
        nextButton.setVisible(false);

        // Reset Button Setup
        Button resetButton = new Button("Reset Level");
        resetButton.setLayoutX(winBoxX + winBoxWidth - 140);
        resetButton.setLayoutY(winBoxY + winBoxHeight - 60);
        resetButton.setVisible(false);

        // Anchor everything to win & lose box
        root.getChildren().addAll(winBox, winText, nextButton, resetButton);

        // Button actions
        nextButton.setOnAction(_ -> loadLevel(root, winBox, winText, nextButton, resetButton, true));
        resetButton.setOnAction(_ -> loadLevel(root, winBox, winText, nextButton, resetButton, false));

        // Get mouse position
        root.setOnMouseMoved(event -> {
            mouseX = event.getX();
            mouseY = event.getY();

            double pivotX = catapult.getLayoutX() + catapultWidth / 2;
            double pivotY = catapult.getLayoutY() + catapultHeight;

            double angle = Math.toDegrees(
                    Math.atan2(mouseY - pivotY, mouseX - pivotX)
            );

            rotate.setAngle(angle + 90);
        });

        root.setOnMousePressed(e -> {
            if(e.getButton() == MouseButton.PRIMARY)
            {
                mouseDown = true;
                hit = false;
            }
        });

        // Starts update loop
        Timeline loop = createTimeline(Duration.millis(16), circle, scoreText, ballText, winBox, winText, nextButton, resetButton);
        loop.play();

        // Load level
        loadLevel(root, winBox, winText, nextButton, resetButton, true);

        // Settings for application window
        Scene scene = new Scene(root, applicationX, applicationY);
        stage.setScene(scene);
        stage.setTitle(Title);
        stage.setResizable(false);
        stage.show();
    }

    // Update loop
    Timeline createTimeline(Duration interval, Circle circle, TextField scoreText, TextField ballText, Rectangle winBox, TextField winText, Button nextButton, Button resetButton) {
        KeyFrame keyFrame = new KeyFrame(interval, _ -> {
            updateBricks();
            updateCircle(circle);
            updateUI(scoreText, ballText, circle, winBox, winText, nextButton, resetButton);
            if(!mouseDown && !hit)
            {
                circle.setCenterX(20);
                circle.setCenterY(applicationY-20);

                resetCircle();
            }
        });
        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);

        return timeline;
    }

    // Updates brick gravity, collision, & position
    void updateBricks() {
        for (Brick brick : bricks) {
            if (!brick.active) continue;
            brick.velocityY += gravity;
            double newY = brick.rect.getY() + brick.velocityY;
            double maxY = applicationY - brickHeight;

            for (Brick other : bricks) {
                if (other == brick || !other.active) continue;
                boolean xOverlap = brick.rect.getX() < other.rect.getX()+brickWidth && brick.rect.getX()+brickWidth > other.rect.getX();
                boolean landing = brick.rect.getY()+brickHeight <= other.rect.getY() && newY+brickHeight >= other.rect.getY();
                if (xOverlap && landing) maxY = Math.min(maxY, other.rect.getY()-brickHeight);
            }

            if (newY >= maxY) { brick.rect.setY(maxY); brick.velocityY = 0; } else { brick.rect.setY(newY); }
        }
    }

    // Updates ball position, velocity, gravity, collision, & how many ball sare left
    void updateCircle(Circle circle) {
        if (!mouseDown) return;
        if (temp) {
            circleXDirection = (mouseX - circleX) / 15;
            circleYDirection = (mouseY - circleY) / 15;
            
            temp = false;
            currentBalls--;
        }
        circleX += circleXDirection;
        circleY += circleYDirection;
        circleYDirection += gravity;
        circle.setCenterX(circleX);
        circle.setCenterY(circleY);

        if(circleY >= applicationY || circleY <= 0 || circleX >= applicationX || circleY <= 0) resetCircle();

        for(Brick brick : bricks) {
            if(!brick.active) continue;
            if(brick.rect.getBoundsInParent().contains(circleX, circleY)) {
                brick.active = false;
                brick.rect.setOpacity(0);
                score += givenScore;
                hit = true;
                resetCircle();
            }
        }
    }

    // Updates UI (points and balls left)
    void updateUI(TextField scoreText, TextField ballText, Circle circle, Rectangle winBox, TextField winText, Button nextButton, Button resetButton) {
        scoreText.setText(scoreString + score);
        ballText.setText(ballsString + currentBalls);
        circle.setOpacity(circleOpacity);

        if(lose) winBox.setFill(loseBoxColor);
        if(win) winBox.setFill(winBoxColor);
        if(win || lose) {
            winBox.setVisible(true);
            winText.setVisible(true);
            winText.setText(win ? winString : loseString);
            if(win) nextButton.setVisible(true);
            resetButton.setVisible(true);
            winBox.toFront();
            winText.toFront();
            nextButton.toFront();
            resetButton.toFront();
        }
    }

    // Creates inputted text boxes
    TextField createTextField(double x, double y, double w, double h, String text) {
        TextField tf = new TextField(text);
        tf.setPrefSize(w,h);
        tf.relocate(x,y);
        return tf;
    }

    // Creates catapult
    Rectangle getCatapult() {
        Rectangle rect = new Rectangle(catapultWidth, catapultHeight);
        rect.relocate( 0, applicationY - catapultHeight);

        catapultX = rect.getLayoutX();
        catapultY = rect.getLayoutY();
        return rect;
    }

    // Creates inputted rectangles
    Rectangle createRectangle(double x, double y, double w, double h, Color color) {
        Rectangle rect = new Rectangle(x,y,w,h);
        rect.setFill(color);
        rect.setOpacity(0);
        return rect;
    }

    // Creates inputted circles (ball)
    static Circle getCircle() {
        Circle circle = new Circle(0,0,circleRadius);
        return circle;
    }

    // Resets circle position & velocity when collided with something
    void resetCircle() {
        circleXDirection = circleYDirection = 0;
        circleX = 5;
        circleY = applicationY-5;
        temp = true;
        mouseDown = false;
        if(score == winAmount) win = true;
        if(currentBalls <= 0 && !win) lose = true;
    }

    // Loads, reloads, & randomizes levels
    void loadLevel(Pane root, Rectangle winBox, TextField winText, Button nextButton, Button resetButton, boolean random) {
        // Clear previous bricks
        for (Brick b : bricks) root.getChildren().remove(b.rect);
        bricks.clear();
        win = false; lose = false; score = 0;

        // Hide win & reset UI
        winBox.setVisible(false); winText.setVisible(false);
        nextButton.setVisible(false);
        resetButton.setVisible(false);

        // Select a level
        int[][] level;
        if(random) {
            int[][][] levels = {level5, level6};
            level = levels[new Random().nextInt(levels.length)];
            currentLevel = level;
        } else {
            level = currentLevel;
        }

        // Ramdom color & brick generator
        Random rand = new Random();
        winAmount = 0;
        for(int row=0; row<level.length; row++){
            for(int col=0; col<level[row].length; col++){
                if(level[row][col]==1){
                    double offset = (row%2==0)?0:brickWidth/2;
                    double brickX = applicationX - (col * (brickWidth + brickGap) + offset + brickWidth);
                    double brickY = applicationY - (row * (brickHeight+1000) + 50);
                    Color color = Color.rgb(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
                    Brick brick = new Brick(brickX, brickY, brickWidth, brickHeight, color);
                    bricks.add(brick);
                    root.getChildren().add(brick.rect);
                    winAmount += givenScore;
                }
            }
        }
        currentBalls = bricks.size() + Math.ceil(bricks.size()/5.0);
    }

    // launches the application
    public static void main(String[] args) { launch(); }
}