package com.company.Entities.Items.Bullets;

import com.company.Entities.Entity;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import static java.lang.Math.*;

public class Arrow extends Bullet{                                                          //Plutot sous formes d'item ?
    public Arrow(){
        super(1);

        init();
    }

    public Arrow(int quantity){
        super(quantity);

        init();
    }

    @Override
    protected void init(){
        name = "Arrow";
        size = 10;

        try {
            texture = new Image("textures/arrow.png");  //Chargement de texture
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public Arrow(float x, float y, float angle, int attackDamage, Entity sender) {
        super(x, y, angle, attackDamage, sender);

        size = 10;

        try {
            texture = new Image("textures/arrow.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void display(Graphics g) {
        g.setLineWidth(5);
        g.setColor(new Color(50,50,50));
        g.drawLine((float) (x- size *2*cos(angle)), (float) (y- size *2*sin(angle)),x,y);
        g.setColor(new Color(100,100,100));
        g.fillOval(x- size /2,y- size /2, size, size);
    }

    @Override
    public void display(float x, float y, float dx, float dy, Graphics g) {
        if(texture != null){
            texture.draw(x, y, dx, dy);
        }
        else {
            float E = dx / 8;
            g.setLineWidth(5);
            g.setColor(new Color(50, 50, 50));
            g.drawLine(x + E, y + dy - E, x + dx - E, y + E);
            g.setColor(new Color(100, 100, 100));
            g.fillOval(x + dx - E - size / 2, y + E - size / 2, size, size);
        }
        super.display(x, y, dx, dy, g);
    }
}
