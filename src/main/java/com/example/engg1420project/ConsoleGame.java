package com.example.engg1420project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleGame {
    private final ConsoleUI ui;

    private GameSession session;
    private boolean running;

    public ConsoleGame() {
        Scanner scanner = new Scanner(System.in);
        ui = new ConsoleUI(scanner);
    }

    public void run() {
        ui.title("Traveling Salesman");
        showMainMenu();
    }

    private void showMainMenu() {
        boolean menuOpen = true;

        while (menuOpen) {
            ui.section("Main Menu");
            int choice = ui.choose("Choose an option:", List.of(
                    "Start new game",
                    "How to play",
                    "Quit"
            ));

            switch (choice) {
                case 1:
                    startNewGame();
                    break;
                case 2:
                    showHelp();
                    break;
                case 3:
                    menuOpen = false;
                    ui.message("Goodbye.");
                    break;
                default:
                    break;
            }
        }
    }

    private void startNewGame() {
        String playerName = ui.readText("Enter player name:");
        session = GameSession.createSinglePlayer(playerName);
        running = true;

        ui.message("Welcome, " + getPlayer().getName()
                + ". Your journey begins in " + GameRules.START_CITY + ".");
        playTurns();
    }

    private void playTurns() {
        while (running) {
            if (hasWon() || hasLost()) {
                endGame();
                return;
            }

            showTurnHeader();
            int choice = ui.choose("Choose an action:", List.of(
                    "Travel",
                    "Visit shop",
                    "View stats",
                    "View inventory",
                    "Rest",
                    "End turn",
                    "Return to main menu"
            ));

            switch (choice) {
                case 1:
                    travel();
                    break;
                case 2:
                    visitShop();
                    break;
                case 3:
                    showStats();
                    break;
                case 4:
                    showInventory();
                    break;
                case 5:
                    rest();
                    break;
                case 6:
                    endTurn();
                    break;
                case 7:
                    running = false;
                    break;
                default:
                    break;
            }
        }
    }

    private void showTurnHeader() {
        GamePlayer player = getPlayer();
        ui.title("Day " + session.getDay() + " of " + GameRules.MAX_DAYS);
        ui.message("Location: " + player.getCurrentCityName());
        ui.message("Money: $" + player.getMoney() + " | Power: " + player.getPower()
                + " | Treasures: " + player.getTreasuresCollected() + "/" + GameRules.TREASURES_TO_WIN);
        ui.message("Die roll: " + session.getCurrentRoll()
                + " | Travel points left: " + session.getRemainingTravelPoints());
        ui.message("Inventory: " + player.getInventorySummary());
        ui.message("Last event: " + session.getLastEventMessage());
    }

    private void travel() {
        if (session.getRemainingTravelPoints() <= 0) {
            ui.message("No travel points left. End your turn or rest.");
            return;
        }

        List<CityGraph.City> connectedCities = session.getConnectedCitiesForCurrentPlayer();
        List<String> options = new ArrayList<>();

        for (CityGraph.City city : connectedCities) {
            options.add(city.getName());
        }
        options.add("Cancel");

        ui.section("Travel");
        ui.message("You can travel only to connected cities.");
        int choice = ui.choose("Destination:", options);

        if (choice == options.size()) {
            ui.message("Travel cancelled.");
            return;
        }

        CityGraph.City destination = connectedCities.get(choice - 1);
        session.travelCurrentPlayerTo(destination);
        ui.message(session.getLastEventMessage());
    }

    private void visitShop() {
        ui.section("Shop");
        GamePlayer player = getPlayer();
        ui.message("City: " + player.getCurrentCityName() + " | Day: " + session.getDay()
                + " | Money: $" + player.getMoney());
        int choice = ui.choose("Choose shop action:", List.of(
                "Buy items",
                "Sell items",
                "Leave shop"
        ));

        switch (choice) {
            case 1:
                buyFromShop();
                break;
            case 2:
                sellToShop();
                break;
            default:
                ui.message("You leave the shop.");
                break;
        }
    }

    private void buyFromShop() {
        List<MarketEconomy.MarketListing> listings = session.getCurrentMarketListings();
        List<String> options = new ArrayList<>();

        for (MarketEconomy.MarketListing listing : listings) {
            String weaponName = listing.getWeaponName();
            options.add(formatBuyListing(weaponName, listing.getBuyPrice(), listing.getSellPrice()));
        }
        options.add("Back");

        ui.section("Buy Items");
        int choice = ui.choose("Choose an item to buy:", options);
        if (choice == options.size()) {
            return;
        }

        String selectedWeaponName = listings.get(choice - 1).getWeaponName();
        if (!session.buyCurrentPlayerWeapon(selectedWeaponName)) {
            ui.message(session.getLastEventMessage());
            return;
        }

        ui.message(session.getLastEventMessage());
    }

    private void sellToShop() {
        GamePlayer player = getPlayer();
        if (player.getInventory().isEmpty()) {
            ui.message("Inventory is empty. You have nothing to sell.");
            return;
        }

        List<Weapon> inventoryWeapons = new ArrayList<>(player.getInventory().keySet());
        List<String> options = new ArrayList<>();

        for (Weapon weapon : inventoryWeapons) {
            int quantity = player.getInventory().get(weapon);
            int sellPrice = getSellPriceForCurrentCity(weapon.getName());
            options.add(weapon.getName() + " x" + quantity + " | Sell price $" + sellPrice);
        }
        options.add("Back");

        ui.section("Sell Items");
        int choice = ui.choose("Choose an item to sell:", options);
        if (choice == options.size()) {
            return;
        }

        String selectedWeaponName = inventoryWeapons.get(choice - 1).getName();
        session.sellCurrentPlayerWeapon(selectedWeaponName);
        ui.message(session.getLastEventMessage());
    }

    private String formatBuyListing(String weaponName, int buyPrice, int sellPrice) {
        return weaponName
                + " | Buy $" + buyPrice
                + " | Sell $" + sellPrice;
    }

    private int getSellPriceForCurrentCity(String weaponName) {
        for (MarketEconomy.MarketListing listing : session.getCurrentMarketListings()) {
            if (listing.getWeaponName().equals(weaponName)) {
                return listing.getSellPrice();
            }
        }

        return 0;
    }

    private void showStats() {
        GamePlayer player = getPlayer();
        ui.section("Player Stats");
        ui.message("Name: " + player.getName());
        ui.message("Current city: " + player.getCurrentCityName());
        ui.message("Day: " + session.getDay() + "/" + GameRules.MAX_DAYS);
        ui.message("Money: $" + player.getMoney());
        ui.message("Power: " + player.getPower());
        ui.message("Attack power: " + player.getAttackPower());
        ui.message("Treasures collected: " + player.getTreasuresCollected() + "/" + GameRules.TREASURES_TO_WIN);
    }

    private void showInventory() {
        GamePlayer player = getPlayer();
        ui.section("Inventory");
        if (player.getInventory().isEmpty()) {
            ui.message("Inventory is empty.");
            return;
        }

        int itemNumber = 1;
        for (Map.Entry<Weapon, Integer> entry : player.getInventory().entrySet()) {
            Weapon weapon = entry.getKey();
            ui.message(itemNumber + ". " + weapon.getName()
                    + " x" + entry.getValue()
                    + " | Attack +" + weapon.getAttackPower()
                    + " | Value $" + weapon.getPrice());
            itemNumber++;
        }
    }

    private void rest() {
        ui.section("Rest");
        session.restCurrentPlayer();
        ui.message(session.getLastEventMessage());
    }

    private void endTurn() {
        ui.section("End Turn");
        session.endCurrentTurn();
        ui.message(session.getLastEventMessage());
    }

    private boolean hasWon() {
        return session.hasWon();
    }

    private boolean hasLost() {
        return session.hasLost();
    }

    private void endGame() {
        ui.title("Game Over");

        if (hasWon()) {
            ui.message("You reached " + GameRules.GOAL_CITY + " with enough treasure. You win!");
        } else if (!getPlayer().isAlive()) {
            ui.message("You ran out of power. You lose.");
        } else {
            ui.message("You ran out of days. You lose.");
        }

        showStats();
    }

    private void showHelp() {
        ui.section("How To Play");
        ui.message("Roll the die at the start of each turn to get travel points.");
        ui.message("Travel between connected cities. Each city-to-city trip costs 1 travel point.");
        ui.message("Collect treasure and manage your power.");
        ui.message("Market prices change by city and day. Buy low, sell high, and track quantities.");
        ui.message("Buying weapons improves your attack power score.");
        ui.message("Win by reaching " + GameRules.GOAL_CITY + " with "
                + GameRules.TREASURES_TO_WIN + " treasures.");
        ui.message("Lose if your power reaches 0 or you pass day " + GameRules.MAX_DAYS + ".");
    }

    private GamePlayer getPlayer() {
        return session.getCurrentPlayer();
    }
}
