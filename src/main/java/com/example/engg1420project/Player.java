package com.example.engg1420project;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.HashMap;
import java.util.Map;

import static com.example.engg1420project.TrapHouse.*;
import static com.example.engg1420project.TrapHouse.getPowerLost;

public class Player {
    private ImageView imageView;
    private StackPane marker;
    private int cellSize;

    public int x; // Current x position of the player
    public int y; // Current y position of the player

    private int money;
    private int power;

    private Map<String, Integer> inventory;

    public Player(String imagePath, int cellSize, int initalMoney, int intialPower) {
        this.cellSize = cellSize;

        // Load player image
        Image image = new Image(getClass().getResource(imagePath).toString());
        imageView = new ImageView(image);
        imageView.setFitWidth(cellSize);
        imageView.setFitHeight(cellSize);
        marker = new StackPane(imageView);

        //initial x and y positions of the player
        x = 0;
        y = 0;


        money = initalMoney;
        power = intialPower;
        inventory = new HashMap<>();
    }


    //method to get the imageview of the player
    public ImageView getImageView() {
        return imageView;
    }

    public StackPane getMarker() {
        return marker;
    }

    public void setMapLabel(String labelText) {
        Label label = new Label(labelText);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        label.setTextFill(Color.WHITE);
        label.setStyle("-fx-background-color: rgba(0, 0, 0, 0.75); -fx-padding: 1 4 1 4;");
        StackPane.setAlignment(label, javafx.geometry.Pos.BOTTOM_CENTER);
        marker.getChildren().add(label);
    }

    //methods to move player in specific directions
    public void moveUp() {
        if (y > 0) {
            y--;
            updatePosition();
        }
    }

    public void moveDown() {
        if (y < MainMap.GRID_SIZE - 1) {
            y++;
            updatePosition();
        }
    }

    public void moveLeft() {
        if (x > 0) {
            x--;
            updatePosition();
        }
    }

    public void moveRight() {
        if (x < MainMap.GRID_SIZE - 1) {
            x++;
            updatePosition();
        }
    }

    public void moveToCity(CityGraph.City city) {
        setPosition(city.getX(), city.getY());
    }

    public void setPosition(int x, int y) {
        if (x < 0 || x >= MainMap.GRID_SIZE || y < 0 || y >= MainMap.GRID_SIZE) {
            throw new IllegalArgumentException("Player position is outside the map.");
        }

        this.x = x;
        this.y = y;
        updatePosition();
    }

    //method to update the position depending on the
    private void updatePosition() {
        //sets the column index and row index of the player
        GridPane.setColumnIndex(marker, x);
        GridPane.setRowIndex(marker, y);


    }

    // method to enter trap house and handle consequences for entering trap house
    public void enterTrapHouse(TrapHouse trapHouse) {

        System.out.println("Entering Trap house....");
        money = money - getMoneyLost();
        power = power - getPowerLost();
        System.out.println("Lost money: "+ getMoneyLost()+"Lost power: "+ getPowerLost());
}

    // method to buy a weapon
    public boolean buyWeapon(String weaponName, int price) {
        if (money >= price) {
            money -= price;
            inventory.put(weaponName, inventory.getOrDefault(weaponName, 0) + 1);
            System.out.println("Bought " + weaponName + " for $" + price);
            return true;
        } else {
            System.out.println("Insufficient funds to buy " + weaponName);
            return false;
        }
    }

    // method to sell a weapon
    public boolean sellWeapon(String weaponName, int price) {
        int quantity = inventory.getOrDefault(weaponName, 0);
        if (quantity > 0) {
            inventory.put(weaponName, quantity - 1);
            if (inventory.get(weaponName) == 0) {
                inventory.remove(weaponName);
            }
            money += price;
            System.out.println("Sold " + weaponName + " for $" + price);
            return true;
        } else {
            System.out.println("No " + weaponName + " in inventory to sell");
            return false;
        }
    }

    public void loseMoney(int amount) {
        money = Math.max(0, money - amount);
    }

    public void losePower(int amount) {
        power -= amount;
    }

    public boolean isAlive() {
        return power > 0;
    }

    public String getInventorySummary() {
        if (inventory.isEmpty()) {
            return "Empty";
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            sb.append(entry.getKey()).append(" (").append(entry.getValue()).append("), ");
        }
        return sb.toString().replaceAll(", $", "");
    }


    // method to enter trap house



    // method to interact with wall house
    public void interactWithWallHouse(){
        System.out.println("You encountered a wall house. No assets are inside.");
    }

    // Method to get player's money
    public int getMoney() {
        return money;
    }

    // method to get players power
    public int getPower(){
        return power;
    }

}
