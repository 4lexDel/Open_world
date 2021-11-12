package com.company.Obstacles.Structures;

//import org.newdawn.slick.GameContainer;
import com.company.Obstacles.Impassable;
import org.newdawn.slick.*;

public class Wall extends Structure implements Impassable {
    public Wall(float x, float y, float dx, float dy){
        //super(x, y, vx, vy, new Color(#822525));
        super(x, y, dx, dy, new Color(200,100,50));
        init();
    }

    public Wall(float x, float y, float dx, float dy, Color colorStructure){
        super(x, y, dx, dy, colorStructure) ;
        init();
    }

    @Override
    protected void init() {
        try {
            texture = new Image("textures/wall2.jpg");      //Gérer les différents etats (cuit/cru)
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void display(Graphics g){
        /*g.setLineWidth(0);
        g.setColor(colorStructure);
        g.fillRect(x, y, dx, dy);*/

        int nbX = (int)(dx/textureDx);
        int nbY = (int)(dy/textureDy);

        for (int i = 0; i < nbX; i++) {
            for (int j = 0; j < nbY; j++) {
                if(texture != null){
                    texture.draw(x+i*textureDx, y+j*textureDy, textureDx, textureDy);   //A voir à terme
                }
            }
        }

        super.display(g);
    }
}
