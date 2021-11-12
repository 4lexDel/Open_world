package com.company.Obstacles.Vegetations;

import com.company.Entities.Items.Item;
import com.company.Lootable;
import com.company.Obstacles.Impassable;
import com.company.Obstacles.Obstacle;
import org.newdawn.slick.Graphics;

import java.util.List;

import static java.awt.geom.Point2D.distance;

public class Vegetation extends Obstacle implements Impassable, Lootable {
    protected float sizeGlobal;
    protected int nb;                       //Propre a la vegetation
    //int type;
    protected float[] size;
    public static float spawnRate = 0.03F;

    public Vegetation(float x, float y){//ajout de nb ?
        this.x = x;
        this.y = y;
    }

    @Override
    public void display(Graphics g){
        super.display(g);
    }

    @Override
    public void generateLoot(List<Item> items) {

    }

    @Override
    public boolean collision(float xc, float yc, float r) {
        if(distance(x, y, xc, yc)<=r+sizeGlobal/2) return true;
        return false;
    }

    public float getSizeGlobal() {
        return sizeGlobal;
    }

    public void setSizeGlobal(float sizeGlobal) {
        this.sizeGlobal = sizeGlobal;
    }
}
