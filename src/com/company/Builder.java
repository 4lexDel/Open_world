package com.company;

import com.company.Obstacles.Obstacle;
import com.company.Obstacles.Structures.Floor;
import com.company.Obstacles.Structures.Structure;
import com.company.Obstacles.Structures.Wall;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import java.util.ArrayList;
import java.util.List;

import static java.awt.geom.Point2D.distance;
import static java.lang.Math.abs;

public class Builder {
    private float x1 = -1;
    private float y1 = -1;
    private float x2 = -1;
    private float y2 = -1;

    private int buildSensibility = 30;
    private int unitBlock = 50;

    public Builder(){

    }

    public void build(List<Obstacle> obstacles, int id, GameContainer gc) {
        Input input = gc.getInput();

        if(input.isMousePressed(0)){
            if(x1 == -1) {                           //Premier click
                Vector2f coord1 = getCoordBuild(obstacles, gc);
                x1 = coord1.x;
                y1 = coord1.y;
            }
            else if(x2 == -1){                              //2ieme click
                Vector2f coord2 = getCoordBuild(obstacles, gc);
                x2 = coord2.x;
                y2 = coord2.y;
            }
        }
        if(x1 != -1 && x2 != -1){           //zone choisis
            //coord ordre
            float x, y;
            //test modulo
            float dx = abs(x1-x2);
            float dy = abs(y1-y2);                                      //Metre sous forme de fonction

            dx -= dx% Structure.textureDx;
            dy -= dy%Structure.textureDy;

            if(x1<=x2) x = x1;
            else x = x1-dx;
            //On remet dans l'ordre les coordonnées
            if(y1<=y2) y = y1;
            else y = y1-dy;

            Obstacle newObstacle = null;

            switch(id){         //Stocker les id directement dans les differentes structures
                case 1:
                    newObstacle = new Wall(x, y, dx, dy);
                    break;
                case 2:
                    newObstacle = new Floor(x, y, dx, dy);
                    break;
            }

            if(newObstacle != null)
                obstacles.add(newObstacle);                            //WALL PAR DEFAUT A MODIFF
            reset();
            //System.out.println(3);
        }
    }

    public House createHouse(List<Obstacle> obstacles, GameContainer gc){
        Input input = gc.getInput();

        List<Structure> houseStructures = null;

        if(input.isMousePressed(0)){
            if(x1 == -1) {                           //Premier click
                x1 = input.getMouseX() + Game.width * Game.chunkX;
                y1 = input.getMouseY() + Game.height * Game.chunkY;
                System.out.println(1);
            }
            else if(x2 == -1){                              //2ieme click
                x2 = input.getMouseX() + Game.width * Game.chunkX;
                y2 = input.getMouseY() + Game.height * Game.chunkY;
                System.out.println(2);
            }
        }
        if(x1 != -1 && x2 != -1){
            float xA, xB, yA, yB;
            
            if(x1 < x2) {
                xA = x1;
                xB = x2;
            }
            else {
                xA = x2;        //Remet les coordonnées dans l'ordre
                xB = x1;
            }

            if(y1 < y2) {
                yA = y1;
                yB = y2;
            }
            else {
                yA = y2;
                yB = y1;
            }
            
            houseStructures = selectStructureArea(xA, yA, xB, yB, obstacles);
            reset();
            if(houseStructures.size()>0) return new House(houseStructures);         //On retourne la maison avec ses elements
            System.out.println(3);
        }

        return null;
    }

    private List<Structure> selectStructureArea(float x1, float y1, float x2, float y2, List<Obstacle> obstacles) {
        List<Structure> structures = new ArrayList<>();

        for (Obstacle obstacle : obstacles) {
            if(obstacle instanceof Structure){
                Structure structure = (Structure) obstacle;
                if(structure.collision(x1, y1, x2, y2)) structures.add(structure);
            }
        }

        return structures;
    }

    public void reset(){
        x1 = -1;            //Reset
        y1 = -1;
        x2 = -1;
        y2 = -1;
    }

    public void displayConstruction(List<Obstacle> obstacles, GameContainer gc , Graphics g){
        if(x1 != -1 && x2 == -1){
            Input input = gc.getInput();

            float dx = Game.chunkX*Game.width;
            float dy = Game.chunkY*Game.height;

            float xA = x1-dx;
            float yA = y1-dy;

            float xB = input.getMouseX();
            float yB = input.getMouseY();


            Vector2f nearestStructure = nearestStructure(xB +dx, yB +dy, buildSensibility, obstacles);  //REPETITION

            if(nearestStructure != null){
                //System.out.println("test");
                xB = nearestStructure.x-dx;
                yB = nearestStructure.y-dy;
            }

            float x, y;

            //test modulo
            //on reutilise dx et dy
            dx = abs(xA-xB);
            dy = abs(yA-yB);

            dx -= dx%Structure.textureDx;
            dy -= dy%Structure.textureDy;

            if(xA<= xB) x = xA;
            else x = xA-dx;

            if(yA<= yB) y = yA;
            else y = yA-dy;

            g.setColor(new Color(100, 100, 100, 50));
            g.fillRect(x, y, dx, dy);
        }
    }

    Vector2f getCoordBuild(List<Obstacle> obstacles, GameContainer gc){
        Input input = gc.getInput();

        float x = input.getMouseX() + Game.width * Game.chunkX;
        float y = input.getMouseY() + Game.height * Game.chunkY;         //Ajouter un aspect graphique plus clean

        Vector2f nearestObstacle = nearestStructure(x, y, buildSensibility, obstacles);

        if(nearestObstacle != null){
            x = nearestObstacle.x;
            y = nearestObstacle.y;
        }

        return new Vector2f(x, y);
    }

    void delete(List<Obstacle> obstacles, GameContainer gc){
        Input input = gc.getInput();

        if (input.isMousePressed(0)) {
            float dx = Game.chunkX * Game.width;
            float dy = Game.chunkY * Game.height;

            float xm = input.getMouseX() + dx;
            float ym = input.getMouseY() + dy;

            for (int i = 0; i < obstacles.size(); i++) {
                if (obstacles.get(i).collision(xm, ym, buildSensibility)) obstacles.remove(i);
            }
        }
    }

    Vector2f nearestStructure(float x, float y, float limit, List<Obstacle> obstacles){
        float distanceMin = limit;           //Par défaut = trop élevé ==> pour etre "dépassé"
        Vector2f coord = null;

        for (Obstacle obstacle : obstacles) {       //Filtrer ?
            if(obstacle instanceof Structure) {
                float xw = obstacle.getX();
                float yw = obstacle.getY();

                float dx = ((Structure) obstacle).getDx();
                float dy = ((Structure) obstacle).getDy();

                float d = (float) distance(xw, yw, x, y);
                if(d <= limit/2 && d <= distanceMin){
                    distanceMin = d;
                    coord = new Vector2f(xw, yw);
                }

                d = (float) distance(xw+dx, yw, x, y);
                if(d <= limit/2 && d <= distanceMin){               //A moduler !!!!!!!!
                    distanceMin = d;
                    coord = new Vector2f(xw+dx, yw);
                }

                d = (float) distance(xw, yw+dy, x, y);
                if(d <= limit/2 && d <= distanceMin){
                    distanceMin = d;
                    coord = new Vector2f(xw, yw+dy);
                }

                d = (float) distance(xw+dx, yw+dy, x, y);
                if(d <= limit/2 && d <= distanceMin){
                    distanceMin = d;
                    coord = new Vector2f(xw+dx, yw+dy);
                }
            }
        }

        return coord;
    }
    void displayCursor(GameContainer gc, Graphics g){
        Input input = gc.getInput();

        g.setColor(Color.white);
        g.setLineWidth(0);

        g.drawOval(input.getMouseX()-buildSensibility/2, input.getMouseY()-buildSensibility/2, buildSensibility, buildSensibility);
    }
}
