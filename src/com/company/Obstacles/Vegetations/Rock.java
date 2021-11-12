package com.company.Obstacles.Vegetations;

import com.company.Entities.Items.Item;
import com.company.Entities.Items.Stone;
import com.company.ToolBox.RandomNumber;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

import java.util.List;

import static java.lang.Math.*;

public class Rock extends Vegetation{
    public static float spawnRate = 0.02F;

    public Rock(float x, float y){
        super(x, y);
        nb = 50;

        RandomNumber rand = new RandomNumber();
        sizeGlobal = rand.generate(30, 100);        //Différents

        size = new float[nb];

        for(int i = 0; i<nb; i++) {
            size[i] = rand.generate(4*sizeGlobal/5, sizeGlobal);;//random(4*sizeGlobal/5, sizeGlobal);
        }
    }

    public Rock(float x, float y, float sizeGlobal){
        super(x, y);
        nb = 50;

        RandomNumber rand = new RandomNumber();

        this.sizeGlobal = sizeGlobal;

        size = new float[nb];

        for(int i = 0; i<nb; i++) {
            size[i] = rand.generate(4*sizeGlobal/5, sizeGlobal); //random(6*sizeGlobal/7, sizeGlobal);
        }
    }

    public void display(Graphics g){
        float EA = 3;
        //collision = ralenti
        g.setColor(new Color(90,90,90));
        //g.fillOval(x-sizeGlobal/2,y-sizeGlobal/2,sizeGlobal,sizeGlobal);
        //ellipse(x, y, 200, 200);
        for(int i = 0; i<nb; i++) {
            Polygon polygon;                        //Refaire les cailloux
            polygon = new Polygon();
            polygon.addPoint(x,y);
            polygon.addPoint((float) (x+size[i]/2*cos((2*PI*(i+1))/nb+PI/EA)), (float)(y+size[i]/2*sin((2*PI*(i+1))/nb+PI/EA)));
            polygon.addPoint((float) (x+size[i]/2*cos((2*PI*(i+1))/nb-PI/EA)), (float)(y+size[i]/2*sin((2*PI*(i+1))/nb-PI/EA)));
            polygon.closed();

            g.fill(polygon);
        }
        super.display(g);
    }


    @Override
    public void generateLoot(List<Item> items) {
        RandomNumber rand = new RandomNumber();

        Stone loot;
        loot = new Stone((int) rand.generate(1, 3));
        loot.x = x;
        loot.y = y;
        loot.isCollected = false;

        items.add(loot);
    }
}
