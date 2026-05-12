package com.example.engg1420project;

// traphouse class for trap locations in game
public class TrapHouse {
    private static int moneyLost; // money lost in trap
    private static int powerLost; // power lost in trap

    // constructor to initialize money lost and power lost
    public TrapHouse (int moneyLost, int powerLost){
        TrapHouse.moneyLost = moneyLost;
        TrapHouse.powerLost = powerLost;
    }

    //method to get how much money is lost
    public static int getMoneyLost() {
        return moneyLost;
    }

    // method to get how much power is lost
    public static int getPowerLost(){
        return powerLost;
    }


}
