package com.company.Controllers;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import static java.awt.geom.Point2D.distance;
import static java.lang.Math.*;

public class Joystick extends Controller{
    private float x1, y1, x2, y2, x3, y3, d1, d2;

    private Color backgroundColor;
    private Color stickColor;

    private float ratioLimit;

    private boolean limit;

    public Joystick(float x1, float y1, float d1) {
        this.x1 = x1;
        this.y1 = y1;
        this.d1 = d1;

        x2 = x1;
        y2 = y1;

        x3 = x1;
        y3 = y1;

        d2 = 2*d1/5;

        backgroundColor = new Color(150,150,150, 200);
        stickColor = new Color(80, 80,80,200);

        limit = false;

        ratioLimit = 1.5F;
    }

    public void display(GameContainer gc, Graphics g){
        Input input = gc.getInput();

        g.setLineWidth(0);
        g.setColor(backgroundColor);
        g.fillOval(x1-d1/2, y1-d1/2, d1, d1);
        g.setColor(stickColor);
        g.setLineWidth(40);
        g.setColor(new Color(150,150,150));
        g.drawLine(x1, y1, x3, y3);
        g.setLineWidth(0);
        g.fillOval(x3-d2/2, y3-d2/2, d2, d2);

        if(input.isMouseButtonDown(0) && isStickSelected(gc)) stickColor = new Color(200, 200,200,200);
        else stickColor = new Color(80,80,80, 200);
    }

    @Override
    public void use(GameContainer gc){
        Input input = gc.getInput();
        //println(stickUse);
        if(input.isMouseButtonDown(0) && isStickSelected(gc)){
            x2 = input.getMouseX();
            y2 = input.getMouseY();

            x3 = x2;
            y3 = y2;

            float deltaX = x2-x1;
            float deltaY = y2-y1;

            angle = (float) atan(deltaY/deltaX);

            if(x2<x1) angle += PI;

            if(distance(x1, y1, x2, y2) >= ratioLimit*d1/2){
                //limit = true;
                x3 = (float) (x1+ratioLimit*d1/2*cos(angle));
                y3 = (float) (y1+ratioLimit*d1/2*sin(angle));
            }
            //else limit = false;
        }
        else{
            x2 = x1;
            y2 = y1;

            x3 = x1;
            y3 = y1;
        }
    }

    public boolean isStickSelected(GameContainer gc){
        Input input = gc.getInput();

        if(distance(input.getMouseX(), input.getMouseY(), x2, y2) <= d2) return true;
        return false;
    }

    @Override
    public float getPowerRatio(){
        return (float) (distance(x1, y1, x3, y3)/(ratioLimit*d1/2));
    }
}

