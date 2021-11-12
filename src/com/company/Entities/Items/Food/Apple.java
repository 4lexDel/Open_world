package com.company.Entities.Items.Food;

//import com.company.Entities.Entity;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Apple extends Eatable{

    public Apple(int quantity){
        super(quantity);
        name = "Apple";

        init();
    }

    @Override
    protected void init() {
        try {
            texture = new Image("textures/apple.png");
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
            g.setColor(new Color(255, 0, 0));
            g.fillOval(x+E, y+E, dx-2*E, dy-2*E);   //Pomme = rond rouge
        }
        super.display(x, y, dx, dy, g);
    }*/
}
