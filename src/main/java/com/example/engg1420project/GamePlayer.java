package com.example.engg1420project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GamePlayer {
    private final String name;
    private final Map<Weapon, Integer> inventory = new LinkedHashMap<>();
    private String currentCityName;
    private int money;
    private int power;
    private int treasuresCollected;

    public GamePlayer(String name, String startingCityName) {
        this.name = name;
        this.currentCityName = startingCityName;
        money = GameRules.STARTING_MONEY;
        power = GameRules.STARTING_POWER;
    }

    public String getName() {
        return name;
    }

    public String getCurrentCityName() {
        return currentCityName;
    }

    public void travelTo(String cityName) {
        currentCityName = cityName;
        losePower(GameRules.TRAVEL_POWER_COST);
    }

    public int getMoney() {
        return money;
    }

    public int getPower() {
        return power;
    }

    public int getTreasuresCollected() {
        return treasuresCollected;
    }

    public Map<Weapon, Integer> getInventory() {
        return Collections.unmodifiableMap(inventory);
    }

    public String getInventorySummary() {
        if (inventory.isEmpty()) {
            return "Empty";
        }

        List<String> itemNames = new ArrayList<>();
        for (Map.Entry<Weapon, Integer> entry : inventory.entrySet()) {
            itemNames.add(entry.getKey().getName() + " x" + entry.getValue());
        }

        return String.join(", ", itemNames);
    }

    public int getAttackPower() {
        int attackPower = power;
        for (Map.Entry<Weapon, Integer> entry : inventory.entrySet()) {
            attackPower += entry.getKey().getAttackPower() * entry.getValue();
        }
        return attackPower;
    }

    public boolean canAfford(int price) {
        return money >= price;
    }

    public boolean spendMoney(int amount) {
        if (amount < 0 || money < amount) {
            return false;
        }

        money -= amount;
        return true;
    }

    public void addWeapon(Weapon weapon) {
        inventory.put(weapon, inventory.getOrDefault(weapon, 0) + 1);
    }

    public boolean removeWeapon(Weapon weapon) {
        Integer quantity = inventory.get(weapon);
        if (quantity == null || quantity <= 0) {
            return false;
        }

        if (quantity == 1) {
            inventory.remove(weapon);
        } else {
            inventory.put(weapon, quantity - 1);
        }

        return true;
    }

    public void collectTreasure(int value) {
        treasuresCollected++;
        money += value;
    }

    public void collectMoney(int amount) {
        money += amount;
    }

    public void triggerTrap(int moneyLost, int powerLost) {
        loseMoney(moneyLost);
        losePower(powerLost);
    }

    public void recoverPower(int amount) {
        power += amount;
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
}
