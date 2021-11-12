package com.company.Entities.Items;

import org.newdawn.slick.Color;

public enum Material {
    Wood(1 ,new Color(190, 110, 40)), Stone(2, Color.darkGray), Copper(5, Color.orange), Iron(8, Color.gray), Diamonds(20, Color.cyan);

    private final Color color;
    private final int power;

    Material(int power, Color color) {
        this.power = power;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getPower() {
        return power;
    }
}
