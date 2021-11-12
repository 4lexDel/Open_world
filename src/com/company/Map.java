/**
package com.company;

import com.company.Entities.Entity;
import com.company.Entities.Hero;
import com.company.Obstacles.Obstacle;
import com.company.Obstacles.Structures.Structure;
import com.company.Obstacles.Vegetations.Vegetation;
import org.newdawn.slick.Graphics;

import java.util.List;

public class Map {              //Penser a la génération chunk par chunk
    private int nbTree;
    private int nbRock;

    private float width, height;

    private List<Obstacle> obstacles;
    private List<Entity> entities;

    private int chunkX, chunkY;

    public Map(List<Obstacle> obstacles, List<Entity> entities){
        this.obstacles = obstacles;
        this.entities = entities;
        width = Game.width;
        height = Game.height;
    }

    public void setObstacles(List<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public void generate(List<? extends Obstacle> obstacles){

    }

    public void displayObstacle(List<? extends Obstacle> obstacles, Graphics g){
        for (Obstacle obstacle : obstacles) {
            if (obstacle instanceof Vegetation) {
                Vegetation vegetation = (Vegetation) obstacle;

                float x = vegetation.x;
                float y = vegetation.y;
                float sizeItem = vegetation.sizeGlobal;
                if (x - sizeItem < width + chunkX * width && x + sizeItem > 0 + chunkX * width && y - sizeItem < height + chunkY * height && y + sizeItem > chunkY * height)
                    vegetation.display(g);      //AFIN DEVITER BEAUCOUP DE LAG
            } else if (obstacle instanceof Structure) { //Differenciation des structures
                Structure structure = (Structure) obstacle;

                float x = structure.x;
                float y = structure.y;
                float dx = structure.dx;
                float dy = structure.dy;
                if (x < width + chunkX * width && x + dx > 0 + chunkX * width && y < height + chunkY * height && y + dy > chunkY * height)
                    structure.display(g);
            }
        }
    }

    public void displayEntity(List<? extends Entity> entities, Graphics g){
        for (Entity entity : entities) {
            float x = entity.x;
            float y = entity.y;           //Affichage du héro
            float sizeItem = entity.sizeItem;
            if (x - sizeItem < width + chunkX * width && x + sizeItem > 0 + chunkX * width && y - sizeItem < height + chunkY * height && y + sizeItem > chunkY * height)
                entity.display(g);
        }
    }

    public void updateObstacle(List<? extends Obstacle> obstacles){
    ////LES REMOVE
    }

    public void updateEntity(List<? extends Entity> entities){
        ////LES REMOVE
    }

    public void refreshChunkPosition(Hero hero){
        chunkX = (int)(hero.x/width);
        chunkY = (int)(hero.y/height);                  //Centre le jeu sur un hero choisi
    }
}**/
