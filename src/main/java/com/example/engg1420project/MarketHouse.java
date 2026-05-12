package com.example.engg1420project;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MarketHouse {
    private final List<Weapon> availableWeapons;
    private final Map<Weapon, Integer> basePrices;
    private final Map<Weapon, Integer> currentPrices;
    private final Random random = new Random();


    // constructor to initialize markethouse and available weapons
    public MarketHouse() {
        this.availableWeapons = new ArrayList<>();
        this.basePrices = new HashMap<>();
        this.currentPrices = new HashMap<>();

        initializeMarketItems();
        randomizePrices();
    }


    // initialize available weapons in method
    private void initializeMarketItems() {
        addWeapon(new Weapon("Sword", 50, 10));
        addWeapon(new Weapon("Hammer", 70, 15));
        addWeapon(new Weapon("Bow and Arrow", 60, 20));
    }

    // getter to get list of available weapons
    public List<Weapon> getAvailableWeapons() {
        return new ArrayList<>(availableWeapons);
    }

    public int getCurrentPrice(Weapon weapon) {
        return currentPrices.getOrDefault(weapon, 0);
    }

    public int getBasePrice(Weapon weapon) {
        return basePrices.getOrDefault(weapon, 0);
    }

// method to add weapons to available weapons to market if needed
    public void addWeapon(Weapon weapon) {
        availableWeapons.add(weapon);
        basePrices.put(weapon, weapon.getPrice());
    }

// method to remove weapon from available weapons in market if needed
    public void removeWeapon(Weapon weapon) {
        availableWeapons.remove(weapon);
        basePrices.remove(weapon);
        currentPrices.remove(weapon);
    }

    // Randomize prices daily (or per visit)
    public void randomizePrices() {
        for (Weapon weapon : availableWeapons) {
            int base = basePrices.getOrDefault(weapon, weapon.getPrice());
            int variation = random.nextInt(21) - 10; // -10 to +10
            int newPrice = Math.max(1, base + variation);
            currentPrices.put(weapon, newPrice);
        }
    }

    public Weapon findWeaponByName(String weaponName) {
        for (Weapon weapon : availableWeapons) {
            if (weapon.getName().equals(weaponName)) {
                return weapon;
            }
        }
        return null;
    }

    // Buy weapon for player
    public boolean buyWeapon(Player player, String weaponName) {
        Weapon weapon = findWeaponByName(weaponName);
        if (weapon == null) {
            System.out.println("Weapon not available in this market.");
            return false;
        }
        int price = getCurrentPrice(weapon);
        return player.buyWeapon(weaponName, price);
    }

    // Sell weapon from player
    public boolean sellWeapon(Player player, String weaponName) {
        Weapon weapon = findWeaponByName(weaponName);
        if (weapon == null) {
            System.out.println("Weapon not available in this market.");
            return false;
        }
        int price = getCurrentPrice(weapon);
        int sellPrice = price * 4 / 5;
        return player.sellWeapon(weaponName, sellPrice);
    }


}
