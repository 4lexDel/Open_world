package com.company.ToolBox;

import java.util.Random;

public class RandomNumber {         //Réaliser pou gérer les intervalles
    private Random rand;

    public RandomNumber(){
        rand = new Random();
    }

    public float generate(float max, float min){
        return rand.nextFloat()*(max - min) + min;
    }
}
