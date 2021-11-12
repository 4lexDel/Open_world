package com.company.Entities.Animals;

import com.company.Entities.Entity;
import com.company.Game;
import com.company.Entities.Items.Item;
import com.company.Lootable;
import com.company.Obstacles.Obstacle;
import com.company.ToolBox.RandomNumber;

import java.util.List;

import static java.lang.Math.*;

public class Animal extends Entity implements Lootable {
    private int delayChangeDirection;       //Intervalle de temps entre les changements de direction
    private int count;
    private int FPS = Game.FPS;

    public Animal(float x, float y){
        super(x, y);

        speedMax = 1;           //Plus lent
        size = 30;              //plus petit

        RandomNumber rand = new RandomNumber();

        delayChangeDirection = (int) rand.generate(1.5F*FPS,3*FPS);                     //A Lié au FPS !!
        count = delayChangeDirection-1;
    }

    public void move(List<? extends Obstacle> obstacles) {            //Jouer avec l'angle et la vitesse pour plus de douceur'
        int E = 30;

        count++;
        RandomNumber rand = new RandomNumber();
        if(count%delayChangeDirection==0) {                 //Timer
            count = 0;

            vx = rand.generate(-speedMax, speedMax);        //Changement de direction
            vy = rand.generate(-speedMax, speedMax);
        }
        else{
            vx += rand.generate(-speedMax/E, speedMax/E);       //Variations
            vy += rand.generate(-speedMax/E, speedMax/E);
        }

        if(collision(obstacles, vx, vy, true) == null) {         //Collisions testé par anticipation
            x += vx;
            y += vy;
        }

        angle = (float) atan(vy/vx);            //Actualisation de l'angle
        if(vx<0) angle+= PI;
    }

    @Override
    public void generateLoot(List<Item> items) {

    }

    public boolean findPath(){              //Algorithme pathfinding (penser au A*)


        return false;
    }
}

