package com.company.InvCraft;

import com.company.Entities.Items.Item;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.List;

import static java.lang.Math.*;

public class Trade {            //Permet l'utilisation des craft ainsi que des echanges avec les pnj
    private List<Item> input;
    private Item output;

    public Trade(List<Item> input, Item output){
        this.input = input;
        this.output = output;
    }

    public void display(float x, float y, float dx, float dy, Graphics g){
        float lineWidth = 5;
        g.setLineWidth(lineWidth);
        g.setColor(Color.darkGray);
        g.drawRect(x,y,dx,dy);

        g.setLineWidth(0);
        g.setColor(new Color(230, 120, 20, 50));
        g.fillRect(x+lineWidth/2,y+lineWidth/2,dx-lineWidth,dy-lineWidth);

        float size = dx/3;

        g.pushTransform();
        g.translate(x+size /2,y+dy/2);
        for (int i = 0; i < input.size(); i++) {
            float x2 = (float)(size/3*cos((i*2*PI)/input.size()));
            float y2 = (float)(size/3*sin((i*2*PI)/input.size()));

            input.get(i).display(x2- size /6, y2- size /6, size/3, size/3, g);
        }
        g.translate(size,0);

        g.setColor(Color.darkGray);
        g.setLineWidth(2*lineWidth);
        g.drawLine(0,0,2*size/3,0);

        g.translate(size,0);

        output.display(- size /6, - size /6, size/3, size/3, g);

        g.popTransform();
    }

    public void display(float x, float y, float dx, float dy, boolean active,Graphics g){       //Copie pour l'ajout du boolean
        float lineWidth = 5;
        g.setLineWidth(lineWidth);
        if(active) g.setColor(Color.red);               /**Pas clair**/
        else g.setColor(Color.darkGray);
        g.drawRect(x,y,dx,dy);

        g.setLineWidth(0);
        g.setColor(new Color(230, 120, 20, 90));
        g.fillRect(x+lineWidth/2,y+lineWidth/2,dx-lineWidth,dy-lineWidth);

        float size = dx/3;

        g.pushTransform();
        g.translate(x+size /2,y+dy/2);
        for (int i = 0; i < input.size(); i++) {
            float x2 = (float)(size/3*cos((i*2*PI)/input.size()));
            float y2 = (float)(size/3*sin((i*2*PI)/input.size()));

            input.get(i).display(x2- size /6, y2- size /6, size/3, size/3, g);
        }
        g.translate(size,0);

        g.setColor(Color.darkGray);
        g.setLineWidth(2*lineWidth);
        g.drawLine(0,0,2*size/3,0);

        g.translate(size,0);

        output.display(- size /6, - size /6, size/3, size/3, g);

        g.popTransform();
    }

    public List<Item> getInput() {
        return input;
    }

    public Item getOutput() {
        return output;
    }
}
