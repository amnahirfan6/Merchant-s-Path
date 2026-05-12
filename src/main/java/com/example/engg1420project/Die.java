package com.example.engg1420project;

import java.util.Random;

public class Die {
    private Random random;
    public Die(){
        random = new Random();
    }

    public int roll(){
        return random.nextInt(6) + 1;
    }
}
