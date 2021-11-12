package com.company.Entities.Animals;

import com.company.Entities.Items.Item;
import com.company.Entities.Items.Food.Meat;
import com.company.ToolBox.RandomNumber;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.List;

import static java.lang.Math.*;

public class Pig extends Animal implements Passive{
    public static float spawnRate = 0.05F;

    public Pig(float x, float y){
        super(x, y);

        size = 25;
    }

    public void display(Graphics g){
        float EA = (float)PI/5;

        g.setColor(Color.pink);
        g.fillOval(x-size/2, y-size/2, size, size, 6);          //Ajouter un rotate pour faire un cochon plus jolie

        g.setColor(new Color(50));
        g.fillOval(x+size/2*(float)cos(angle-EA)-size/4, y+size/2*(float)sin(angle-EA)-size/4, size/2, size/2);
        g.fillOval(x+size/2*(float)cos(angle+EA)-size/4, y+size/2*(float)sin(angle+EA)-size/4, size/2, size/2);
    }

    @Override
    public void generateLoot(List<Item> items) {
        RandomNumber rand = new RandomNumber();

        Meat loot;
        loot = new Meat((int) rand.generate(1, 3));
        loot.x = x;
        loot.y = y;
        loot.isCollected = false;

        items.add(loot);
    }
}
