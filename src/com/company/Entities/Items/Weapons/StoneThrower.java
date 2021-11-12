package com.company.Entities.Items.Weapons;

import com.company.Entities.Items.Bullets.Bullet;
import com.company.Entities.Entity;
import com.company.Entities.Items.Bullets.Pebble;
import com.company.Entities.Items.Material;
import com.company.Entities.Items.Stone;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.List;

import static java.lang.Math.*;

public class StoneThrower extends Weapon implements Range{

    public StoneThrower(int quantity){         //Lance pierre
        super(Material.Wood, quantity);              //Null ?
        durabilityMax = 100;
        durability = durabilityMax;
        attackDamage = 30;

        name = "Stone thrower";
        bulletRequired = new Stone(3);

        try {
            texture = new Image("textures/stone_thrower.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public StoneThrower(){
        super(Material.Wood);
        durabilityMax = 100;
        durability = durabilityMax;
        attackDamage = 30;

        name = "Stone thrower";
        bulletRequired = new Stone(3);

        try {
            texture = new Image("textures/stone_thrower.png");
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

            g.setLineWidth(10);
            g.setColor(new Color(133, 110, 22));
            float x1 = x + dx / 2;
            float y1 = y + dy - E;
            float y2 = y + 4 * dy / 7;
            float y3 = y + E;
            g.drawLine(x1, y1, x1, y2);
            g.drawLine(x1, y2, x + E, y3);
            g.drawLine(x1, y2, x + dx - E, y3);             //Branches
            g.setColor(new Color(100, 100, 100));
            float size = E;
            g.fillOval(x1 - E / 2, y3 - E / 2, E, E);       //Pierre
        }

        super.display(x, y, dx, dy, g);
    }*/

    @Override
    public void shot(Entity sender, List<Bullet> bullets) {         //A continuer
        /*boolean shotAllowed = true;
        if(sender instanceof Hero) {
            if(((Hero) sender).inventory.searchItem(bulletRequired)){
                ((Hero) sender).inventory.removeItem(bulletRequired);
            }
            else shotAllowed = false;
        }
        if(shotAllowed) {*/
        float EA = (float) (PI / 5);

        float x = sender.x;
        float y = sender.y;
        float size = sender.size / 2;
        float angle = sender.angle;

        //float x1 = (float) (x + sizeItem * cos(angle));
        //float y1 = (float) (y + sizeItem * sin(angle));

        bullets.add(new Pebble(x, y, angle, attackDamage, sender));          //Penser a éviter les auto explosions
        bullets.add(new Pebble(x, y, angle + EA, attackDamage, sender));
        bullets.add(new Pebble(x, y, angle - EA, attackDamage, sender));

        super.use();
        //}
    }
}
