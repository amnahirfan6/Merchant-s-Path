package com.example.engg1420project;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class TreasureHouse {

    private static final int GRID_SIZE = 10;

    public int value;
    public String name;
    int x;
    int y;

    public TreasureHouse(int value, String name, int x, int y) {
        this.value = value;
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public static TreasureHouse spawnTreasure(GridPane grid, String name, int value) {

        Random random = new Random();

        int xT1 = random.nextInt(GRID_SIZE); // x for Treasure 1
        int yT1 = random.nextInt(GRID_SIZE);
        TreasureHouse newTreasure = new TreasureHouse(value, name, xT1, yT1);
        Rectangle cell = new Rectangle(MainMap.getCellSize(), MainMap.getCellSize());
        cell.setFill(Color.GREEN); // Using the first color for red blocks
        grid.add(cell, xT1, yT1);

        return newTreasure;
    }


}
