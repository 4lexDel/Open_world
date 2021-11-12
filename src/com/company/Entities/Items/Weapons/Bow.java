package com.company.Entities.Items.Weapons;

import com.company.Entities.Items.Bullets.Arrow;
import com.company.Entities.Items.Bullets.Bullet;
import com.company.Entities.Entity;
import com.company.Entities.Hero;
import com.company.Entities.Items.Material;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.List;

import static java.lang.Math.*;

public class Bow extends Weapon implements Range{

    public Bow(int quantity){
        super(Material.Wood, quantity);

        attackDamage = 90;
        durabilityMax = 200;
        durability = durabilityMax;

        name = "Bow";

        try {
            texture = new Image("textures/bow.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public Bow(){
        super(Material.Wood);

        attackDamage = 90;
        durabilityMax = 200;
        durability = durabilityMax;

        name = "Bow";

        try {
            texture = new Image("textures/bow.png");
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
            float E = dx / 8;

            g.setLineWidth(3);
            g.setColor(new Color(200, 200, 200));

            g.drawLine(x + 2 * E, y + E, x + 2 * E, y + dy - E);

            g.setLineWidth(5);
            g.setColor(new Color(133, 110, 22));

            Polygon polygon;
            polygon = new Polygon();
            polygon.addPoint((int) (x + 2 * E), (int) (y + E));
            polygon.addPoint((int) (x + dx - 2 * E), (int) (y + dy / 3));
            polygon.addPoint((int) (x + dx - 2 * E), (int) (y + 2 * dy / 3));
            polygon.addPoint((int) (x + 2 * E), (int) (y + dy - E));
            polygon.setClosed(false);

            g.draw((polygon));
        }

        super.display(x, y, dx, dy, g);
    }*/

    @Override
    public void shot(Entity sender, List<Bullet> bullets) {         //A continuer
        if(!(sender instanceof Hero) || ((Hero) sender).inventory.searchItem(new Arrow())) {
            if(sender instanceof Hero) ((Hero) sender).inventory.removeItem(new Arrow());
            float EA = (float) (PI / 5);

            float x = sender.x;
            float y = sender.y;
            float size = sender.size / 2;
            float angle = sender.angle;

            bullets.add(new Arrow(x, y, angle, attackDamage, sender));          //Penser a éviter les auto explosions
            super.use();
        }
    }
}

