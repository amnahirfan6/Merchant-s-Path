package com.example.engg1420project;

public class PlayerStatus {
    private int treasuresCollected;
    private int moneyEarned;
    private int powerLevel;

    public PlayerStatus(String playerName) {
        this.treasuresCollected = 0;
        this.moneyEarned = 0;
        this.powerLevel = 0;
    }

    public PlayerStatus() {
        treasuresCollected = 0;
        moneyEarned = 0;
        powerLevel = 0;
    }

    // Getters and setters for player status attributes
    public int getTreasuresCollected() {
        return treasuresCollected;
    }

    public void setTreasuresCollected(int treasuresCollected) {
        this.treasuresCollected = treasuresCollected;
    }

    public int getMoneyEarned() {
        return moneyEarned;
    }

    public void setMoneyEarned(int moneyEarned) {
        this.moneyEarned = moneyEarned;
    }

    public int getPowerLevel() {
        return powerLevel;
    }

    public void setPowerLevel(int powerLevel) {
        this.powerLevel = powerLevel;
    }

    @Override
    public String toString() {
        return "Treasures Collected: " + treasuresCollected +
                ", Money Earned: " + moneyEarned +
                ", Power Level: " + powerLevel;
    }
}
