package com.example.engg1420project;

public class Battle {

    int player1Strength;
    int player2Strength;
    int player1Money;
    int player2Money;


    public Battle(int player1Strength, int player2Strength, int player1Money, int player2Money) {
        this.player1Strength = player1Strength;
        this.player2Strength = player2Strength;
        this.player1Money = player1Money;
        this.player2Money = player2Money;
    }

    public void moneyAndPowerTransfer () {
        if (player1Strength > player2Strength) {
            player1Money += ((player1Strength - player2Strength)/(player1Strength + player2Strength)) * player2Money;
            player1Strength = player1Strength - player2Strength;
            player2Strength = 0;
        } else {
            player2Money += ((player2Strength - player1Strength)/(player2Strength + player1Strength)) * player1Money;
            player2Strength = player2Strength - player1Strength;
            player1Strength = 0;
        }
    }



}
