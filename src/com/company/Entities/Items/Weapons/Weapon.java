package com.company.Entities.Items.Weapons;

import com.company.Entities.Items.Item;
import com.company.Entities.Items.Materializable;
import com.company.Entities.Items.Material;
import org.newdawn.slick.Graphics;

public class Weapon extends Item implements Materializable {
    protected Material material;
    int attackDamage;

    Item bulletRequired;

    public Weapon(Material material, int quantity) {           //Ajouter interface pour differencier les armes a distance des autres
        super(quantity);
        this.material = material;
        attackDamage = 1;
        durabilityMax*=material.getPower();
        durability = durabilityMax;
    }

    public Weapon(Material material) {
        super(1);
        this.material = material;
        attackDamage = 1;
        durabilityMax*=material.getPower();
        durability = durabilityMax;
    }

    @Override
    public void display(float x, float y, float dx, float dy, Graphics g) {
        //Balles restantes
        super.display(x, y, dx, dy, g);
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    protected void use() {
        durability--;
    }
}
