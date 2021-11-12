package com.company.Entities.Items.Weapons;

import com.company.Entities.Entity;
import com.company.Entities.Items.Material;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.List;

import static java.lang.Math.*;

public class Sword extends Weapon implements HandToHand{
    public Sword(Material material, int quantity){         //Lance pierre
        super(material, quantity);
        this.material = material;
        attackDamage = 50;

        name = "Sword";
    }

    public Sword(Material material){
        super(material);
        this.material = material;
        attackDamage = 50;

        name = "Sword";
    }

    @Override
    public void display(float x, float y, float dx, float dy, Graphics g) {
        float E = dx/8;
        float y1 = 3*dy/4;

        g.setLineWidth(5);
        g.setColor(material.getColor());

        g.drawLine(x+dx/2, y+E/2, x+dx/2, y+y1);

        g.setColor(new Color(133,110,22));

        g.drawLine(x+3*E, y+y1, x+dx-3*E, y+y1);
        g.drawLine(x+dx/2, y+y1, x+dx/2, y+dy-E);

        super.display(x, y, dx, dy, g);
    }

    @Override
    public boolean hit(Entity sender, List<? extends Entity>... entities) {         //A continuer
        float size = sender.size;
        float angle = sender.angle;

        Entity target = null;

        for (List<? extends Entity> targets : entities) {
            Entity trans = sender.collision(targets, (float)(size*cos(angle)), (float)(size*sin(angle)));
            if(trans != null) target = trans;
        }

        if(target != null && target != sender) {
            super.use();
            target.getDamage(attackDamage);
            return true;
        }
        return false;
    }
}
