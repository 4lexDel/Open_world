package com.company.ToolBox;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class ScoreBar {
    private float y;
    private float width, height;
    private float value;
    private float valueMax;

    private Color colorBar;

    public ScoreBar(float y, float width, float height, float valueMax, Color colorBar){
        this.y = y;
        this.width = width;
        this.height = height;
        this.valueMax = valueMax;

        this.colorBar = colorBar;
    }

    public void display(GameContainer gc, Graphics g){
        int E = 10;
        int lineWidth = 4;

        float widthValue = value*(width/valueMax);

        g.setColor(Color.darkGray);
        g.setLineWidth(lineWidth);
        g.drawRect(gc.getWidth()-width-E, E+y, width, height);
        g.setColor(colorBar);
        g.fillRect(gc.getWidth()-widthValue-E+lineWidth/2, E+lineWidth/2+y, widthValue-lineWidth, height -lineWidth);
    }

    public void setColorBar(Color colorBar) {
        this.colorBar = colorBar;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void addValue(float nb){
        value+=nb;
    }
}
