package com.company.Entities.Items;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Stone extends Item{

    public Stone(int quantity){
        super(quantity);
        name = "Stone";

        try {
            texture = new Image("textures/stone.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    /*@Override
    public void display(float x, float y, float dx, float dy, Graphics g) {
        if(texture != null){
            texture.draw(x, y, dx, dy);
        }
        else {
            float E = dx / 5;
            g.setLineWidth(0);
            g.setColor(new Color(100, 100, 100));
            g.fillRect(x + E, y + E, dx - 2 * E, dy - 2 * E);
        }
        super.display(x, y, dx, dy, g);
    }*/
}
