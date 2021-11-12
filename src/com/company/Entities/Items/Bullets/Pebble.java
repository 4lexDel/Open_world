package com.company.Entities.Items.Bullets;

import com.company.Entities.Entity;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Pebble extends Bullet {
    public Pebble(){
        super(1);

        init();
    }

    public Pebble(int quantity){
        super(quantity);

        init();
    }

    @Override
    protected void init() {
        name = "Pebble";

        try {
            texture = new Image("textures/pebble.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public Pebble(float x, float y, float angle, int attackDamage, Entity sender) {
        super(x, y, angle, attackDamage, sender);

        name = "Pebble";

        try {
            texture = new Image("textures/pebble.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override                                                               //VERSION Projj
    public void display(Graphics g) {
        g.setLineWidth(0);
        g.setColor(new Color(100,100,100));     //Graphique d'un caillou
        g.fillOval(x-size/2,y-size/2, size, size);
    }

    @Override
    public void display(float x, float y, float dx, float dy, Graphics g) {                 //VERSION ITEM
        if(texture != null){
            texture.draw(x, y, dx, dy);
        }
        else {
            g.setLineWidth(0);
            g.setColor(new Color(100, 100, 100));     //Graphique d'un caillou
            g.fillOval(x + dx / 2 - size / 2, y + dy / 2 - size / 2, size, size);
        }
        super.display(x, y, dx, dy, g);
    }
}
