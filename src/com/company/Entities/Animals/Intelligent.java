package com.company.Entities.Animals;

import com.company.Controllers.Controller;
import com.company.Entities.Entity;
import com.company.Game;

import static java.lang.Math.PI;

public abstract class Intelligent extends Animal {
    int count = 0;
    int ratio = 20;          //Trouver une astuce pour faire intervenir la vitesse
    int directionTime = ratio;
    float dx, dy;

    Intelligent(float x, float y) {
        super(x, y);
    }

    public void move(Entity target, int[][] mark, float markX, float markY, int markDensity, Controller test){            //Gerer la taille des animaux
        count++;
        if(count%(int)(Game.FPS/10)==0 || true) {

            if(directionTime<ratio) {
                directionTime++;
                x+=dx;
                y+=dy;
            }


            //if(test.isAction()){
            float mx = x - markX;     //AJOUTER SYSTME DOBJECTIF POUR PAS DEVIER DES CHEMINS BALISES
            float my = y - markY;

            float vx = markDensity;
            float vy = markDensity;

            if(directionTime>=ratio) {
                if (isPathPossible(mark, markDensity, mx, my, mx - vx, my + vy)) {
                    dx = -vx / ratio;
                    dy = vy / ratio;
                    angle = (float) (3 * PI / 4);
                    directionTime = 0;
                }
                //DOWN/RIGHT
                else if (isPathPossible(mark, markDensity, mx, my, mx + vx, my + vy)) {
                    dx = vx / ratio;
                    dy = vy / ratio;
                    angle = (float) (PI / 4);
                    directionTime = 0;
                }
                //UP/LEFT
                else if (isPathPossible(mark, markDensity, mx, my, mx - vx, my - vy)) {
                    dx = -vx / ratio;
                    dy = -vy / ratio;
                    angle = (float) -(3 * PI / 4);
                    directionTime = 0;
                }
                //UP/RIGHT
                else if (isPathPossible(mark, markDensity, mx, my, mx + vx, my - vy)) {
                    dx = vx / ratio;
                    dy = -vy / ratio;
                    angle = (float) -(PI / 4);
                    directionTime = 0;
                }
                //DOWN
                else if (isPathPossible(mark, markDensity, mx, my, mx, my + vy)) {
                    dx = 0;
                    dy = vy / ratio;
                    angle = (float) PI / 2;
                    directionTime = 0;
                }
                //UP
                else if (isPathPossible(mark, markDensity, mx, my, mx, my - vy)) {
                    dx = 0;
                    dy = -vy / ratio;
                    angle = (float) -PI / 2;
                    directionTime = 0;
                }
                //RIGHT
                else if (isPathPossible(mark, markDensity, mx, my, mx + vx, my)) {
                    dx = vx / ratio;
                    dy = 0;
                    angle = 0;
                    directionTime = 0;
                }
                //LEFT
                else if (isPathPossible(mark, markDensity, mx, my, mx - vx, my)) {
                    dx = -vx / ratio;
                    dy = 0;
                    angle = (float) PI;
                    directionTime = 0;
                }
            }
        }
        //}
    }

    public boolean isPathPossible(int[][] mark, int markDensity, float currentX, float currentY, float nextX, float nextY){
        int nb = mark.length;

        int cX = (int) (currentX/markDensity);
        int cY = (int) (currentY/markDensity);

        int nX = (int) (nextX/markDensity);
        int nY = (int) (nextY/markDensity);

        //System.out.println("cx : "+cX+" cy : "+cY+" nx : "+nX+" ny : "+nY);

        if (nX >= 0 && nX < nb && nY >= 0 && nY < nb && cX >= 0 && cX < nb && cY >= 0 && cY < nb) {
            if(mark[nX][nY] > 1 && mark[nX][nY] < mark[cX][cY]) {
                //System.out.println(0);
                return true;
            }
        }
        return false;
    }
}
