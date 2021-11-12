package com.company.Entities.Items.Food;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Meat extends Eatable{

    public Meat(int quantity){
        super(quantity);

        init();
    }

    @Override
    protected void init() {
        name = "Meat";

        hungerGive = 30;

        try {
            texture = new Image("textures/uncooked_meat.png");      //Gérer les différents etats (cuit/cru)
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
            float E = dx / 10;
            g.setColor(new Color(255, 100, 100));
            g.fillOval(x + E, y + 2 * E, dx - 2 * E, dy - 4 * E);
        }
        super.display(x, y, dx, dy, g);
    }*/
}
