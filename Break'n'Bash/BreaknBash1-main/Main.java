package com.example.breaknbash;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static javafx.application.Platform.exit;

public class Main extends Application {

    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    private int playerX = 310;
    private int ballPosX = 120;
    private int ballPosY = 320;
    private int ballDirX = -1;
    private int ballDirY = -2;

    private MapGenerator map;

    @Override
    public void start(Stage primaryStage) {
        map = new MapGenerator(3, 7);
        Canvas canvas = new Canvas(700, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // AnimationTimer for game loop
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (play) {
                    updateGame();
                    renderGame(gc);
                }
            }
        }.start();

        Scene scene = new Scene(new StackPane(canvas), 700, 600);
        scene.setOnKeyPressed(event -> handleKeyPress(event.getCode()));

        primaryStage.setTitle("Brick Breaker!");
        primaryStage.setScene(scene);
        primaryStage.show();

        startGame();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void startGame() {
        play = true;
        ballPosX = 120;
        ballPosY = 320;
        ballDirX = -4;
        ballDirY = -4;
        playerX = 310;
        score = 0;
        totalBricks = 24;
        map = new MapGenerator(4, 6);
    }

    public void updateGame() {
        if (new Rectangle2D(ballPosX, ballPosY, 20, 20).intersects(new Rectangle2D(playerX, 550, 100, 8))) {
            ballDirY = -ballDirY;
        }

        for (int i = 0; i < map.map.length; i++) {
            for (int j = 0; j < map.map[0].length; j++) {
                if (map.map[i][j] > 0) {
                    int brickX = j * map.brickWidth + 60;
                    int brickY = i * map.brickHeight + 40;
                    int brickWidth = map.brickWidth;
                    int brickHeight = map.brickHeight;

                    Rectangle2D brickRect = new Rectangle2D(brickX, brickY, brickWidth, brickHeight);
                    if (new Rectangle2D(ballPosX, ballPosY, 20, 20).intersects(brickRect)) {
                        map.setBrickValue(0, i, j);
                        totalBricks--;
                        score += 10;

                        if (ballPosX + 19 <= brickRect.getMinX() || ballPosX + 1 >= brickRect.getMaxX()) {
                            ballDirX = -ballDirX;
                        } else {
                            ballDirY = -ballDirY;
                        }
                    }
                }
            }
        }

        ballPosX += ballDirX;
        ballPosY += ballDirY;

        if (ballPosX < 0 || ballPosX > 680) {
            ballDirX = -ballDirX;
        }
        if (ballPosY < 0) {
            ballDirY = -ballDirY;
        }
        if (ballPosY > 570) {
            play = false;
            ballDirX = 0;
            ballDirY = 0;
        }
    }

    public void renderGame(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 700, 600);

        map.draw(gc);

        gc.setFill(Color.YELLOW);
        gc.fillRect(0, 0, 3, 600);
        gc.fillRect(0, 0, 700, 3);
        gc.fillRect(697, 0, 3, 600);

        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 350, 30);

        gc.setFill(Color.BLUEVIOLET);
        gc.fillRect(playerX, 550, 100, 8);

        gc.setFill(Color.YELLOW);
        gc.fillOval(ballPosX, ballPosY, 20, 20);

        if (totalBricks <= 0) {
            gc.setFill(Color.RED);
            gc.fillText("You Won!", 300, 300);
            gc.fillText("Press ENTER to restart", 250, 350);
            exit();
        }

        if (ballPosY > 570) {
            gc.setFill(Color.RED);
            gc.fillText("Game Over! Your score: " + score, 200, 300);
            gc.fillText("Press ENTER to restart", 250, 350);
        }
    }

    public void handleKeyPress(KeyCode key) {
        if (key == KeyCode.RIGHT) {
            if (playerX < 600) {
                playerX += 60;
            }
        }
        if (key == KeyCode.LEFT) {
            if (playerX > 10) {
                playerX -= 60;
            }
        }
        if (key == KeyCode.ENTER) {
            if (!play) {
                startGame();
            }
        }
    }
}
