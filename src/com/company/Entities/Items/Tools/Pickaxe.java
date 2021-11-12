package com.company.Entities.Items.Tools;

import com.company.Entities.Items.Material;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

public class Pickaxe extends Tool{
    public Pickaxe(Material material, int quantity) {
        super(material, quantity);
        name = "Pickaxe";
    }

    public Pickaxe(Material material) {
        super(material, 1);
        name = "Pickaxe";
    }

    @Override
    public void display(float x, float y, float dx, float dy, Graphics g) {
        float E = dx/8;

        g.setLineWidth(5);
        g.setColor(new Color(133,110,22));

        g.drawLine(x+dx/2, y+E, x+dx/2, y+dy-E);

        g.setColor(material.getColor());

        Polygon polygon;
        polygon = new Polygon();
        polygon.addPoint((int)(x+E/2),(int)(y+2*E));
        polygon.addPoint((int)(x+dx/2),(int)(y+E));
        polygon.addPoint((int)(x+dx-E/2),(int)(y+2*E));
        polygon.setClosed(false);

        g.setLineWidth(5);
        g.draw(polygon);

        super.display(x, y, dx, dy, g);
    }
}
