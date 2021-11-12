package com.company.Entities.Items.Tools;

import com.company.Entities.Items.Material;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

public class Axe extends Tool{
    public Axe(Material material, int quantity) {
        super(material, quantity);
        name = "Axe";
    }

    public Axe(Material material) {
        super(material, 1);
        name = "Axe";
    }

    @Override
    public void display(float x, float y, float dx, float dy, Graphics g) {
        float E = dx/8;

        float y2 = dy/3;

        g.setLineWidth(5);
        g.setColor(new Color(133,110,22));

        g.drawLine(x+dx/2, y+E, x+dx/2, y+dy-E);

        g.setColor(material.getColor());
        g.setLineWidth(0);

        Polygon polygon1;
        polygon1 = new Polygon();
        polygon1.addPoint(x+dx/2, y+0.8F*y2);
        polygon1.addPoint(x+2*E, y+E);
        polygon1.addPoint(x+2*E, y+2*y2);
        polygon1.addPoint(x+dx/2, y+y2);

        polygon1.setClosed(true);

        Polygon polygon2;
        polygon2 = new Polygon();
        polygon2.addPoint(x+dx/2, y+0.8F*y2);
        polygon2.addPoint(x+dx-2*E, y+E);
        polygon2.addPoint(x+dx-2*E, y+2*y2);
        polygon2.addPoint(x+dx/2, y+y2);

        polygon2.setClosed(true);

        g.fill(polygon1);
        g.fill(polygon2);

        super.display(x, y, dx, dy, g);
    }
}

