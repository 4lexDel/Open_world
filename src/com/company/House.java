package com.company;

import com.company.Obstacles.Structures.Structure;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;
import java.util.List;

import static java.awt.geom.Point2D.distance;
import static java.lang.Math.*;

public class House{
    public static float spawnRate = 0.005F;

    List<Structure> structures;
    float x0, y0;

    public House(){
        structures = new ArrayList<Structure>();
    }

    public House(List<Structure> structures){
        this.structures = structures;
    }

    public void addStructure(Structure newStructure){
        structures.add(newStructure);
        calculCenterCoord();
    }

    public void calculCenterCoord(){
        float xMin, yMin, xMax, yMax;

        Vector2f coordMin = getCoordMin();
        Vector2f coordMax = getCoordMax();


        xMin = coordMin.x;
        yMin = coordMin.y;
        xMax = coordMax.x;
        yMax = coordMax.y;

        x0 = xMin+(xMax-xMin)/2;
        y0 = yMin+(yMax-yMin)/2;
    }

    public void display(Graphics g){
        g.setColor(new Color(0,0,0));
        g.drawOval(x0, y0, 20, 20);
        for(Structure structure : structures){
            structure.display(g);
        }
    }

    public void rotateRight(){
        //for(int i=0; i<3; i++)  
        rotateHouse((float) (PI/2));
    }

    public void rotateLeft(){
        rotateHouse((float) (-PI/2));
    }

    public void rotateHouse(float angle){
        for(Structure structure : structures){
            float xS = structure.getX();
            float yS = structure.getY();

            float a = (float) atan((yS-y0)/(xS-x0));
            if(xS<x0) a += PI;

            a+=angle;

            float d = (float) distance(xS, yS, x0, y0);

            structure.setX((float) (x0+d*cos(a)));
            structure.setY((float) (y0+d*sin(a)));

            boolean mode = true;
            if(angle<0) mode = false;

            structure.reverseStructure(mode);
        }
    }

    public Vector2f getCoordMin(){
        float xMin = structures.get(0).getX();
        float yMin = structures.get(0).getY();
        for(Structure structure : structures){
            float xS = structure.getX();
            float yS = structure.getY();

            if(xS<xMin) xMin = xS;
            if(yS<yMin) yMin = yS;
        }

        return new Vector2f(xMin, yMin);
    }

    public Vector2f getCoordMax(){
        float xMax = 0;
        float yMax = 0;
        for(Structure s : structures){
            float xS = s.getX()+s.getDx();
            float yS = s.getY()+s.getDy();

            if(xS>xMax) xMax = xS;
            if(yS>yMax) yMax = yS;
        }

        return new Vector2f(xMax, yMax);
    }

    public List<Structure> getStructures() {
        return structures;
    }
}
