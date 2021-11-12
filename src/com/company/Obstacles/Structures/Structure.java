package com.company.Obstacles.Structures;

import com.company.Obstacles.Obstacle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Structure extends Obstacle {
    public static int textureDx = 35;
    public static int textureDy = 35;

    protected boolean collisionActive;
    protected float dx, dy;           //Propre aux structure
    protected Color colorStructure;

    public Structure(float x, float y, float dx, float dy){
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        collisionActive = true;
    }

    public Structure(float x, float y, float dx, float dy, Color colorStructure){
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.colorStructure = colorStructure;
        collisionActive = true;
    }

    public void display(Graphics g){
        super.display(g);
    }

    @Override
    public boolean collision(float xc, float yc, float r) {                             //A améliorer : version rond/rect
        if (xc - r < x + dx && xc + r > x && yc - r < y + dy && yc + r > y) return true;
        return false;
    }

    public boolean collision(float x1, float y1, float x2, float y2) {                             //A améliorer : version rond/rect
        if (x1 < x + dx && x2 > x && y1 < y + dy && y2 > y) return true;
        return false;
    }

    public void reverseStructure(boolean mode){
        float temp = dx;
        dx = dy;
        dy = temp;

        if(mode) x-=dx;//problème de dx et dy
        else y-=dy;
    }

    @Override
    public float getX() {
        return super.getX();
    }

    @Override
    public float getY() {
        return super.getY();
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }

    public void setX(float  x) {
        this.x = x;
    }

    public void setY(float  y) {
        this.y = y;
    }
}
