package com.example.engg1420project;



class Weapon {
    private final String name;
    private final int price;
    private final int attackPower;

    public Weapon(String name, int price, int attackPower) {
        this.name = name;
        this.price = price;
        this.attackPower = attackPower;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getAttackPower() {
        return attackPower;
    }
}

