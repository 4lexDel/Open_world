package com.company.Obstacles.Vegetations;

import com.company.Entities.Items.Item;
import com.company.Entities.Items.Stick;
import com.company.Entities.Items.Wood;
import com.company.ToolBox.RandomNumber;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

import java.util.List;

import static java.lang.Math.*;

public class Tree extends Vegetation {
    public static float spawnRate = 0.03F;

    public Tree(float x, float y){
        super(x, y);
        nb = 100;

        RandomNumber rand = new RandomNumber();
        sizeGlobal = rand.generate(100, 170);       //propres aux arbres

        size = new float[nb];

        for(int i = 0; i<nb; i++) {
            size[i] = rand.generate(6*sizeGlobal/7, sizeGlobal); //random(6*sizeGlobal/7, sizeGlobal);
        }
    }

    public Tree(float x, float y, float sizeGlobal){
        super(x, y);
        nb = 100;

        RandomNumber rand = new RandomNumber();

        this.sizeGlobal = sizeGlobal;

        size = new float[nb];

        for(int i = 0; i<nb; i++) {
            size[i] = rand.generate(6*sizeGlobal/7, sizeGlobal); //random(6*sizeGlobal/7, sizeGlobal);
        }
    }

    public void display(Graphics g){
        float EA = 5;
        g.setColor(new Color(0, 90, 0));
        //g.fillOval(x-sizeGlobal/2,y-sizeGlobal/2,sizeGlobal,sizeGlobal);

        for(int i = 0; i<nb; i++) {
            Polygon polygon;
            polygon = new Polygon();            //JOLIE
            polygon.addPoint(x,y);
            polygon.addPoint((float) (x+size[i]/2*cos((2*PI*(i+1))/nb+PI/EA)), (float)(y+size[i]/2*sin((2*PI*(i+1))/nb+PI/EA)));
            polygon.addPoint((float) (x+size[i]/2*cos((2*PI*(i+1))/nb-PI/EA)), (float)(y+size[i]/2*sin((2*PI*(i+1))/nb-PI/EA)));

            g.fill(polygon);
        }

        super.display(g);
    }

    @Override
    public void generateLoot(List<Item> items) {
        RandomNumber rand = new RandomNumber();

        Wood loot1;                                                 //Ajouter sous forme de liste ?
        loot1 = new Wood((int) rand.generate(2, 5));
        loot1.x = x;
        loot1.y = y;                       //Protocole a automatiser
        loot1.isCollected = false;

        items.add(loot1);

        int nb = (int)(rand.generate(1, 10));           //Bien

        if(nb>7) {
            Stick loot2;
            loot2 = new Stick(1);
            loot2.x = x;
            loot2.y = y;
            loot2.isCollected = false;
            items.add(loot2);
        }
    }
}
