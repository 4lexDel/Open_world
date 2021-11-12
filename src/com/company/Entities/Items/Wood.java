package com.company.Entities.Items;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Wood extends Item{


    public Wood(int quantity){
        super(quantity);
        name = "Wood";

        try {
            texture = new Image("textures/wood.png");
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
            float size1 = dx;
            float size2 = size1 / 2;

            g.setLineWidth(40);
            g.setColor(new Color(164, 115, 11));
            g.fillOval(x + dy / 2 - size1 / 2, y + dy / 2 - size1 / 2, size1, size1, 8);     //A debattre
            g.setColor(new Color(191, 134, 12));
            g.fillOval(x + dy / 2 - size2 / 2, y + dy / 2 - size2 / 2, size2, size2, 8);
        }
        super.display(x, y, dx, dy, g);
    }*/
}
