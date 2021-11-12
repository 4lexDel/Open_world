package com.company.Entities.Items;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Stick extends Item{


    public Stick(int quantity){
        super(quantity);
        name = "Stick";
    }

    @Override
    public void display(float x, float y, float dx, float dy, Graphics g) {
        float E = dx/8;
        float lineWidht = 5;
        g.setLineWidth(lineWidht);
        g.setColor(new Color(164,115,11));
        g.drawLine(x+E, y+E, x+dx-2*E, y+dy-2*E);
        //g.setColor(new Color(191,134,12));
        super.display(x, y, dx, dy, g);
    }
}
