package com.example.engg1420project;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MarketEconomy {
    private static final double SELL_RATE = 0.65;

    private final MarketHouse marketHouse;

    public MarketEconomy(MarketHouse marketHouse) {
        this.marketHouse = marketHouse;
    }

    public List<MarketListing> getListings(String cityName, int day) {
        List<MarketListing> listings = new ArrayList<>();
        for (Weapon weapon : marketHouse.getAvailableWeapons()) {
            int buyPrice = calculateBuyPrice(weapon, cityName, day);
            int sellPrice = Math.max(1, (int) Math.round(buyPrice * SELL_RATE));
            listings.add(new MarketListing(weapon, buyPrice, sellPrice));
        }
        return listings;
    }

    public int getBuyPrice(Weapon weapon, String cityName, int day) {
        return calculateBuyPrice(weapon, cityName, day);
    }

    public int getSellPrice(Weapon weapon, String cityName, int day) {
        return Math.max(1, (int) Math.round(calculateBuyPrice(weapon, cityName, day) * SELL_RATE));
    }

    private int calculateBuyPrice(Weapon weapon, String cityName, int day) {
        int basePrice = marketHouse.getBasePrice(weapon);
        Random seededRandom = new Random((cityName + weapon.getName()).hashCode() * 31L + day);
        double cityMultiplier = 0.85 + seededRandom.nextDouble() * 0.45;
        double dayMultiplier = 0.90 + seededRandom.nextDouble() * 0.25;
        return Math.max(1, (int) Math.round(basePrice * cityMultiplier * dayMultiplier));
    }

    public static class MarketListing {
        private final Weapon weapon;
        private final int buyPrice;
        private final int sellPrice;

        public MarketListing(Weapon weapon, int buyPrice, int sellPrice) {
            this.weapon = weapon;
            this.buyPrice = buyPrice;
            this.sellPrice = sellPrice;
        }

        public Weapon getWeapon() {
            return weapon;
        }

        public String getWeaponName() {
            return weapon.getName();
        }

        public int getBuyPrice() {
            return buyPrice;
        }

        public int getSellPrice() {
            return sellPrice;
        }
    }
}
