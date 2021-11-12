package com.company.Entities.Items.Tools;

import com.company.Entities.Entity;
import com.company.Entities.Items.Item;
import com.company.Entities.Items.Materializable;
import com.company.Entities.Items.Material;
import com.company.Obstacles.Obstacle;
import org.newdawn.slick.Graphics;

import java.util.List;

import static java.lang.Math.*;

public class Tool extends Item implements Materializable {
    protected Material material;
    protected int efficiency = 5;

    public Tool(Material material ,int quantity) {
        super(quantity);
        this.material = material;
        efficiency*=material.getPower();
        durabilityMax*=material.getPower();
        durability = durabilityMax;
    }

    public Tool(Material material) {
        super(1);
        this.material = material;
        efficiency*=material.getPower();
        durabilityMax*=material.getPower();
        durability = durabilityMax;
    }

    @Override
    public void display(float x, float y, float dx, float dy, Graphics g) {
        super.display(x, y, dx, dy, g);
    }

    public void mine(Entity sender, int rangeMax, List<? extends Obstacle> obstacles){
        float angle = sender.angle;

        float vx = (float)(rangeMax*cos(angle));
        float vy = (float)(rangeMax*sin(angle));

        Obstacle obstacleToMine = sender.collision(obstacles, vx, vy, false);

        if(obstacleToMine != null){
            //System.out.println("MINE !");
            obstacleToMine.getDamage(efficiency);
            durability--;
        }
    }

    @Override
    public Material getMaterial() {
        return material;
    }
}
