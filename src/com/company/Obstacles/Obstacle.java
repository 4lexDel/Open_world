package com.company.Obstacles;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Obstacle {        //Heritage obligatoire
    public static float spawnRate = 0;

    protected float x, y;
    protected int durabilityMax = 50;
    protected int durability = durabilityMax;

    private final int durabilityBarWidth = 80;          //Optimiser

    protected boolean destroy = false;

    protected Image texture = null;

    public Obstacle(){

    }

    protected void init(){}

    public void display(Graphics g){
        if(durability<durabilityMax)displayDurability(g);
    }

    public void displayDurability(Graphics g){
        g.setColor(Color.green);

        int width = (int) (durability*durabilityBarWidth/durabilityMax);

        g.fillRect((int) (x-width/2), (int)y, width, 5);                   //PROBLEME DE DIMENSION
    }

    public void getDamage(int value){
        durability-=value;
        if(durability<=0){
            destroy = true;
        }
    }

    public boolean isDestroy() {
        return destroy;
    }

    public boolean collision(float x, float y, float r){
        return false;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
