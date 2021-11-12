package com.company.Entities;

import com.company.Game;
import com.company.Obstacles.Impassable;
import com.company.Obstacles.Obstacle;
import com.company.Obstacles.Structures.Structure;
import com.company.Obstacles.Vegetations.Vegetation;
import com.company.ToolBox.ScoreBar;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

//import java.awt.*;
import java.util.List;

import static java.awt.geom.Point2D.distance;

public class Entity{
    public static int nbEntities;
    public static float spawnRate = 0;

    public float x, y;
    public float vx, vy, size, speedMax, angle;          //Parametres de mouvements

    protected String name;

    protected boolean invincibility = false;
    protected float pvRegeneration = 0.02F;

    protected ScoreBar pvScoreBar, energyScoreBar, hungerScoreBar;
    protected float scoreBarHeight;

    protected int pvMax = 100;
    protected float pv = 100;              //Gestion de la vie
    protected boolean dead = false;

    protected int energyMax = 100;
    protected float energy = energyMax;

    protected float energyRegeneration = 2/(float)Game.FPS;
    protected float runEnergyCost = 10/(float)Game.FPS;
    protected float mineEnergyCost = 5;
    protected float fightEnergyCost = 3;

    protected int hungerMax = 100;
    protected float hunger = hungerMax/2;

    protected float hungerTime = 0.1F/Game.FPS;

    protected Image texture = null;

    public Entity(float x, float y, String name){
        this.x = x;
        this.y = y;
        this.name = name;

        init();
    }

    public Entity(float x, float y){
        this.x = x;
        this.y = y;
        name = "anonymous";

        init();              //Compteur factuelle    ==> trouver un moyen de décrémenter
    }

    protected void init(){
        speedMax = 6;
        size = 25;

        angle = 0;

        nbEntities++;

        scoreBarHeight = Game.height/40;

        pvScoreBar = new ScoreBar(0,Game.width/3, scoreBarHeight, pvMax, Color.red);
        energyScoreBar = new ScoreBar(1.5F*scoreBarHeight, Game.width/3, scoreBarHeight, energyMax, Color.cyan);
        hungerScoreBar = new ScoreBar(3*scoreBarHeight, Game.width/3, scoreBarHeight, hungerMax, Color.orange);
    }

    public void display(Graphics g){

    }

    public void displayPV(GameContainer gc, Graphics g){
        pvScoreBar.setValue(pv);
        pvScoreBar.display(gc, g);
    }

    public void displayEnergy(GameContainer gc, Graphics g){
        energyScoreBar.setValue(energy);
        energyScoreBar.display(gc, g);
    }

    public void displayHunger(GameContainer gc, Graphics g){
        hungerScoreBar.setValue(hunger);
        hungerScoreBar.display(gc, g);
    }

    public void updateLifeState(){
        updateEnergy(energyRegeneration);
        updateHunger(-hungerTime);
    }

    public void updateEnergy(float value){
        energy+=value;
        if(energy>energyMax){
            pv+=pvRegeneration;         //Nuancer
            if(pv>pvMax)pv=pvMax;
            energy=energyMax;
        }
        if(energy<0){
            energy=0;
            this.getDamage(1);
        }
    }
    public void updateHunger(float value){
        hunger+=value;
        if(hunger>hungerMax){
            updateEnergy(energyRegeneration*10);
            hunger=hungerMax;
        }
        if(hunger<0){
            hunger=0;
            updateEnergy(-energyRegeneration*10);
        }
    }

    public Obstacle checkCollision(List<? extends Obstacle> obstacles){        //Utiliser avec la fonction collision
        for(int i = 0; i< obstacles.size(); i++) {
            if(obstacles.get(i) instanceof Impassable) {
                if (!obstacles.get(i).isDestroy()) {
                    if (obstacles.get(i) instanceof Structure) {
                        Structure structure = (Structure) obstacles.get(i);

                        if (x - size / 2 < structure.getX() + structure.getDx() && x + size / 2 > structure.getX() && y - size / 2 < structure.getY() + structure.getDy() && y + size / 2 > structure.getY())
                            return structure;
                    } else if (obstacles.get(i) instanceof Vegetation) {
                        Vegetation vegetation = (Vegetation) obstacles.get(i);

                        if (distance(vegetation.getX(), vegetation.getY(), x, y) <= vegetation.getSizeGlobal() / 2 + size / 2)
                            return vegetation;
                    }
                }
            }
        }
        return null;
    }
    public Obstacle collision(List<? extends Obstacle> obstacles, float vx, float vy, boolean anticipation) {
        if(checkCollision(obstacles) != null) return null;     //Debug autorise les mouvements si l'entité est bloqué
        x += vx;
        y += vy;            //Anticipation

        Obstacle obstacleCollision = checkCollision(obstacles);

        if (obstacleCollision != null) {
            if(anticipation) {
                y -= vy;
                if (checkCollision(obstacles) != null) {        //Objectif de ce systeme : rendre les collisions plus "agréable"
                    x -= vx;                            //on anticipe individuellement en x puis en y pour permettre de potentiellement "glisser" sur l'obstacle
                    y += vy;
                    if (checkCollision(obstacles) != null) {
                        y -= vy;
                    }
                }
            }
            else {
                x -= vx;
                y -= vy;
                return obstacleCollision;             //A ameliorer ?
            }
            return obstacleCollision;
        }

        x -= vx;
        y -= vy;

        return null;
    }

    public Entity collision(List<? extends Entity> entities, float vx, float vy){       //Astuce de la variable locale plus simple !
        float xc = x;
        float yc = y;

        xc += vx;
        yc += vy;

        for (Entity target : entities) {
            if(!target.isDead())
                if (distance(target.x, target.y, xc, yc) <= target.size/2 + size/2) return target;
        }

        return null;
    }

    public void getDamage(int value){           //A continuer ??
        if(!invincibility) {
            pv -= value;
            if (pv <= 0) {
                dead = true;
                System.out.println(name+" est mort !");
            }
        }
    }

    public String getName() {
        return name;
    }

    public float getPv() {
        return pv;
    }

    public boolean isDead() {return dead;}
}

