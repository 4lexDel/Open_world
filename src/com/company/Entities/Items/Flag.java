package com.company.Entities.Items;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Flag extends Item{
    private Color flagColor;
    private int id;

    public Flag(String name, Color flagColor) {
        super(1, name);
        init(0);
        this.flagColor = flagColor;
    }

    public Flag(String name, int id, Color flagColor) {
        super(1, name);
        init(id);
        this.flagColor = flagColor;
    }

    public Flag(Color flagColor) {
        super(1, "Flag");
        init(0);
        this.flagColor = flagColor;
    }

    protected void init(int id) {
        this.id = id;
    }

    @Override
    public void display(float x, float y, float dx, float dy, Graphics g) {
        g.setLineWidth(0);
        g.setColor(flagColor);
        g.fillRect(x,y,dx,dy);
        super.display(x, y, dx, dy, g);
    }

    public int getId() {
        return id;
    }
}
