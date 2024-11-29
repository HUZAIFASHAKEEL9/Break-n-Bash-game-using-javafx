package com.example.breaknbash;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MapGenerator {

    public int map[][];
    public int brickWidth;
    public int brickHeight;

    // Constructor to initialize the map with rows and columns
    public MapGenerator(int row, int col) {
        map = new int[row][col];
        brickWidth = 540 / col; // Set the width of each brick
        brickHeight = 150 / row; // Set the height of each brick

        // Initialize the map with bricks (1 means brick is present)
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                map[i][j] = 1; // 1 means a brick exists
            }
        }
    }

    // Method to draw the bricks on the canvas
    public void draw(GraphicsContext gc) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    // Draw the brick only if it is present (i.e., value is greater than 0)
                    gc.setFill(Color.RED);
                    gc.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    gc.setStroke(Color.BLACK);
                    gc.strokeRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    // Method to change the brick value (to mark it as broken)
    public void setBrickValue(int value, int row, int col) {
        map[row][col] = value;
    }
}
