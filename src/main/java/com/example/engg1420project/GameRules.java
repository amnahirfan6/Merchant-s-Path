package com.example.engg1420project;


public final class GameRules {
    public static final int STARTING_MONEY = 100;
    public static final int STARTING_POWER = 100;
    public static final int MAX_DAYS = 15;
    public static final int TREASURES_TO_WIN = 3;
    public static final int TRAVEL_POWER_COST = 2;
    public static final int REST_POWER_RECOVERY = 15;
public static final int POTION_COST = 50;
public static final int POTION_POWER_GAIN = 20;

    public static final int MIN_TREASURE_VALUE = 20;
    public static final int MAX_TREASURE_VALUE = 50;
    public static final int MIN_TRAP_MONEY_LOSS = 10;
    public static final int MAX_TRAP_MONEY_LOSS = 25;
    public static final int MIN_TRAP_POWER_LOSS = 8;
    public static final int MAX_TRAP_POWER_LOSS = 17;

    public static final int TREASURE_EVENT_CHANCE = 35;
    public static final int TRAP_EVENT_CHANCE = 25;

    public static final String START_CITY = "Oakport";
    public static final String GOAL_CITY = "Castleford";

    private GameRules() {
    }
}
