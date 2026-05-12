package com.example.engg1420project;

import java.util.HashMap;
import java.util.Map;

public class Scoreboard {
    private Map<String, PlayerStatus> playerStatusMap;

    public Scoreboard() {
        playerStatusMap = new HashMap<>();
    }

    // Method to add a player to the scoreboard
    public void addPlayer(String playerName) {
        playerStatusMap.put(playerName, new PlayerStatus());
    }

    // Method to update player status (treasures collected, money earned, power level, etc.)
    public void updatePlayerStatus(String playerName, PlayerStatus status) {
        playerStatusMap.put(playerName, status);
    }

    // Method to get player status
    public PlayerStatus getPlayerStatus(String playerName) {
        return playerStatusMap.get(playerName);
    }

    // Method to display the scoreboard
    public void displayScoreboard() {
        System.out.println("Scoreboard:");
        for (Map.Entry<String, PlayerStatus> entry : playerStatusMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
