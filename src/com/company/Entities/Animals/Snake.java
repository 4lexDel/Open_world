package com.company.Entities.Animals;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import static java.lang.Math.*;

public class Snake extends Intelligent {
    public static float spawnRate = 0.01F;

    public Snake(float x, float y) {
        super(x, y);
        size = 20;
    }

    @Override
    public void display(Graphics g) {
        float EA = (float)PI/5;

        g.setColor(Color.green);
        g.fillOval(x-size/2, y-size/2, size, size, 6);          //Ajouter un rotate pour faire un cochon plus jolie

        g.setColor(new Color(50));
        g.fillOval(x+size/2*(float)cos(angle-EA)-size/4, y+size/2*(float)sin(angle-EA)-size/4, size/2, size/2);
        g.fillOval(x+size/2*(float)cos(angle+EA)-size/4, y+size/2*(float)sin(angle+EA)-size/4, size/2, size/2);
    }
}
