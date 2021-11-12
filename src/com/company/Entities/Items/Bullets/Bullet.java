package com.company.Entities.Items.Bullets;

import com.company.Entities.Entity;
import com.company.Entities.Items.Item;
import com.company.Obstacles.Obstacle;
import org.newdawn.slick.Graphics;

import java.util.List;

import static java.lang.Math.*;

public class Bullet extends Item {         //Plus pratique
    int attackDamage;
    Entity sender;

    public Bullet(){
        super(1);                        //A optimiser
    }

    public Bullet(int quantity){
        super(quantity);                        //A optimiser
    }

    public Bullet(float x, float y, float angle, int attackDamage, Entity sender) {
        this();     //Bonne pratique ?
        this.x = x;
        this.y = y;         //redondance

        this.angle = angle;         //Necessaire au projectile pour se diriger
        size = 20;
        speedMax = 5;

        this.attackDamage = attackDamage;
        this.sender = sender;

        vx = (float) (speedMax*cos(angle));
        vy = (float) (speedMax*sin(angle));
    }

    @Override
    public void display(Graphics g) {

    }

    public void move(List<? extends Obstacle> obstacles, List<? extends Entity>... entities){   //A resoudre
        if(super.collision(obstacles, vx, vy, false) == null) {
            x += vx;
            y += vy;
        }
        else dead = true;

        Entity target = null;

        for (List<? extends Entity> targets : entities) {
            Entity trans = super.collision(targets, 0, 0);
            if(trans != null) target = trans;
        }

        if(target != null && target != sender) {        //Pour eviter de se suicider en tirant
            target.getDamage(attackDamage);
            dead = true;
        }
        //else dead = true;
    }
}
