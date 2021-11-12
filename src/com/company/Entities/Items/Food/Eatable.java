package com.company.Entities.Items.Food;

import com.company.Entities.Hero;
import com.company.Entities.Items.Item;

public abstract class Eatable extends Item {
    protected int hungerGive = 20;

    public Eatable(int quantity) {
        super(quantity);
    }

    public Eatable(int quantity, String name) {
        super(quantity, name);
    }

    public void eat(Hero eater) {       //A continuer
        eater.updateHunger(hungerGive);
        quantity--;
    }
}
