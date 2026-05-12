package com.example.engg1420project;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Test extends Application {
    private static final int GRID_SIZE = 12;
    private static final int PLAYABLE_GRID_SIZE = 10;
    private static final int CELL_SIZE = 80;
    private static final int WINDOW_WIDTH = GRID_SIZE * CELL_SIZE;
    private static final int WINDOW_HEIGHT = GRID_SIZE * CELL_SIZE;

    @Override
    public void start(Stage stage) {
        // Create the root stack pane
        StackPane root = new StackPane();

        // Create the 12x12 grid with the border
        GridPane gridWithBorder = createGridWithBorder();
        root.getChildren().add(gridWithBorder);

        // Create the 10x10 grid for the playable area
        GridPane grid = createGrid();
        root.getChildren().add(grid);

        // Load the background image
        Image backgroundImage = new Image(getClass().getResource("/images/map.png").toString());
        ImageView backgroundView = new ImageView(backgroundImage);

        // Clip the background image to fit the playable area
        Rectangle clip = new Rectangle(PLAYABLE_GRID_SIZE * CELL_SIZE, PLAYABLE_GRID_SIZE * CELL_SIZE);
        backgroundView.setClip(clip);

        // Add the background image to the center cell of the playable grid
        grid.add(backgroundView, (GRID_SIZE - PLAYABLE_GRID_SIZE) / 2, (GRID_SIZE - PLAYABLE_GRID_SIZE) / 2);

        // Add a colored rectangle behind the playable area to check its position
        Rectangle coloredRect = new Rectangle(PLAYABLE_GRID_SIZE * CELL_SIZE, PLAYABLE_GRID_SIZE * CELL_SIZE, Color.RED);
        grid.getChildren().add(coloredRect);
        GridPane.setRowIndex(coloredRect, (GRID_SIZE - PLAYABLE_GRID_SIZE) / 2);
        GridPane.setColumnIndex(coloredRect, (GRID_SIZE - PLAYABLE_GRID_SIZE) / 2);

        // Create the scene and set it to the stage
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setTitle("Traveling Salesman Game");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    // Method to create the 12x12 grid with the border
    private GridPane createGridWithBorder() {
        GridPane grid = new GridPane();
        grid.setHgap(1);
        grid.setVgap(1);
        grid.setStyle("-fx-grid-lines-visible: true; -fx-grid-line-color: black;");

        // Loop through each cell in the grid
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                cell.setFill(Color.TRANSPARENT);
                grid.add(cell, i, j);
                if (i == 0 || i == GRID_SIZE - 1 || j == 0 || j == GRID_SIZE - 1) {
                    cell.setFill(Color.GRAY); // Set the border cells to gray
                }
            }
        }
        return grid;
    }

    // Method to create the 10x10 grid for the playable area
    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER); // Center the grid within the StackPane
        grid.setHgap(1);
        grid.setVgap(1);

        // Loop through each cell in the grid
        for (int i = 0; i < PLAYABLE_GRID_SIZE; i++) {
            for (int j = 0; j < PLAYABLE_GRID_SIZE; j++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                cell.setFill(Color.TRANSPARENT);
                grid.add(cell, i, j);
            }
        }
        return grid;
    }

    public static void main(String[] args) {
        launch();
    }
}
