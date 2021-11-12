package com.company.InvCraft;

import com.company.Entities.Items.Item;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.List;

public class TradeSection {
    private List<Trade> trades;
    private Item itemSection;
    private String sectionName;

    public TradeSection(List<Trade> trades, Item itemSection, String sectionName) {
        this.trades = trades;
        this.itemSection = itemSection;
        this.sectionName = sectionName;

        this.itemSection.setShowName(false);
    }

    public void display(float x, float y, float dx, float dy, Graphics g){
        float lineWidth = 5;
        int ratio = 2;

        g.setLineWidth(lineWidth);
        g.setColor(Color.darkGray);
        g.drawRect(x,y,dx,dy);

        g.setLineWidth(0);
        g.setColor(new Color(230, 120, 20, 50));
        g.fillRect(x+lineWidth/2,y+lineWidth/2,dx-lineWidth,dy-lineWidth);

        float x1 = x + dx / 2 - (dy / (2 * ratio));
        float y1 = y + dy / 2 - (dy / (2 * ratio));
        itemSection.display(x1, y1, dy/ratio, dy/ratio, g);
        displayName(x1, y1, dy/ratio, dy/ratio, g);
    }

    public void display(float x, float y, float dx, float dy, boolean active, Graphics g){
        float lineWidth = 5;
        int ratio = 2;

        g.setLineWidth(lineWidth);
        if(active) g.setColor(Color.red);               /**Pas clair**/
        else g.setColor(Color.darkGray);
        g.drawRect(x,y,dx,dy);

        g.setLineWidth(0);
        g.setColor(new Color(230, 120, 20, 50));
        g.fillRect(x+lineWidth/2,y+lineWidth/2,dx-lineWidth,dy-lineWidth);

        float x1 = x + dx / 2 - (dy / (2 * ratio));
        float y1 = y + dy / 2 - (dy / (2 * ratio));
        itemSection.display(x1, y1, dy/ratio, dy/ratio, g);
        displayName(x1, y1, dy/ratio, dy/ratio, g);
    }

    public void displayName(float x, float y, float dx, float dy, Graphics g){
        g.setColor(Color.darkGray);

        float E = g.getFont().getWidth(sectionName)/2;         //Centrage
        g.drawString(sectionName, x + dx / 2 - E, y - dy / 5);
    }

    public List<Trade> getTrades() {
        return trades;
    }
}
