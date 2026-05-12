package com.example.engg1420project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameSession {
    private final List<GamePlayer> players;
    private final CityGraph cityGraph;
    private final MarketHouse marketHouse;
    private final MarketEconomy marketEconomy;
    private final Random random;
    private final Die die;

    private int day = 1;
    private int currentPlayerIndex = 0;
    private int currentRoll;
    private int remainingTravelPoints;
    private String lastEventMessage = "No events yet.";

    public GameSession(List<GamePlayer> players) {
        this.players = new ArrayList<>(players);
        cityGraph = CityGraph.createDefaultMap();
        marketHouse = new MarketHouse();
        marketEconomy = new MarketEconomy(marketHouse);
        random = new Random();
        die = new Die();
        rollForCurrentTurn();
    }

    public static GameSession createSinglePlayer(String playerName) {
        return new GameSession(List.of(new GamePlayer(playerName, GameRules.START_CITY)));
    }

    public static GameSession createTwoPlayerMapGame() {
        return new GameSession(List.of(
                new GamePlayer("Player 1", GameRules.START_CITY),
                new GamePlayer("Player 2", "Riverbend")
        ));
    }

    public List<GamePlayer> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public GamePlayer getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public int getDay() {
        return day;
    }

    public String getLastEventMessage() {
        return lastEventMessage;
    }

    public int getCurrentRoll() {
        return currentRoll;
    }

    public int getRemainingTravelPoints() {
        return remainingTravelPoints;
    }

    public CityGraph getCityGraph() {
        return cityGraph;
    }

    public MarketHouse getMarketHouse() {
        return marketHouse;
    }

    public List<MarketEconomy.MarketListing> getCurrentMarketListings() {
        return marketEconomy.getListings(getCurrentPlayer().getCurrentCityName(), day);
    }

    public List<CityGraph.City> getConnectedCitiesForCurrentPlayer() {
        return cityGraph.getConnectedCities(getCurrentPlayer().getCurrentCityName());
    }

    public void travelCurrentPlayerTo(CityGraph.City destination) {
        GamePlayer movingPlayer = getCurrentPlayer();
        String startingCityName = movingPlayer.getCurrentCityName();

        if (remainingTravelPoints <= 0) {
            lastEventMessage = movingPlayer.getName() + " has no travel points left.";
            return;
        }

        if (!cityGraph.hasConnection(startingCityName, destination.getName())) {
            lastEventMessage = "Invalid travel choice. " + destination.getName()
                    + " is not connected to " + startingCityName + ".";
            return;
        }

        movingPlayer.travelTo(destination.getName());
        remainingTravelPoints--;
        lastEventMessage = movingPlayer.getName() + " traveled to " + destination.getName()
                + ", spent 1 travel point, and spent " + GameRules.TRAVEL_POWER_COST + " power.";

        if (movingPlayer.isAlive()) {
            resolveCityEvent(movingPlayer, destination);
        } else {
            lastEventMessage += "\n" + movingPlayer.getName() + " ran out of power.";
        }

        if (remainingTravelPoints == 0) {
            lastEventMessage += "\nNo travel points left. Turn ended.";
            advanceTurn();
        }
    }

    public Weapon findMarketWeaponByName(String weaponName) {
        for (Weapon weapon : marketHouse.getAvailableWeapons()) {
            if (weapon.getName().equals(weaponName)) {
                return weapon;
            }
        }
        return null;
    }

    public boolean buyCurrentPlayerWeapon(String weaponName) {
        GamePlayer player = getCurrentPlayer();
        Weapon weapon = findMarketWeaponByName(weaponName);
        if (weapon == null) {
            lastEventMessage = weaponName + " is not available in this market.";
            return false;
        }

        int price = marketEconomy.getBuyPrice(weapon, player.getCurrentCityName(), day);
        if (!player.spendMoney(price)) {
            lastEventMessage = player.getName() + " could not afford " + weaponName
                    + " for $" + price + ".";
            return false;
        }

        player.addWeapon(weapon);
        lastEventMessage = player.getName() + " purchased " + weaponName
                + " for $" + price + ".";
        return true;
    }

    public boolean sellCurrentPlayerWeapon(String weaponName) {
        GamePlayer player = getCurrentPlayer();
        Weapon weapon = findMarketWeaponByName(weaponName);
        if (weapon == null) {
            lastEventMessage = weaponName + " is not available in this market.";
            return false;
        }

        int price = marketEconomy.getSellPrice(weapon, player.getCurrentCityName(), day);

        if (!player.removeWeapon(weapon)) {
            lastEventMessage = player.getName() + " does not have " + weaponName + " to sell.";
            return false;
        }

        player.collectMoney(price);
        lastEventMessage = player.getName() + " sold " + weaponName
                + " for $" + price + ".";
        return true;
    }

    public void restCurrentPlayer() {
        GamePlayer player = getCurrentPlayer();
        player.recoverPower(GameRules.REST_POWER_RECOVERY);
        lastEventMessage = player.getName() + " rested and recovered "
                + GameRules.REST_POWER_RECOVERY + " power.";
        advanceTurn();
    }

    public void endCurrentTurn() {
        lastEventMessage = getCurrentPlayer().getName() + " ended the turn with "
                + remainingTravelPoints + " travel point(s) unused.";
        advanceTurn();
    }

    public boolean hasWon() {
        for (GamePlayer player : players) {
            if (player.getCurrentCityName().equals(GameRules.GOAL_CITY)
                    && player.getTreasuresCollected() >= GameRules.TREASURES_TO_WIN) {
                return true;
            }
        }

        return false;
    }

    public boolean hasLost() {
        if (day > GameRules.MAX_DAYS) {
            return true;
        }

        for (GamePlayer player : players) {
            if (player.isAlive()) {
                return false;
            }
        }

        return true;
    }

    private void resolveCityEvent(GamePlayer player, CityGraph.City city) {
        if (city.getName().equals(GameRules.GOAL_CITY)) {
            lastEventMessage += "\nReached " + GameRules.GOAL_CITY + ".";
            return;
        }

        int eventRoll = random.nextInt(100);
        if (eventRoll < GameRules.TREASURE_EVENT_CHANCE) {
            int treasureValue = randomValue(GameRules.MIN_TREASURE_VALUE, GameRules.MAX_TREASURE_VALUE);
            player.collectTreasure(treasureValue);
            lastEventMessage += "\nFound treasure worth $" + treasureValue + ".";
        } else if (eventRoll < GameRules.TREASURE_EVENT_CHANCE + GameRules.TRAP_EVENT_CHANCE) {
            int moneyLost = randomValue(GameRules.MIN_TRAP_MONEY_LOSS, GameRules.MAX_TRAP_MONEY_LOSS);
            int powerLost = randomValue(GameRules.MIN_TRAP_POWER_LOSS, GameRules.MAX_TRAP_POWER_LOSS);
            player.triggerTrap(moneyLost, powerLost);
            lastEventMessage += "\nTrap! Lost $" + moneyLost + " and " + powerLost + " power.";
        } else {
            lastEventMessage += "\nThe city was quiet.";
        }
    }

    private void advanceTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        if (currentPlayerIndex == 0) {
            day++;
        }
        rollForCurrentTurn();
    }

    private void rollForCurrentTurn() {
        currentRoll = die.roll();
        remainingTravelPoints = currentRoll;
        lastEventMessage += "\n" + getCurrentPlayer().getName() + " rolled a "
                + currentRoll + " for travel points.";
    }

    private int randomValue(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }
}
